package com.framgia.fdms.screen.profile;

import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;
import com.framgia.fdms.BR;
import com.framgia.fdms.R;
import com.framgia.fdms.data.model.Picture;
import com.framgia.fdms.data.model.User;
import com.framgia.fdms.data.source.local.sharepref.SharePreferenceImp;
import com.framgia.fdms.screen.authenication.login.LoginActivity;
import com.framgia.fdms.utils.Utils;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;
import static com.framgia.fdms.data.source.local.sharepref.SharePreferenceKey.USER_PREFS;
import static com.framgia.fdms.utils.Constant.PICK_IMAGE_REQUEST;

/**
 * Exposes the data to be used in the Profile screen.
 */

public class ProfileViewModel extends BaseObservable
        implements ProfileContract.ViewModel, DatePickerDialog.OnDateSetListener {

    private final AppCompatActivity mActivity;
    private final Context mContext;
    private final ProfileFragment mFragment;
    private Calendar mCalendar = Calendar.getInstance();
    private SharePreferenceImp mPreferences;
    private ProfileContract.Presenter mPresenter;
    private User mUser;
    private boolean mIsEdit;
    private String mBirthDay;

    public ProfileViewModel(AppCompatActivity activity, ProfileFragment fragment) {
        mActivity = activity;
        mContext = activity.getApplicationContext();
        mFragment = fragment;
        mPreferences = new SharePreferenceImp(mContext);
    }

    @Override
    public void onStart() {
        mPresenter.onStart();
    }

    @Override
    public void onStop() {
        mPresenter.onStop();
    }

    @Override
    public void setPresenter(ProfileContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK) {
            Uri uri = data.getData();
            mUser.setAvatar(new Picture(Utils.getPathFromUri(mActivity, uri)));
            notifyChange();
        }
    }

    @Override
    public void setCurrentUser(User user) {
        mUser = user;
        if (mUser == null) mUser = new User();

        DateFormat format = new SimpleDateFormat("dd - MM - yyyy", Locale.getDefault());
        mBirthDay = mUser.getBirthday() == null ? "" : format.format(mUser.getBirthday());

        setUser(user);
    }

    @Override
    public void onError(String message) {
        Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onPickDateTime() {
        if (mCalendar == null) mCalendar = Calendar.getInstance();
        DatePickerDialog datePicker =
                DatePickerDialog.newInstance(this, mCalendar.get(Calendar.YEAR),
                        mCalendar.get(Calendar.MONTH), mCalendar.get(Calendar.DAY_OF_MONTH));
        datePicker.show(mActivity.getFragmentManager(), "");
    }

    @Override
    public void onClickPickAvatar() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        mFragment.startActivityForResult(
                Intent.createChooser(intent, mContext.getString(R.string.title_select_file_upload)),
                PICK_IMAGE_REQUEST);
    }

    @Override
    public void onClickEditProfile() {
        mIsEdit = !mIsEdit;
        notifyChange();
    }

    @Override
    public void onClickDoneEditProfile() {
        // TODO: 5/30/2017 work call api update profile
    }

    @Override
    public void onClickLogout() {
        // TODO: 5/30/2017 Call api logout
        mPreferences.remove(USER_PREFS);
        mActivity.startActivity(LoginActivity.getInstance(mContext));
        mActivity.finish();
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        mBirthDay = dayOfMonth + " - " + (monthOfYear + 1) + " - " + year;
        mCalendar.set(Calendar.YEAR, year);
        mCalendar.set(Calendar.MONTH, monthOfYear);
        mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        mUser.setBirthday(mCalendar.getTime());
    }

    @Bindable
    public User getUser() {
        return mUser;
    }

    public void setUser(User user) {
        mUser = user;
        notifyPropertyChanged(BR.user);
    }

    @Bindable
    public boolean isEdit() {
        return mIsEdit;
    }

    public void setEdit(boolean edit) {
        mIsEdit = edit;
        notifyPropertyChanged(BR.edit);
    }

    @Bindable
    public String getBirthDay() {
        return mBirthDay;
    }

    public void setBirthDay(String birthDay) {
        mBirthDay = birthDay;
        notifyPropertyChanged(BR.birthDay);
    }
}
