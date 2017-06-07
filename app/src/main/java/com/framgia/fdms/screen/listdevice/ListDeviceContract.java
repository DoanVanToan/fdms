package com.framgia.fdms.screen.listdevice;

import android.content.Intent;
import com.framgia.fdms.BasePresenter;
import com.framgia.fdms.BaseViewModel;
import com.framgia.fdms.data.model.Category;
import com.framgia.fdms.data.model.Device;
import com.framgia.fdms.data.model.Status;
import com.framgia.fdms.data.model.User;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import java.util.List;

/**
 * This specifies the contract between the view and the presenter.
 */
interface ListDeviceContract {
    /**
     * View.
     */
    interface ViewModel extends BaseViewModel<Presenter> {
        void onDeviceLoaded(List<Device> devices);

        void onDeviceClick(Device device);

        void showProgressbar();

        void onError(String msg);

        void hideProgressbar();

        void onRegisterDeviceClick(FloatingActionsMenu actionsMenu);

        void onStartReturnDevice(FloatingActionsMenu actionsMenu);

        void onDeviceCategoryLoaded(List<Category> categories);

        void onDeviceStatusLoaded(List<Status> statuses);

        void onSearch(String keyWord);

        void onChooseCategory();

        void onChooseStatus();

        void onReset();

        void getData();

        void onActivityResult(int requestCode, int resultCode, Intent data);

        void setupFloatingActionsMenu(User user);
    }

    /**
     * Presenter.
     */
    interface Presenter extends BasePresenter {
        void loadMoreData();

        void getData(String keyWord, Category category, Status status);

        void getCurrentUser();
    }
}
