package com.framgia.fdms.screen.authenication.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableField;
import android.support.v7.widget.AppCompatCheckBox;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;
import com.android.databinding.library.baseAdapters.BR;
import com.framgia.fdms.R;
import com.framgia.fdms.data.model.User;
import com.framgia.fdms.screen.authenication.forgotpassword.ForgotpasswordActivity;
import com.framgia.fdms.screen.main.MainActivity;
import com.framgia.fdms.screen.authenication.register.RegisterActivity;

/**
 * Exposes the data to be used in the Login screen.
 */
public class LoginViewModel extends BaseObservable implements LoginContract.ViewModel {

    private Context mContext;
    private LoginContract.Presenter mPresenter;
    private String mUsername;
    private String mPassword;
    private String mUsernameError;
    private String mPasswordError;
    private boolean isValid;
    public final ObservableField<Integer> progressBarVisibility = new ObservableField<>();
    private boolean mIsRememberAccount;

    public LoginViewModel(Context context) {
        mContext = context;
        progressBarVisibility.set(View.GONE);
    }

    @Override
    public void onStart() {
        mPresenter.onStart();
    }

    @Override
    public void onStop() {
        mPresenter.onStop();
    }

    @Override
    public void setPresenter(LoginContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onLoginError(String msg) {
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
        hideProgressbar();
    }

    @Override
    public void onLoginSuccess() {
        mContext.startActivity(MainActivity.getInstance(mContext));
        ((Activity) (mContext)).finish();
        isValid = false;
    }

    @Override
    public void onInputUserNameError() {
        mUsernameError = mContext.getString(R.string.msg_error_user_name);
        notifyPropertyChanged(BR.usernameError);
    }

    @Override
    public void onInputPasswordError() {
        mPasswordError = mContext.getString(R.string.msg_error_user_name);
        notifyPropertyChanged(BR.passwordError);
    }

    public void onLoginClick() {
        if (!mPresenter.validateDataInput(mUsername, mPassword)) {
            return;
        }
        mPresenter.login(mUsername, mPassword);
    }

    public void onForgotPasswordClick() {
        mContext.startActivity(ForgotpasswordActivity.getForgetPasswordIntent(mContext));
    }

    public void onSignUpClick() {
        mContext.startActivity(new Intent(mContext, RegisterActivity.class));
    }

    @Bindable
    public String getUsername() {
        return mUsername;
    }

    public void setUsername(String username) {
        mUsername = username;
        notifyPropertyChanged(BR.username);
    }

    @Bindable
    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        mPassword = password;
        notifyPropertyChanged(BR.password);
    }

    @Bindable
    public String getUsernameError() {
        return mUsernameError;
    }

    public void setUsernameError(String usernameError) {
        mUsernameError = usernameError;
    }

    @Bindable
    public String getPasswordError() {
        return mPasswordError;
    }

    public void setPasswordError(String passwordError) {
        mPasswordError = passwordError;
    }

    @Override
    public void showProgressbar() {
        progressBarVisibility.set(View.VISIBLE);
    }

    @Override
    public void hideProgressbar() {
        progressBarVisibility.set(View.GONE);
    }

    @Override
    public void onCachedAccountLoaded(String user, String password) {
        if (user == null || password == null) return;
        setUsername(user);
        setPassword(password);
        setRememberAccount(true);
    }

    @Bindable
    public boolean isRememberAccount() {
        return mIsRememberAccount;
    }

    public void setRememberAccount(boolean rememberAccount) {
        mIsRememberAccount = rememberAccount;
        notifyPropertyChanged(BR.rememberAccount);
    }
}
