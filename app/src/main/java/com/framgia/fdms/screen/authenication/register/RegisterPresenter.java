package com.framgia.fdms.screen.authenication.register;

import android.text.TextUtils;
import com.framgia.fdms.data.model.User;
import com.framgia.fdms.data.source.UserRepository;
import com.framgia.fdms.data.source.api.request.RegisterRequest;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Listens to user actions from the UI ({@link RegisterActivity}), retrieves the data and updates
 * the UI as required.
 */
final class RegisterPresenter implements RegisterContract.Presenter {

    private final RegisterContract.ViewModel mViewModel;
    private CompositeSubscription mCompositeSubscription;
    private UserRepository mUserRepository;
    private RegisterRequest mRequest;

    public RegisterPresenter(RegisterContract.ViewModel viewModel, UserRepository repository) {
        mViewModel = viewModel;
        mUserRepository = repository;
        mRequest = new RegisterRequest();
        mCompositeSubscription = new CompositeSubscription();
    }

    @Override
    public void onStart() {
        //TODO dev later
    }

    @Override
    public void onStop() {
        mCompositeSubscription.clear();
    }

    @Override
    public void register(RegisterRequest request) {
        if (!validateDataInput(request)) {
            return;
        }
        Subscription subscription = mUserRepository.register(mRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<User>() {
                    @Override
                    public void call(User request) {
                        mViewModel.onRegisterSuccess();
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mViewModel.onRegisterError();
                    }
                });
        mCompositeSubscription.add(subscription);
    }

    @Override
    public boolean validateDataInput(RegisterRequest request) {
        boolean isValid = true;
        if (TextUtils.isEmpty(request.getUserName())) {
            isValid = false;
            mViewModel.onInputUserNameError();
        }
        if (TextUtils.isEmpty(request.getPassWord())) {
            isValid = false;
            mViewModel.onInputPasswordError();
        }
        if (TextUtils.isEmpty(request.getConfirmPassword())) {
            isValid = false;
            mViewModel.onInputConfirmPasswordError();
        }
        if (TextUtils.isEmpty(request.getFirstName())) {
            isValid = false;
            mViewModel.onInputFirstnameError();
        }
        if (TextUtils.isEmpty(request.getLastName())) {
            isValid = false;
            mViewModel.onInputLastnameError();
        }
        if (TextUtils.isEmpty(request.getAddress())) {
            isValid = false;
            mViewModel.onInputAddressError();
        }
        if (TextUtils.isEmpty(request.getRole())) {
            isValid = false;
            mViewModel.onInputRoleError();
        }
        if (TextUtils.isEmpty(request.getDepartment())) {
            isValid = false;
            mViewModel.onInputDepartmentError();
        }
        return isValid;
    }
}
