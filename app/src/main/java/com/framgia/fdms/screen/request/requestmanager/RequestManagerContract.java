package com.framgia.fdms.screen.request.requestmanager;

import android.content.Intent;
import com.framgia.fdms.BaseFragmentContract;
import com.framgia.fdms.data.model.Request;
import com.framgia.fdms.data.model.Respone;
import com.framgia.fdms.data.model.Status;
import com.framgia.fdms.data.model.User;
import java.util.List;

/**
 * This specifies the contract between the view and the presenter.
 */
interface RequestManagerContract {
    /**
     * View.
     */
    interface ViewModel extends BaseFragmentContract.ViewModel {
        void onGetRequestSuccess(List<Request> requests);

        void onGetStatusSuccess(List<Status> statuses);

        void onGetRelativeSuccess(List<Status> relatives);

        void onLoadError(String msg);

        void getData();

        void onActivityResult(int requestCode, int resultCode, Intent data);

        void onUpdateActionSuccess(Respone<Request> requestRespone);

        void refreshData();

        void setCurrentUser(User user);

        void setRefresh(boolean isRefresh);

        void onGetRequestError();
    }

    /**
     * Presenter.
     */
    interface Presenter extends BaseFragmentContract.Presenter {
        void getRequest(int requestStatusId, int relativeId, int perPage, int page);

        void getStatusDevice();

        void getListRelative();

        void updateActionRequest(int requestId, int actionId);

        void getCurrentUser();
    }
}
