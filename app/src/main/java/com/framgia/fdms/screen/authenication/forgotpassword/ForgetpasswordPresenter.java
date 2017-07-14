package com.framgia.fdms.screen.authenication.forgotpassword;

import android.text.TextUtils;

/**
 * Listens to user actions from the UI ({@link ForgotpasswordActivity}), retrieves the data and
 * updates
 * the UI as required.
 */
final class ForgetpasswordPresenter implements ForgetpasswordContract.Presenter {
    private static final String TAG = ForgetpasswordPresenter.class.getName();
    private static final String EMAIL_REGEX =
            "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)"
                    + "+[a-zA-Z]{2,6}$";
    private final ForgetpasswordContract.ViewModel mViewModel;

    public ForgetpasswordPresenter(ForgetpasswordContract.ViewModel viewModel) {
        mViewModel = viewModel;
    }

    @Override
    public void onStart() {
        //TODO dev later
    }

    @Override
    public void onStop() {
        //TODO dev later
    }

    @Override
    public boolean validateDataInput(String email) {
        if (TextUtils.isEmpty(email)) {
            mViewModel.onInputEmailError();
            return false;
        }
        if (!email.matches(EMAIL_REGEX)) {
            mViewModel.onInputFormatEmailError();
            return false;
        }
        return true;
    }
}
