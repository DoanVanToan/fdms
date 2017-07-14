package com.framgia.fdms.screen.authenication.forgotpassword;

import com.framgia.fdms.BasePresenter;
import com.framgia.fdms.BaseViewModel;

/**
 * This specifies the contract between the view and the presenter.
 */
interface ForgetpasswordContract {
    /**
     * View.
     */
    interface ViewModel extends BaseViewModel<Presenter> {
        void onInputEmailError();

        void onInputFormatEmailError();
    }

    /**
     * Presenter.
     */
    interface Presenter extends BasePresenter {
        boolean validateDataInput(String email);
    }
}
