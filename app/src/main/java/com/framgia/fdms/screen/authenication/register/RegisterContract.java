package com.framgia.fdms.screen.authenication.register;

import com.framgia.fdms.BasePresenter;
import com.framgia.fdms.BaseViewModel;
import com.framgia.fdms.data.source.api.request.RegisterRequest;

/**
 * This specifies the contract between the view and the presenter.
 */
interface RegisterContract {
    /**
     * View.
     */
    interface ViewModel extends BaseViewModel<RegisterContract.Presenter> {
        void onRegisterError();

        void onRegisterSuccess();

        void onInputUserNameError();

        void onInputPasswordError();

        void onInputConfirmPasswordError();

        void onInputFirstnameError();

        void onInputLastnameError();

        void onInputAddressError();

        void onInputRoleError();

        void onInputDepartmentError();
    }

    interface Presenter extends BasePresenter {
        void register(RegisterRequest request);

        boolean validateDataInput(RegisterRequest request);
    }
}
