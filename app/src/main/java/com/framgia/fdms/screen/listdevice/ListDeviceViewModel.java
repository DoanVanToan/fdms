package com.framgia.fdms.screen.listdevice;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;
import com.android.databinding.library.baseAdapters.BR;
import com.framgia.fdms.R;
import com.framgia.fdms.data.model.Category;
import com.framgia.fdms.data.model.Device;
import com.framgia.fdms.data.model.Status;
import com.framgia.fdms.data.model.User;
import com.framgia.fdms.screen.devicecreation.CreateDeviceActivity;
import com.framgia.fdms.screen.devicecreation.DeviceStatusType;
import com.framgia.fdms.screen.devicedetail.DeviceDetailActivity;
import com.framgia.fdms.screen.returndevice.ReturnDeviceActivity;
import com.framgia.fdms.screen.selection.StatusSelectionActivity;
import com.framgia.fdms.screen.selection.StatusSelectionType;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import java.util.ArrayList;
import java.util.List;

import static com.framgia.fdms.utils.Constant.BundleConstant.BUNDLE_CATEGORY;
import static com.framgia.fdms.utils.Constant.BundleConstant.BUNDLE_STATUE;
import static com.framgia.fdms.utils.Constant.OUT_OF_INDEX;
import static com.framgia.fdms.utils.Constant.RequestConstant.REQUEST_SELECTION;
import static com.framgia.fdms.utils.Constant.Role.BO_MANAGER;
import static com.framgia.fdms.utils.Constant.Role.BO_STAFF;

/**
 * Exposes the data to be used in the ListDevice screen.
 */

public class ListDeviceViewModel extends BaseObservable implements ListDeviceContract.ViewModel {
    private final Fragment mFragment;
    private ObservableField<Integer> mProgressBarVisibility = new ObservableField<>();
    private ObservableBoolean mIsLoadingMore = new ObservableBoolean(false);
    private ListDeviceAdapter mAdapter;
    private ListDeviceContract.Presenter mPresenter;
    private Context mContext;
    private FragmentActivity mActivity;
    private List<Category> mCategories;
    private List<Status> mStatuses;
    private Category mCategory;
    private Status mStatus;
    private String mKeyWord;
    private boolean mIsBo = false;

    public ObservableBoolean getIsLoadingMore() {
        return mIsLoadingMore;
    }

