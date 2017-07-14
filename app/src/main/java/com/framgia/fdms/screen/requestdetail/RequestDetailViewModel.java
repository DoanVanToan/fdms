package com.framgia.fdms.screen.requestdetail;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;
import com.android.databinding.library.baseAdapters.BR;
import com.framgia.fdms.R;
import com.framgia.fdms.data.model.Category;
import com.framgia.fdms.data.model.Request;
import com.framgia.fdms.data.model.Respone;
import com.framgia.fdms.data.model.User;
import com.framgia.fdms.data.source.CategoryRepository;
import com.framgia.fdms.data.source.api.service.FDMSServiceClient;
import com.framgia.fdms.data.source.remote.CategoryRemoteDataSource;
import com.framgia.fdms.screen.assignment.AssignmentActivity;
import com.framgia.fdms.screen.selection.StatusSelectionActivity;
import com.framgia.fdms.screen.selection.StatusSelectionType;
import com.framgia.fdms.utils.Constant;
import com.framgia.fdms.widget.FdmsProgressDialog;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import java.util.ArrayList;
import java.util.List;
import rx.subscriptions.CompositeSubscription;

import static android.app.Activity.RESULT_OK;
import static com.framgia.fdms.utils.Constant.BundleConstant.BUNDLE_CATEGORY;
import static com.framgia.fdms.utils.Constant.BundleConstant.BUNDLE_RESPONE;
import static com.framgia.fdms.utils.Constant.RequestAction.APPROVED;
import static com.framgia.fdms.utils.Constant.RequestAction.CANCEL;
import static com.framgia.fdms.utils.Constant.RequestAction.DONE;
import static com.framgia.fdms.utils.Constant.RequestAction.WAITING_APPROVE;
import static com.framgia.fdms.utils.Constant.RequestAction.WAITING_DONE;
import static com.framgia.fdms.utils.Constant.RequestConstant.REQUEST_CATEGORY;
import static com.framgia.fdms.utils.Constant.RequestConstant.REQUEST_CREATE_ASSIGNMENT;
import static com.framgia.fdms.utils.Utils.hideSoftKeyboard;
import static com.github.clans.fab.FloatingActionButton.SIZE_MINI;

/**
 * Created by tuanbg on 5/24/17.
 */

