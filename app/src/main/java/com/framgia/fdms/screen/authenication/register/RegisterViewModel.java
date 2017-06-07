package com.framgia.fdms.screen.authenication.register;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.widget.Toast;
import com.framgia.fdms.BR;
import com.framgia.fdms.R;
import com.framgia.fdms.data.source.api.request.RegisterRequest;
import com.framgia.fdms.screen.main.MainActivity;

/**
 * Exposes the data to be used in the Register screen.
 */

public class RegisterViewModel extends BaseObservable implements RegisterContract.ViewModel {

    private Context mContext;
    private RegisterContract.Presenter mPresenter;
    private String mUsernameError;
    private String mPasswordError;
    private String mConfirmPasswordError;
    private String mFirstnameError;
    private String mLastnameError;
    private String mAddressError;
    private String mRoleError;
    private String mDepartmentError;
    private RegisterRequest mRequest;

    public RegisterViewModel(Context context) {
        mContext = context;
        mRequest = new RegisterRequest();
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
    public void setPresenter(RegisterContract.Presenter presenter) {
        mPresenter = presenter;
    }

    public void onCreateAccountClick() {
        if (!mPresenter.validateDataInput(mRequest)) {
            return;
        }
        mPresenter.register(mRequest);
    }

    @Override
    public void onRegisterError() {
        Toast.makeText(mContext, R.string.msg_create_account_error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRegisterSuccess() {
        mContext.startActivity(MainActivity.getInstance(mContext));
    }

    @Override
    public void onInputUserNameError() {
        mUsernameError = mContext.getString(R.string.msg_error_user_name);
        notifyPropertyChanged(BR.usernameError);
    }

    @Override
    public void onInputPasswordError() {
        mPasswordError = mContext.getString(R.string.msg_error_user_name);
        notifyPropertyChanged(com.framgia.fdms.BR.passwordError);
    }

    @Override
    public void onInputConfirmPasswordError() {
        mConfirmPasswordError = mContext.getString(R.string.msg_error_user_name);
        notifyPropertyChanged(com.framgia.fdms.BR.confirmPasswordError);
    }

    @Override
    public void onInputFirstnameError() {
        mFirstnameError = mContext.getString(R.string.msg_error_user_name);
        notifyPropertyChanged(com.framgia.fdms.BR.firstnameError);
    }

    @Override
    public void onInputLastnameError() {
        mLastnameError = mContext.getString(R.string.msg_error_user_name);
        notifyPropertyChanged(com.framgia.fdms.BR.lastnameError);
    }

    @Override
    public void onInputAddressError() {
        mAddressError = mContext.getString(R.string.msg_error_user_name);
        notifyPropertyChanged(com.framgia.fdms.BR.addressError);
    }

    @Override
    public void onInputRoleError() {
        mRoleError = mContext.getString(R.string.msg_error_user_name);
        notifyPropertyChanged(com.framgia.fdms.BR.roleError);
    }

    @Override
    public void onInputDepartmentError() {
        mDepartmentError = mContext.getString(R.string.msg_error_user_name);
        notifyPropertyChanged(com.framgia.fdms.BR.departmentError);
    }

    @Bindable
    public String getUserName() {
        return mRequest.getUserName();
    }

    @Bindable
    public String getPassword() {
        return mRequest.getPassWord();
    }

    public void setPassword(String password) {
        mRequest.setPassWord(password);
    }

    @Bindable
    public String getConfirmPassowrd() {
        return mRequest.getConfirmPassword();
    }

    public void setConfirmPassowrd(String confirmPassowrd) {
        mRequest.setConfirmPassword(confirmPassowrd);
    }

    @Bindable
    public String getFirstname() {
        return mRequest.getFirstName();
    }

    public void setFirstname(String firstname) {
        mRequest.setFirstName(firstname);
    }

    @Bindable
    public String getLastname() {

        return mRequest.getLastName();
    }

    public void setLastname(String lastname) {
        mRequest.setLastName(lastname);
    }

    @Bindable
    public String getAddress() {
        return mRequest.getAddress();
    }

    public void setAddress(String address) {
        mRequest.setAddress(address);
    }

    @Bindable
    public String getRole() {
        return mRequest.getRole();
    }

    public void setRole(String role) {
        mRequest.setRole(role);
    }

    @Bindable
    public String getDepartment() {
        return mRequest.getDepartment();
    }

    public void setDepartment(String department) {
        mRequest.setDepartment(department);
    }

    public String getUsername() {
        return mRequest.getUserName();
    }

    public void setUsername(String username) {
        mRequest.setUserName(username);
    }

    @Bindable
    public String getUsernameError() {
        return mUsernameError;
    }

    @Bindable
    public String getPasswordError() {
        return mPasswordError;
    }

    @Bindable
    public String getConfirmPasswordError() {
        return mConfirmPasswordError;
    }

    @Bindable
    public String getFirstnameError() {
        return mFirstnameError;
    }

    @Bindable
    public String getLastnameError() {
        return mLastnameError;
    }

    @Bindable
    public String getAddressError() {
        return mAddressError;
    }

    @Bindable
    public String getRoleError() {
        return mRoleError;
    }

    @Bindable
    public String getDepartmentError() {
        return mDepartmentError;
    }
}
