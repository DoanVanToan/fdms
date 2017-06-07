package com.framgia.fdms.screen.requestcreation;

import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import com.framgia.fdms.BR;
import com.framgia.fdms.R;
import com.framgia.fdms.data.model.Category;
import com.framgia.fdms.data.model.Request;
import com.framgia.fdms.data.model.Status;
import com.framgia.fdms.data.source.api.request.DeviceRequest;
import com.framgia.fdms.data.source.api.request.RequestCreatorRequest;
import com.framgia.fdms.screen.selection.StatusSelectionActivity;
import com.framgia.fdms.screen.selection.StatusSelectionType;
import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;
import static com.framgia.fdms.utils.Constant.BundleConstant.BUNDLE_CATEGORY;
import static com.framgia.fdms.utils.Constant.BundleConstant.BUNDLE_STATUE;
import static com.framgia.fdms.utils.Constant.RequestConstant.REQUEST_ASSIGNER;
import static com.framgia.fdms.utils.Constant.RequestConstant.REQUEST_CATEGORY;
import static com.framgia.fdms.utils.Constant.RequestConstant.REQUEST_RELATIVE;

/**
 * Exposes the data to be used in the Requestcreation screen.
 */

public class RequestCreationViewModel extends BaseObservable
        implements RequestCreationContract.ViewModel {

    private ArrayAdapter<Category> mAdapterCategory;
    private RequestCreationContract.Presenter mPresenter;
    private AppCompatActivity mActivity;
    private String mRequestTitle;
    private String mRequestDescription;
    private Status mAssignee;
    private Status mRelative;
    private Category mCategory;
    private RequestCreatorRequest mRequest;
    private DeviceRequest mDeviceRequest;

    private List<Status> mRelatives = new ArrayList<>();
    private List<Status> mAssigners = new ArrayList<>();
    private List<Category> mCategories = new ArrayList<>();

    private RequestCreationAdapter mAdapter;
    private Context mContext;
    private String mTitleError;
    private String mDescriptionError;
    private String mRelativeError;

    public RequestCreationViewModel(AppCompatActivity activity) {
        mActivity = activity;
        mContext = activity.getApplicationContext();
        mRequest = new RequestCreatorRequest();
        mAdapterCategory =
                new ArrayAdapter<>(mActivity, R.layout.support_simple_spinner_dropdown_item);
        mAdapter = new RequestCreationAdapter(mContext, this);
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
    public void setPresenter(RequestCreationContract.Presenter presenter) {
        mPresenter = presenter;
    }

    public AppCompatActivity getActivity() {
        return mActivity;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK || data == null) return;

        switch (requestCode) {
            case REQUEST_RELATIVE:
                Bundle bundle = data.getExtras();
                Status status = bundle.getParcelable(BUNDLE_STATUE);
                if (status.getName().equals(mContext.getString(R.string.action_clear))) {
                    status.setName(mContext.getString(R.string.title_empty));
                }
                setRelative(status);
                break;
            case REQUEST_ASSIGNER:
                bundle = data.getExtras();
                status = bundle.getParcelable(BUNDLE_STATUE);
                if (status.getName().equals(mContext.getString(R.string.action_clear))) {
                    status.setName(mContext.getString(R.string.title_empty));
                }
                setAssignee(status);
                break;
            case REQUEST_CATEGORY:
                bundle = data.getExtras();
                Category category = bundle.getParcelable(BUNDLE_CATEGORY);
                if (category.getName().equals(mContext.getString(R.string.action_clear))) {
                    category.setName(mContext.getString(R.string.title_empty));
                }
                setCategory(category);
                break;
            default:
                break;
        }
    }

    @Override
    public void onRelativeClick() {
        if (mRelatives == null) return;
        mActivity.startActivityForResult(
                StatusSelectionActivity.getInstance(mContext, mCategories, mRelatives,
                        StatusSelectionType.STATUS), REQUEST_RELATIVE);
    }

    @Override
    public void onAssigneeClick() {
        if (mAssigners == null) return;
        mActivity.startActivityForResult(
                StatusSelectionActivity.getInstance(mContext, mCategories, mAssigners,
                        StatusSelectionType.STATUS), REQUEST_ASSIGNER);
    }

    @Override
    public void onCategoryClick(DeviceRequest deviceRequest) {
        if (mCategories == null) return;
        mActivity.startActivityForResult(
                StatusSelectionActivity.getInstance(mContext, mCategories, mAssigners,
                        StatusSelectionType.CATEGORY), REQUEST_CATEGORY);
        mDeviceRequest = deviceRequest;
    }

    @Override
    public void onCreateRequestClick() {
        mRequest.setDeviceRequests(mAdapter.getData());
        mPresenter.registerRequest(mRequest);
    }

    @Override
    public void onGetAssignedSuccess(List<Status> assignees) {
        updateAssignee(assignees);
    }

    private void updateAssignee(List<Status> assignees) {
        if (assignees == null) {
            return;
        }
        mAssigners.clear();
        mAssigners.addAll(assignees);
        notifyPropertyChanged(BR.assigners);
    }

    @Override
    public void onGetRelativeSuccess(List<Status> relatives) {
        updateRelative(relatives);
    }

    @Override
    public void onGetCategorySuccess(List<Category> categories) {
        updateCategory(categories);
    }

    private void updateCategory(List<Category> categories) {
        if (categories == null) {
            return;
        }
        mCategories.clear();
        mCategories.addAll(categories);
        notifyPropertyChanged(BR.categories);
    }

    @Override
    public void onLoadError(String message) {
        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void hideProgressbar() {
        // TODO: later
    }

    @Override
    public void showProgressbar() {
        // TODO: later
    }

    @Override
    public void onAddItemClick() {
        mAdapter.addItem();
    }

    @Override
    public void onGetRequestSuccess(Request request) {
        mActivity.finish();
    }

    @Override
    public void onInputTitleError() {
        mTitleError = mContext.getString(R.string.msg_error_user_name);
        notifyPropertyChanged(BR.titleError);
    }

    @Override
    public void onInputDescriptionError() {
        mDescriptionError = mContext.getString(R.string.msg_error_user_name);
        notifyPropertyChanged(BR.descriptionError);
    }

    @Override
    public void onInputRelativeError() {
        mRelativeError = mContext.getString(R.string.msg_error_user_name);
        notifyPropertyChanged(BR.relativeError);
    }

    private void updateRelative(List<Status> relatives) {
        if (relatives == null) {
            return;
        }
        mRelatives.clear();
        mRelatives.addAll(relatives);
    }

    @Bindable
    public String getRequestTitle() {
        return mRequestTitle;
    }

    public void setRequestTitle(String requestTitle) {
        mRequest.setTitle(requestTitle);
        mRequestTitle = requestTitle;
    }

    @Bindable
    public String getRequestDescription() {
        return mRequestDescription;
    }

    public void setRequestDescription(String requestDescription) {
        mRequest.setDescription(requestDescription);
        mRequestDescription = requestDescription;
    }

    @Bindable
    public Status getAssignee() {
        return mAssignee;
    }

    public void setAssignee(Status assignee) {
        mRequest.setAssigneeId(assignee.getId());
        mAssignee = assignee;
        notifyPropertyChanged(BR.assignee);
    }

    @Bindable
    public Status getRelative() {
        return mRelative;
    }

    public void setRelative(Status relative) {
        mRequest.setForUserId(relative.getId());
        mRelative = relative;
        notifyPropertyChanged(BR.relative);
    }

    @Bindable
    public Category getCategory() {
        return mCategory;
    }

    public void setCategory(Category category) {
        mDeviceRequest.setCategory(category);
        mDeviceRequest.setDeviceCategoryId(category.getId());
        mCategory = category;
        notifyPropertyChanged(BR.category);
    }

    public ArrayAdapter<Category> getAdapterCategory() {
        return mAdapterCategory;
    }

    public RequestCreationAdapter getAdapter() {
        return mAdapter;
    }

    @Bindable
    public String getTitleError() {
        return mTitleError;
    }

    @Bindable
    public String getDescriptionError() {
        return mDescriptionError;
    }

    @Bindable
    public String getRelativeError() {
        return mRelativeError;
    }

    @Bindable
    public List<Status> getAssigners() {
        return mAssigners;
    }

    @Bindable
    public List<Category> getCategories() {
        return mCategories;
    }
}
