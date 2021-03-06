package com.framgia.fdms.screen.request.requestmanager;

import android.content.Context;
import android.content.Intent;
import android.databinding.Bindable;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import com.framgia.fdms.BR;
import com.framgia.fdms.BaseFragmentContract;
import com.framgia.fdms.BaseFragmentModel;
import com.framgia.fdms.R;
import com.framgia.fdms.data.model.Request;
import com.framgia.fdms.data.model.Respone;
import com.framgia.fdms.data.model.Status;
import com.framgia.fdms.data.model.User;
import com.framgia.fdms.screen.assignment.AssignmentActivity;
import com.framgia.fdms.screen.request.OnRequestClickListenner;
import com.framgia.fdms.screen.request.userrequest.UserRequestAdapter;
import com.framgia.fdms.screen.requestdetail.RequestDetailActivity;
import com.framgia.fdms.screen.selection.StatusSelectionActivity;
import com.framgia.fdms.screen.selection.StatusSelectionType;
import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;
import static com.framgia.fdms.screen.selection.StatusSelectionAdapter.FIRST_INDEX;
import static com.framgia.fdms.utils.Constant.ACTION_CLEAR;
import static com.framgia.fdms.utils.Constant.BundleConstant.BUNDLE_RESPONE;
import static com.framgia.fdms.utils.Constant.BundleConstant.BUNDLE_STATUE;
import static com.framgia.fdms.utils.Constant.OUT_OF_INDEX;
import static com.framgia.fdms.utils.Constant.RequestConstant.REQUEST_CREATE_ASSIGNMENT;
import static com.framgia.fdms.utils.Constant.RequestConstant.REQUEST_DETAIL;
import static com.framgia.fdms.utils.Constant.RequestConstant.REQUEST_SELECTION;
import static com.framgia.fdms.utils.Constant.RequestConstant.REQUEST_STATUS;

/**
 * Exposes the data to be used in the RequestManager screen.
 */

