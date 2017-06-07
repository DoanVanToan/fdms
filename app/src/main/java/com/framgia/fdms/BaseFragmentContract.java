package com.framgia.fdms;

/**
 * Created by beepi on 09/05/2017.
 */

import android.support.v7.widget.RecyclerView;
import com.framgia.fdms.data.model.Status;
import java.util.List;

/**
 * This contract defines all method in {@link BaseFragmentModel} and {@link BaseFragmentContract.Presenter}
 * to get all data from server into {@link RecyclerView} with load more
 */
public interface BaseFragmentContract {
    interface ViewModel extends BaseViewModel<BaseFragmentContract.Presenter> {

        void showProgressbar();

        void hideProgressbar();
    }

    interface Presenter extends BasePresenter {
        void onLoadMore();

        void getData(Status relative, Status status);
    }
}
