package com.framgia.fdms.screen.requestcreation;

import android.content.Intent;
import com.framgia.fdms.BasePresenter;
import com.framgia.fdms.BaseViewModel;
import com.framgia.fdms.data.model.Category;
import com.framgia.fdms.data.model.Device;
import com.framgia.fdms.data.model.Request;
import com.framgia.fdms.data.model.Status;
import com.framgia.fdms.data.source.api.request.DeviceRequest;
import com.framgia.fdms.data.source.api.request.RequestCreatorRequest;
import java.util.List;

/**
 * This specifies the contract between the view and the presenter.
 */
public interface RequestCreationContract {
    /**
     * View.
     */
    interface ViewModel extends BaseViewModel<Presenter> {
        void onRelativeClick();

        void onAssigneeClick();

        void onCreateRequestClick();

        void onGetAssignedSuccess(List<Status> assignees);

        void onGetRelativeSuccess(List<Status> relatives);

        void onGetCategorySuccess(List<Category> categories);

        void onLoadError(String message);

        void hideProgressbar();

        void showProgressbar();

        void onGetRequestSuccess(Request request);

        void onInputTitleError();

        void onInputDescriptionError();

        void onInputRelativeError();

        void onActivityResult(int requestCode, int resultCode, Intent data);

        void onAddItemClick();

        void onCategoryClick(DeviceRequest deviceRequest);
    }

    /**
     * Presenter.
     */
    interface Presenter extends BasePresenter {
        void getListAssign();

        void getListRelative();

        void getListCategory();

        void registerRequest(RequestCreatorRequest request);

        boolean validateDataInput(RequestCreatorRequest request);
    }
}