    public ListDeviceViewModel(FragmentActivity activity, Fragment fragment) {
        mFragment = fragment;
        mActivity = activity;
        mContext = activity.getApplicationContext();
        mAdapter = new ListDeviceAdapter(mContext, new ArrayList<Device>(), this);

        setCategory(new Category(OUT_OF_INDEX, mContext.getString(R.string.title_btn_category)));
        setStatus(new Status(OUT_OF_INDEX, mContext.getString(R.string.title_request_status)));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode != REQUEST_SELECTION
                || data == null
                || data.getExtras() == null
                || resultCode != Activity.RESULT_OK) {
            return;
        }
        Bundle bundle = data.getExtras();
        Category category = bundle.getParcelable(BUNDLE_CATEGORY);
        Status status = bundle.getParcelable(BUNDLE_STATUE);
        if (category != null) {
            if (category.getName().equals(mContext.getString(R.string.action_clear))) {
                category.setName(mContext.getString(R.string.title_btn_category));
            }
            setCategory(category);
            mAdapter.clear();
        }
        if (status != null) {
            if (status.getName().equals(mContext.getString(R.string.action_clear))) {
                status.setName(mContext.getString(R.string.title_request_status));
            }
            setStatus(status);
            mAdapter.clear();
        }
        mPresenter.getData(mKeyWord, mCategory, mStatus);
    }

    @Override
    public void setupFloatingActionsMenu(User user) {
        String role = user.getRole();
        if (role == null) return;

        setBo(user.isBo());
    }

    @Override
    public void onChooseCategory() {
        if (mCategories == null) return;
        mFragment.startActivityForResult(
                StatusSelectionActivity.getInstance(mContext, mCategories, null,
                        StatusSelectionType.CATEGORY), REQUEST_SELECTION);
    }

    @Override
    public void onChooseStatus() {
        if (mStatuses == null) return;
        mFragment.startActivityForResult(
                StatusSelectionActivity.getInstance(mContext, null, mStatuses,
                        StatusSelectionType.STATUS), REQUEST_SELECTION);
    }

    @Override
    public void onReset() {
        mAdapter.clear();
        mPresenter.getData(null, mCategory, mStatus);
    }

    @Override
    public void getData() {
        mPresenter.getData(null, null, null);
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
    public void setPresenter(ListDeviceContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onDeviceLoaded(List<Device> devices) {
        mIsLoadingMore.set(false);
        mAdapter.onUpdatePage(devices);
    }

    @Override
    public void onDeviceClick(Device device) {
        mContext.startActivity(DeviceDetailActivity.getInstance(mContext, device.getId()));
    }

    @Override
    public void showProgressbar() {
        mProgressBarVisibility.set(View.VISIBLE);
    }

    @Override
    public void onError(String msg) {
        mIsLoadingMore.set(false);
        hideProgressbar();
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void hideProgressbar() {
        mProgressBarVisibility.set(View.GONE);
    }

    @Override
    public void onRegisterDeviceClick(FloatingActionsMenu actionsMenu) {
        actionsMenu.collapse();
        mContext.startActivity(CreateDeviceActivity.getInstance(mContext, DeviceStatusType.CREATE));
    }

    @Override
    public void onStartReturnDevice(FloatingActionsMenu actionsMenu) {
        actionsMenu.collapse();
        mContext.startActivity(ReturnDeviceActivity.newIntent(mContext));
    }

    @Override
    public void onDeviceCategoryLoaded(List<Category> categories) {
        updateCategory(categories);
    }

    @Override
    public void onDeviceStatusLoaded(List<Status> statuses) {
        updateStatus(statuses);
    }

    @Override
    public void onSearch(String keyWord) {
        mAdapter.clear();
        mKeyWord = keyWord;
        mPresenter.getData(mKeyWord, mCategory, mStatus);
    }

    public void updateCategory(List<Category> list) {
        if (list == null) {
            return;
        }
        // update list mCategories
        mCategories = list;
    }

    public void updateStatus(List<Status> list) {
        if (list == null) {
            return;
        }
        // update list statuses
        mStatuses = list;
    }

    public ObservableField<Integer> getProgressBarVisibility() {
        return mProgressBarVisibility;
    }

    private RecyclerView.OnScrollListener mScrollListenner = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            if (dy <= 0) {
                return;
            }

            LinearLayoutManager layoutManager =
                    (LinearLayoutManager) recyclerView.getLayoutManager();

            int visibleItemCount = layoutManager.getChildCount();
            int totalItemCount = layoutManager.getItemCount();
            int pastVisiblesItems = layoutManager.findFirstVisibleItemPosition();

            if (!mIsLoadingMore.get() && (visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                mIsLoadingMore.set(true);
                mPresenter.loadMoreData();
            }
        }
    };

    public RecyclerView.OnScrollListener getScrollListenner() {
        return mScrollListenner;
    }

    @Bindable
    public ListDeviceAdapter getAdapter() {
        return mAdapter;
    }

    @Bindable
    public Category getCategory() {
        return mCategory;
    }

    public void setCategory(Category category) {
        mCategory = category;
        notifyPropertyChanged(BR.category);
    }

    @Bindable
    public Status getStatus() {
        return mStatus;
    }

    public void setStatus(Status status) {
        mStatus = status;
        notifyPropertyChanged(BR.status);
    }

    @Bindable
    public boolean isBo() {
        return mIsBo;
    }

    public void setBo(boolean bo) {
        mIsBo = bo;
        notifyPropertyChanged(BR.bo);
    }
}