public class RequestDetailViewModel extends BaseObservable
        implements RequestDetailContract.ViewModel {
    private Context mContext;
    private FragmentActivity mActivity;
    private ObservableBoolean mIsEdit = new ObservableBoolean();
    private RequestDetailAdapter mAdapter;
    private CategoryRepository mCategoryRepository;
    private CompositeSubscription mSubscription;
    private List<Category> mCategories = new ArrayList<>();
    private RequestDetailContract.Presenter mPresenter;
    private ObservableField<Category> mCategory = new ObservableField<>();
    private List<Request.RequestAction> mListAction = new ArrayList<>();
    private FloatingActionMenu mActionMenu;
    private String mStatusRequest;
    private Request mRequest;
    private int mPosition;
    private FdmsProgressDialog mProgressDialog;
    private int mProgressBarVisibility = View.GONE;
    private User mUser;
    private FloatingActionMenu mFloatingActionsMenu;
    private Request mRequestTemp;
    private int mActionMenuVisibility;

    public RequestDetailViewModel(AppCompatActivity activity, List<Request.DeviceRequest> request,
            List<Request.RequestAction> actions, String statusRequest, Request actionRequest,
            FloatingActionMenu floatingActionsMenu) {
        mContext = activity;
        mActivity = activity;
        setRequest(actionRequest);
        mStatusRequest = statusRequest;
        mIsEdit.set(false);
        mAdapter = new RequestDetailAdapter(activity, this);
        mAdapter.onUpdatePage(request);
        mSubscription = new CompositeSubscription();
        mListAction.addAll(actions);
        mCategoryRepository = new CategoryRepository(
                new CategoryRemoteDataSource(FDMSServiceClient.getInstance()));
        mFloatingActionsMenu = floatingActionsMenu;
    }

    @Override
    public void showProgressbar() {
        setProgressBarVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressbar() {
        setProgressBarVisibility(View.GONE);
    }

    @Override
    public void onLoadError(String message) {
        Toast.makeText(mActivity, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onGetCategorySuccess(List<Category> categories) {
        if (categories == null) {
            return;
        }
        mCategories.clear();
        mCategories.addAll(categories);
    }

    public ObservableBoolean getIsEdit() {
        return mIsEdit;
    }

    @Override
    public void onStart() {
        mPresenter.onStart();
    }

    @Override
    public void onStop() {
    }

    @Override
    public void setPresenter(RequestDetailContract.Presenter presenter) {
        mPresenter = presenter;
    }

    public void removeAt(int pos) {
        mAdapter.removeAt(pos);
    }

    public void addAtItem() {
        mAdapter.addItem();
    }

    public void onCategoryClick() {
        if (mCategories == null) return;
        mActivity.startActivityForResult(
                StatusSelectionActivity.getInstance(mContext, mCategories, null,
                        StatusSelectionType.CATEGORY), REQUEST_CATEGORY);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK || data == null) return;
        switch (requestCode) {
            case REQUEST_CATEGORY:
                Bundle bundle = data.getExtras();
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
    public void onSubmitEditClick() {
        mPresenter.updateRequest(mRequest);
        mActionMenu.showMenu(true);
        mIsEdit.set(false);
        hideSoftKeyboard(mActivity);
    }

    @Override
    public void onCancelEditClick() {
        mIsEdit.set(false);
        mActionMenu.showMenu(true);
        if (mRequestTemp != null) {
            setRequest(mRequestTemp);
            mAdapter.clear();
            mAdapter.onUpdatePage(mRequestTemp.getDevices());
        }
        hideSoftKeyboard(mActivity);
    }

    @Override
    public boolean onBackPressed() {
        if (mIsEdit.get()) {
            mIsEdit.set(false);
            return false;
        }
        return true;
    }

    public void initFloatActionButton(final FloatingActionMenu menu) {
        mActionMenu = menu;
        for (int i = 0; i < mListAction.size(); i++) {
            mPosition = i;
            FloatingActionButton button = new FloatingActionButton(mContext);
            button.setLabelText(mListAction.get(i).getName());
            switch (mListAction.get(i).getId()) {
                case CANCEL:
                    button.setImageResource(R.drawable.ic_cancel_white_24px);
                    break;
                case WAITING_APPROVE:
                    button.setImageResource(R.drawable.ic_timer);
                    break;
                case APPROVED:
                    button.setImageResource(R.drawable.ic_done_white_24dp);
                    break;
                case WAITING_DONE:
                    button.setImageResource(R.drawable.ic_timer);
                    break;
                case DONE:
                    button.setImageResource(R.drawable.ic_done_white_24dp);
                    break;
                default:
                    break;
            }
            button.setButtonSize(SIZE_MINI);
            menu.addMenuButton(button);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPresenter.updateActionRequest(mRequest.getId(),
                            mListAction.get(mPosition).getId());
                    menu.hideMenu(true);
                }
            });
        }
    }

    @Override
    public void onGetReponeSuccess(Respone<Request> requestRespone) {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putSerializable(BUNDLE_RESPONE, requestRespone);
        intent.putExtras(bundle);
        mActivity.setResult(RESULT_OK, intent);
        mActivity.finish();
    }

    @Override
    public void setCurrentUser(User user) {
        if (user == null) return;
        mUser = user;
        if (mUser.isBoStaff() && mRequest.getRequestStatus()
                .equals(Constant.DeviceStatus.WAITING_DONE) && mRequest.getId() > 0) {
            FloatingActionButton button = new FloatingActionButton(mContext);
            button.setImageResource(R.drawable.ic_timer);
            button.setLabelText(mContext.getString(R.string.title_add_device));
            button.setButtonSize(SIZE_MINI);
            mFloatingActionsMenu.addMenuButton(button);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mActivity.startActivityForResult(
                            AssignmentActivity.getInstance(mContext, mRequest.getId()),
                            REQUEST_CREATE_ASSIGNMENT);
                }
            });
            return;
        }

        setActionMenuVisibility(
                mListAction != null && mListAction.size() > 0 ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onUploadRequestError(String message) {
        Snackbar.make(mActivity.findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG)
                .show();
        onCancelEditClick();
    }

    public ObservableField<Category> getCategory() {
        return mCategory;
    }

    public void setCategory(Category category) {
        mCategory.set(category);
        notifyPropertyChanged(BR.category);
    }

    public void initEditRequest() {
        mIsEdit.set(true);
        mActionMenu.hideMenu(true);
        try {
            mRequestTemp = (Request) mRequest.clone();
            List<Request.DeviceRequest> list = new ArrayList<>();
            for (Request.DeviceRequest deviceRequest : mRequest.getDevices()) {
                list.add((Request.DeviceRequest) deviceRequest.clone());
            }
            mRequestTemp.setDevices(list);
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }

    public String getStatusRequest() {
        return mStatusRequest;
    }

    @Bindable
    public int getProgressBarVisibility() {
        return mProgressBarVisibility;
    }

    public void setProgressBarVisibility(int progressBarVisibility) {
        mProgressBarVisibility = progressBarVisibility;
        notifyPropertyChanged(BR.progressBarVisibility);
    }

    @Bindable
    public Request getRequest() {
        return mRequest;
    }

    public void setRequest(Request request) {
        mRequest = request;
        notifyPropertyChanged(BR.request);
    }

    @Bindable
    public RequestDetailAdapter getAdapter() {
        return mAdapter;
    }

    public void setAdapter(RequestDetailAdapter adapter) {
        mAdapter = adapter;
        notifyPropertyChanged(BR.adapter);
    }

    @Bindable
    public int getActionMenuVisibility() {
        return mActionMenuVisibility;
    }

    public void setActionMenuVisibility(int actionMenuVisibility) {
        this.mActionMenuVisibility = actionMenuVisibility;
        notifyPropertyChanged(BR.actionMenuVisibility);
    }
}
