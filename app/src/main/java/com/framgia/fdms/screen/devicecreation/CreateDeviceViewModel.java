package com.framgia.fdms.screen.devicecreation;

import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;
import com.framgia.fdms.BR;
import com.framgia.fdms.R;
import com.framgia.fdms.data.model.Category;
import com.framgia.fdms.data.model.Device;
import com.framgia.fdms.data.model.Picture;
import com.framgia.fdms.data.model.Status;
import com.framgia.fdms.screen.main.MainActivity;
import com.framgia.fdms.screen.selection.StatusSelectionActivity;
import com.framgia.fdms.screen.selection.StatusSelectionType;
import com.framgia.fdms.utils.Utils;
import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;
import static com.framgia.fdms.utils.Constant.BundleConstant.BUNDLE_CATEGORY;
import static com.framgia.fdms.utils.Constant.BundleConstant.BUNDLE_STATUE;
import static com.framgia.fdms.utils.Constant.PICK_IMAGE_REQUEST;
import static com.framgia.fdms.utils.Constant.RequestConstant.REQUEST_CATEGORY;
import static com.framgia.fdms.utils.Constant.RequestConstant.REQUEST_STATUS;

/**
 * Exposes the data to be used in the Createdevice screen.
 */

public class CreateDeviceViewModel extends BaseObservable
        implements CreateDeviceContract.ViewModel {

    private DeviceStatusType mDeviceType = DeviceStatusType.CREATE;
    private Context mContext;
    private AppCompatActivity mActivity;
    private CreateDeviceContract.Presenter mPresenter;
    private String mDeviceCodeError;
    private String mNameDeviceError;
    private String mSerialNumberError;
    private String mModelNumberError;

    private Device mDevice;

    private List<Status> mStatuses = new ArrayList<>();
    private List<Category> mCategories = new ArrayList<>();

    private Category mCategory;
    private Status mStatus;

    public CreateDeviceViewModel(CreateDeviceActivity activity, Device device,
            DeviceStatusType type) {
        mContext = activity.getApplicationContext();
        mActivity = activity;
        if (device == null) {
            mDevice = new Device();
        } else {
            mDevice = device;
            mCategory = new Category(device.getDeviceCategoryId(), device.getDeviceCategoryName());
            mStatus = new Status(device.getDeviceStatusId(), device.getDeviceStatusName());
        }
        mDeviceType = type;
    }

    @Override
    public void onCreateDeviceClick() {
        switch (mDeviceType) {
            case CREATE:
                mPresenter.registerDevice(mDevice);
                break;
            case EDIT:
                mPresenter.updateDevice(mDevice);
                break;
            default:
                break;
        }
    }

    public void onChooseCategory() {
        if (mCategories == null) return;
        mActivity.startActivityForResult(
                StatusSelectionActivity.getInstance(mContext, mCategories, mStatuses,
                        StatusSelectionType.CATEGORY), REQUEST_CATEGORY);
    }

    public void onChooseStatus() {
        if (mStatuses == null) return;
        mActivity.startActivityForResult(
                StatusSelectionActivity.getInstance(mContext, mCategories, mStatuses,
                        StatusSelectionType.STATUS), REQUEST_STATUS);
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
    public void setPresenter(CreateDeviceContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onDeviceCategoryLoaded(List<Category> categories) {
        updateCategory(categories);
    }

    public void updateCategory(List<Category> list) {
        if (list == null) {
            return;
        }
        mCategories.clear();
        mCategories.addAll(list);
    }

    public void updateStatus(List<Status> list) {
        if (list == null) {
            return;
        }
        mStatuses.clear();
        mStatuses.addAll(list);
    }

    @Override
    public void showProgressbar() {
        // TODO: later
    }

    @Override
    public void hideProgressbar() {
        // TODO: later
    }

    @Override
    public void onRegisterError() {
        Toast.makeText(mContext, R.string.msg_create_device_error, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRegisterSuccess() {
        mContext.startActivity(MainActivity.getInstance(mContext));
    }

    @Override
    public void onInputProductionNameError() {
        mNameDeviceError = mContext.getString(R.string.msg_error_user_name);
        notifyPropertyChanged(BR.nameDeviceError);
    }

    @Override
    public void onInputSerialNumberError() {
        mSerialNumberError = mContext.getString(R.string.msg_error_user_name);
        notifyPropertyChanged(BR.serialNumberError);
    }

    @Override
    public void onInputModellNumberError() {
        mModelNumberError = mContext.getString(R.string.msg_error_user_name);
        notifyPropertyChanged(BR.modelNumberError);
    }

    @Override
    public void onInputDeviceCodeError() {
        mDeviceCodeError = mContext.getString(R.string.msg_error_user_name);
        notifyPropertyChanged(BR.deviceCodeError);
    }

    @Override
    public void onInputCategoryError() {
        // TODO: later
    }

    @Override
    public void onInputStatusError() {
        // TODO: later
    }

    @Override
    public void onAddImageClick() {
        pickImage();
    }

    public void pickImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        mActivity.startActivityForResult(
                Intent.createChooser(intent, mContext.getString(R.string.title_select_file_upload)),
                PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK || data == null) return;
        switch (requestCode) {
            case PICK_IMAGE_REQUEST:
                if (data.getData() != null) {
                    Uri uri = data.getData();
                    mDevice.setPicture(new Picture(Utils.getPathFromUri(mActivity, uri)));
                }
                break;
            case REQUEST_CATEGORY:
                Bundle bundle = data.getExtras();
                Category category = bundle.getParcelable(BUNDLE_CATEGORY);
                if (category.getName().equals(mContext.getString(R.string.action_clear))) {
                    category.setName(mContext.getString(R.string.title_empty));
                }
                setCategory(category);
                break;
            case REQUEST_STATUS:
                bundle = data.getExtras();
                Status status = bundle.getParcelable(BUNDLE_STATUE);
                if (status.getName().equals(mContext.getString(R.string.action_clear))) {
                    status.setName(mContext.getString(R.string.title_empty));
                }
                setStatus(status);
                break;
            default:
                break;
        }
    }

    @Override
    public void onDeviceStatusLoaded(List<Status> statuses) {
        updateStatus(statuses);
    }

    @Bindable
    public String getDeviceCodeError() {
        return mDeviceCodeError;
    }

    @Bindable
    public String getNameDeviceError() {
        return mNameDeviceError;
    }

    @Bindable
    public String getSerialNumberError() {
        return mSerialNumberError;
    }

    @Bindable
    public String getModelNumberError() {
        return mModelNumberError;
    }

    @Bindable
    public Category getCategory() {
        return mCategory;
    }

    public void setCategory(Category category) {
        mDevice.setDeviceCategoryId(category.getId());
        mCategory = category;
        notifyPropertyChanged(BR.category);
    }

    @Bindable
    public Status getStatus() {
        return mStatus;
    }

    public void setStatus(Status status) {
        mDevice.setDeviceStatusId(status.getId());
        mStatus = status;
        notifyPropertyChanged(BR.status);
    }

    @Override
    public void onUpdateSuccess(Device device) {
        mDevice = device;
    }

    @Override
    public void onUpdateError() {
        Toast.makeText(mContext, R.string.msg_update_device_error, Toast.LENGTH_LONG).show();
    }

    public AppCompatActivity getActivity() {
        return mActivity;
    }

    public Device getDevice() {
        return mDevice;
    }

    public DeviceStatusType getDeviceType() {
        return mDeviceType;
    }
}
