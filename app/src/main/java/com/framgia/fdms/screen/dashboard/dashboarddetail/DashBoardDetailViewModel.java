package com.framgia.fdms.screen.dashboard.dashboarddetail;

import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import com.framgia.fdms.BR;
import com.framgia.fdms.R;
import com.framgia.fdms.data.model.Dashboard;
import com.framgia.fdms.data.model.Device;
import com.framgia.fdms.data.model.Request;
import com.framgia.fdms.data.model.Respone;
import com.framgia.fdms.data.model.User;
import com.framgia.fdms.screen.request.OnRequestClickListenner;
import com.framgia.fdms.screen.request.userrequest.UserRequestAdapter;
import com.framgia.fdms.screen.requestdetail.RequestDetailActivity;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;
import static com.framgia.fdms.screen.dashboard.dashboarddetail.DashBoardDetailFragment
        .DEVICE_DASHBOARD;
import static com.framgia.fdms.screen.dashboard.dashboarddetail.DashBoardDetailFragment
        .REQUEST_DASHBOARD;
import static com.framgia.fdms.utils.Constant.BundleConstant.BUNDLE_RESPONE;
import static com.framgia.fdms.utils.Constant.RequestConstant.REQUEST_DETAIL;

/**
 * Exposes the data to be used in the Scanner screen.
 */

