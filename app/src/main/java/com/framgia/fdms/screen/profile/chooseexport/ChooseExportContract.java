package com.framgia.fdms.screen.profile.chooseexport;

import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import com.framgia.fdms.BasePresenter;
import com.framgia.fdms.BaseViewModel;
import com.framgia.fdms.data.model.Device;
import java.util.List;

/**
 * Created by tuanbg on 6/15/17.
 */

public interface ChooseExportContract {
    interface ViewModel extends BaseViewModel<Presenter> {

        void showProgressbar();

        void hideProgressbar();

        void onError(String message);

        void onDeviceLoaded(List<Device> devices);

        void initToolbar(Toolbar toolbar);

        void onDeviceClick(Device device);
    }

    interface Presenter extends BasePresenter {
        void getListDevice();
    }
}
