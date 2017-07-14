package com.framgia.fdms.screen.tutorial;

import com.framgia.fdms.BasePresenter;
import com.framgia.fdms.BaseViewModel;

/**
 * This specifies the contract between the view and the presenter.
 */
interface TutorialContract {
    /**
     * View.
     */
    interface ViewModel extends BaseViewModel<Presenter> {
        void onSkipClick();
    }

    /**
     * Presenter.
     */
    interface Presenter extends BasePresenter {
    }
}
