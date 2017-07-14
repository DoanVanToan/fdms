package com.framgia.fdms.screen.authenication.forgotpassword;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import com.framgia.fdms.BR;
import com.framgia.fdms.R;
import com.framgia.fdms.screen.main.MainActivity;

/**
 * Exposes the data to be used in the Forgetpassword screen.
 */

public class ForgotpasswordViewModel extends BaseObservable implements ForgetpasswordContract.ViewModel {

    private Context mContext;
    private ForgetpasswordContract.Presenter mPresenter;
    private String mEmail;
    private String mEmailError;

    public ForgotpasswordViewModel(Context context) {
        mContext = context;
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
    public void setPresenter(ForgetpasswordContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onInputEmailError() {
        mEmailError =mContext.getString(R.string.error_text_email);
        notifyPropertyChanged(BR.emailError);
    }

    @Override
    public void onInputFormatEmailError() {
        mEmailError =mContext.getString(R.string.error_text_format_email);
        notifyPropertyChanged(BR.emailError);
    }

    @Bindable
    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    @Bindable
    public String getEmailError() {
        return mEmailError;
    }

    public void setEmailError(String emailError) {
        mEmailError = emailError;
    }

    public void onClickRequest() {
        if (!mPresenter.validateDataInput(getEmail())) {
            return;
        }
        mContext.startActivity(MainActivity.getInstance(mContext));
    }
}