public class DashBoardDetailViewModel extends BaseObservable
        implements DashBoardDetailContract.ViewModel, OnRequestClickListenner {

    private static final float PIE_DATA_SLICE_SPACE = 3f;
    private static final float PIE_DATA_SLECTION_SHIFT = 5f;
    private DashBoardDetailContract.Presenter mPresenter;
    private PieData mPieData;
    private Context mContext;
    private int mTotal;
    private UserRequestAdapter mAdapterTopRequest;
    private TopDeviceAdapter mAdapterTopDevice;
    private String mDashboardTitle;
    private int mEmptyViewVisible = View.GONE;
    private int mDashboardType;
    private Fragment mFragment;
    private boolean mIsRefresh;

    public DashBoardDetailViewModel(Fragment fragment, int dashboardType) {
        mFragment = fragment;
        mContext = fragment.getContext();
        mPieData = new PieData();
        mAdapterTopRequest =
                new UserRequestAdapter(mContext, new ArrayList<Request>(), this, new User());
        mAdapterTopDevice = new TopDeviceAdapter(mContext, new ArrayList<Device>());
        initDashboardTitle(dashboardType);
        mDashboardType = dashboardType;
    }

    private void initDashboardTitle(int dashboardType) {
        if (dashboardType == DEVICE_DASHBOARD) {
            setDashboardTitle(mContext.getString(R.string.title_top_devices));
        } else if (dashboardType == REQUEST_DASHBOARD) {
            setDashboardTitle(mContext.getString(R.string.title_top_requests));
        }
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
    public void setPresenter(DashBoardDetailContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null || data.getExtras() == null || resultCode != RESULT_OK) {
            return;
        }
        Bundle bundle = data.getExtras();

        if (requestCode == REQUEST_DETAIL) {
            Respone<Request> requestRespone =
                    (Respone<Request>) bundle.getSerializable(BUNDLE_RESPONE);
            if (requestRespone != null) {
                onUpdateActionSuccess(requestRespone);
            }
        }
    }

    @Override
    public void setCurrentUser(User user) {
        if (user == null) return;
        mAdapterTopRequest.updateUser(user);
    }

    @Bindable
    public PieData getPieData() {
        return mPieData;
    }

    public void setPieData(PieData pieData) {
        mPieData = pieData;
        notifyPropertyChanged(BR.pieData);
    }

    @Override
    public void setDataSet(PieDataSet dataSet) {
        mPieData = new PieData(dataSet);
        setPieData(mPieData);
    }

    @Override
    public void onDashBoardError(String error) {
        Toast.makeText(mContext, error, Toast.LENGTH_LONG).show();
        setEmptyViewVisible(View.VISIBLE);
    }

    @Override
    public void onDashBoardLoaded(List<Dashboard> dashboards) {
        int total = 0;
        List<Integer> colors = new ArrayList<Integer>();
        List<PieEntry> values = new ArrayList<PieEntry>();

        if (dashboards != null && dashboards.size() != 0) {
            setEmptyViewVisible(View.GONE);
        } else {
            setEmptyViewVisible(View.VISIBLE);
        }

        for (Dashboard dashboard : dashboards) {
            total += dashboard.getCount();
        }

        for (int i = 0; i < dashboards.size(); i++) {
            Dashboard dashboard = dashboards.get(i);
            float percent = (float) dashboard.getCount() / total * 100f;
            values.add(new PieEntry(percent, dashboard.getTitle(), i));
            colors.add(Color.parseColor(dashboard.getBackgroundColor()));
        }

        PieDataSet dataSet = new PieDataSet(values, mContext.getString(R.string.title_chart));
        dataSet.setSliceSpace(PIE_DATA_SLICE_SPACE);
        dataSet.setSelectionShift(PIE_DATA_SLECTION_SHIFT);
        dataSet.setColors(colors);

        setDataSet(dataSet);
        setTotal(total);
    }

    @Override
    public void getData() {
        mPresenter.getData();
    }

    @Override
    public void onGetTopRequestSuccess(List<Request> requests) {
        // TODO: 29/05/2017
        if (requests == null) return;
        mAdapterTopRequest.onUpdatePage(requests);
    }

    @Override
    public void onGetTopDeviceSuccess(List<Device> devices) {
        // TODO: 29/05/2017
        if (devices == null) return;
        mAdapterTopDevice.onUpdatePage(devices);
    }

    @Override
    public void onUpdateActionSuccess(Respone<Request> requestRespone) {
        if (requestRespone == null || requestRespone.getData() == null) return;
        mAdapterTopRequest.updateItem(requestRespone.getData());
        Snackbar.make(mFragment.getView(), requestRespone.getMessage(), Snackbar.LENGTH_LONG)
                .show();
    }

    @Override
    public void showProgressbar() {
        // TODO: 08/06/2017
    }

    @Override
    public void hideProgressbar() {
        // TODO: 08/06/2017
    }

    public void setTotal(int total) {
        mTotal = total;
        notifyPropertyChanged(BR.total);
    }

    @Bindable
    public int getTotal() {
        return mTotal;
    }

    public UserRequestAdapter getAdapterTopRequest() {
        return mAdapterTopRequest;
    }

    public TopDeviceAdapter getAdapterTopDevice() {
        return mAdapterTopDevice;
    }

    @Bindable
    public String getDashboardTitle() {
        return mDashboardTitle;
    }

    public void setDashboardTitle(String dashboardTitle) {
        mDashboardTitle = dashboardTitle;
        notifyPropertyChanged(BR.dashboardTitle);
    }

    @Bindable
    public int getEmptyViewVisible() {
        return mEmptyViewVisible;
    }

    public void setEmptyViewVisible(int emptyViewVisible) {
        mEmptyViewVisible = emptyViewVisible;
        notifyPropertyChanged(BR.emptyViewVisible);
    }

    public int getDashboardType() {
        return mDashboardType;
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
                            // TODO: 22/05/2017 update request status
                            mPresenter.updateActionRequest(request.getRequest().getId(),
                                    action.getId());
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

    }

    @Bindable
    public boolean isRefresh() {
        return mIsRefresh;
    }

    public void setRefresh(boolean refresh) {
        mIsRefresh = refresh;
        notifyPropertyChanged(BR.refresh);
    }

    private SwipeRefreshLayout.OnRefreshListener mRefreshLayout =
            new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    mAdapterTopDevice.clear();
                    mAdapterTopRequest.clear();
                    getData();
                }
            };

    public SwipeRefreshLayout.OnRefreshListener getRefreshLayout() {
        return mRefreshLayout;
    }
}
