package com.framgia.fdms.screen.dashboard.dashboarddetail;

import android.content.Intent;
import com.framgia.fdms.BasePresenter;
import com.framgia.fdms.BaseViewModel;
import com.framgia.fdms.data.model.Dashboard;
import com.framgia.fdms.data.model.Device;
import com.framgia.fdms.data.model.Request;
import com.framgia.fdms.data.model.Respone;
import com.framgia.fdms.data.model.User;
import com.github.mikephil.charting.data.PieDataSet;
import java.util.List;

/**
 * This specifies the contract between the view and the presenter.
 */
interface DashBoardDetailContract {
    /**
     * View.
     */
    interface ViewModel extends BaseViewModel<Presenter> {
        void setDataSet(PieDataSet dataSet);

        void onDashBoardError(String error);

        void onDashBoardLoaded(List<Dashboard> dashboards);

        void getData();

        void onGetTopRequestSuccess(List<Request> requests);

        void onGetTopDeviceSuccess(List<Device> topDevices);

        void onUpdateActionSuccess(Respone<Request> requestRespone);

        void showProgressbar();

        void hideProgressbar();

        void onActivityResult(int requestCode, int resultCode, Intent data);

        void setCurrentUser(User user);

        void setRefresh(boolean isRefresh);
    }

    /**
     * Presenter.
     */
    interface Presenter extends BasePresenter {
        void getDeviceDashboard();

        void getRequestDashboard();

        void getData();

        void getTopRequest();

        void getTopDevice();

        void updateActionRequest(int requestId, int actionId);

        void getCurrentUser();
    }
}
