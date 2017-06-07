package com.framgia.fdms.screen.scanner;

import android.widget.FrameLayout;
import com.framgia.fdms.BasePresenter;
import com.framgia.fdms.BaseViewModel;

/**
 * This specifies the contract between the view and the presenter.
 */
interface ScannerContract {
    /**
     * View.
     */
    interface ViewModel extends BaseViewModel<Presenter> {
        void onResume();

        void onPause();

        void bindView(FrameLayout view);
    }

    /**
     * Presenter.
     */
    interface Presenter extends BasePresenter {
    }
}