public class RequestManagerViewModel extends BaseFragmentModel
        implements RequestManagerContract.ViewModel, OnRequestClickListenner {

    private final Fragment mFragment;
    private Context mContext;
    private UserRequestAdapter mAdapter;

    private List<Status> mStatuses = new ArrayList<>();
    private List<Status> mRelatives = new ArrayList<>();
    private Status mStatus;
    private Status mRelative;
    private boolean mIsRefresh;
    private int mEmptyViewVisible = View.GONE; // show empty state when no date

    public RequestManagerViewModel(Fragment fragment) {
        mFragment = fragment;
        mContext = fragment.getContext();
        mAdapter = new UserRequestAdapter(mContext, new ArrayList<Request>(), this, new User());
        setStatus(new Status(OUT_OF_INDEX, mContext.getString(R.string.title_request_status)));
        setRelative(new Status(OUT_OF_INDEX, mContext.getString(R.string.title_request_relative)));
    }

    @Override
    public void onStart() {
        if (mPresenter == null) return;
        mPresenter.onStart();
    }

    @Override
    public void onStop() {
        mPresenter.onStop();
    }

    public void setPresenter(BaseFragmentContract.Presenter presenter) {
        mPresenter = presenter;
    }

    public void setAdapter(UserRequestAdapter adapter) {
        mAdapter = adapter;
    }

    public UserRequestAdapter getAdapter() {
        return mAdapter;
    }

    @Override
    public void onGetRequestSuccess(List<Request> requests) {
        setEmptyViewVisible(requests.isEmpty() ? View.VISIBLE : View.GONE);
        mAdapter.onUpdatePage(requests);
    }

    @Override
    public void onGetRequestError() {
        setEmptyViewVisible(View.VISIBLE);
    }

    @Override
    public void onGetStatusSuccess(List<Status> statuses) {
        updateStatus(statuses);
    }

    @Override
    public void onGetRelativeSuccess(List<Status> relatives) {
        updateRelative(relatives);
    }

    @Override
    public void onLoadError(String msg) {
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getData() {
        mPresenter.getData(null, null);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null || data.getExtras() == null || resultCode != RESULT_OK) {
            return;
        }
        Bundle bundle = data.getExtras();
        switch (requestCode) {
            case REQUEST_SELECTION:
                Status relative = bundle.getParcelable(BUNDLE_STATUE);
                if (relative != null) {
                    if (relative.getName().equals(mContext.getString(R.string.action_clear))) {
                        relative.setName(mContext.getString(R.string.title_request_relative));
                    }
                    setRelative(relative);
                    mAdapter.clear();
                    mPresenter.getData(mRelative, mStatus);
                }
                break;
            case REQUEST_STATUS:
                Status status = bundle.getParcelable(BUNDLE_STATUE);
                if (status != null) {
                    if (status.getName().equals(mContext.getString(R.string.action_clear))) {
                        status.setName(mContext.getString(R.string.title_request_status));
                    }
                    setStatus(status);
                    mAdapter.clear();
                    mPresenter.getData(mRelative, mStatus);
                }
                break;
            case REQUEST_DETAIL:
                Respone<Request> requestRespone =
                        (Respone<Request>) bundle.getSerializable(BUNDLE_RESPONE);
                if (requestRespone != null) {
                    onUpdateActionSuccess(requestRespone);
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onUpdateActionSuccess(Respone<Request> requestRespone) {
        if (requestRespone == null || requestRespone.getData() == null) return;
        mAdapter.updateItem(requestRespone.getData());
        Snackbar.make(mFragment.getView(), requestRespone.getMessage(), Snackbar.LENGTH_LONG)
                .show();
    }

    @Override
    public void refreshData() {
        mAdapter.clear();
        mPresenter.getData(mRelative, mStatus);
    }

    @Override
    public void setCurrentUser(User user) {
        if (user == null) return;
        mAdapter.updateUser(user);
    }

    public void onSelectStatusClick() {
        if (mStatuses == null) return;
        mFragment.startActivityForResult(
                StatusSelectionActivity.getInstance(mContext, null, mStatuses,
                        StatusSelectionType.STATUS), REQUEST_STATUS);
    }

    public void onSelectRelativeClick() {
        if (mRelatives == null) return;
        mFragment.startActivityForResult(
                StatusSelectionActivity.getInstance(mContext, null, mRelatives,
                        StatusSelectionType.STATUS), REQUEST_SELECTION);
    }

    public void updateStatus(List<Status> list) {
        if (list == null) {
            return;
        }
        mStatuses = list;
        mStatuses.add(FIRST_INDEX, new Status(OUT_OF_INDEX, ACTION_CLEAR));
    }

    public void updateRelative(List<Status> relatives) {
        if (relatives == null) {
            return;
        }
        mRelatives = relatives;
        mRelatives.add(FIRST_INDEX, new Status(OUT_OF_INDEX, ACTION_CLEAR));
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
    public Status getRelative() {
        return mRelative;
    }

    public void setRelative(Status relative) {
        mRelative = relative;
        notifyPropertyChanged(BR.relative);
    }

    @Override
    public void onMenuClick(View v, final UserRequestAdapter.RequestModel request) {
        if (request == null
                || request.getRequest() == null
                || request.getRequest().getRequestActionList() == null) {
            return;
        }

        Request requestModel = request.getRequest();
        PopupMenu popupMenu = new PopupMenu(v.getContext(), v);
        for (int i = 0; i < requestModel.getRequestActionList().size(); i++) {
            final Request.RequestAction action = requestModel.getRequestActionList().get(i);
            popupMenu.getMenu()
                    .add(action.getName())
                    .setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem menuItem) {
                            ((RequestManagerContract.Presenter) mPresenter).updateActionRequest(
                                    request.getRequest().getId(), action.getId());
                            return false;
                        }
                    });
        }
        popupMenu.show();
    }

    @Override
    public void onDetailRequestClick(Request request) {
        mFragment.startActivityForResult(RequestDetailActivity.newInstance(mContext, request),
                REQUEST_DETAIL);
    }

    @Override
    public void onAddDeviceClick(int requestId) {
        mFragment.startActivityForResult(AssignmentActivity.getInstance(mContext, requestId),
                REQUEST_CREATE_ASSIGNMENT);
    }

    @Bindable
    public boolean getIsRefresh() {
        return mIsRefresh;
    }

    @Override
    public void setRefresh(boolean isRefresh) {
        mIsRefresh = isRefresh;
        notifyPropertyChanged(BR.isRefresh);
    }

    private SwipeRefreshLayout.OnRefreshListener mRefreshLayout =
            new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    mAdapter.clear();
                    getData();
                }
            };

    public SwipeRefreshLayout.OnRefreshListener getRefreshLayout() {
        return mRefreshLayout;
    }

    @Bindable
    public int getEmptyViewVisible() {
        return mEmptyViewVisible;
    }

    public void setEmptyViewVisible(int emptyViewVisible) {
        mEmptyViewVisible = emptyViewVisible;
        notifyPropertyChanged(BR.emptyViewVisible);
    }
}
