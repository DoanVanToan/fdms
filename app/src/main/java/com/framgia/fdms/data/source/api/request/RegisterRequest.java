package com.framgia.fdms.data.source.api.request;

import rx.subscriptions.CompositeSubscription;

/**
 * Created by levutantuan on 4/5/17.
 */

public class RegisterRequest extends BaseRequest {
    public String mUserName;
    public String mPassWord;
    public String mConfirmPassword;
    public String mFirstName;
    public String mLastName;
    public String mAddress;
    public String mRole;
    public String mDepartment;
    private CompositeSubscription mCompositeSubscription = new CompositeSubscription();

    public RegisterRequest(String userName, String passWord, String confirmPassword,
            String firstName, String lastName, String address, String role, String department) {
        mUserName = userName;
        mPassWord = passWord;
        mConfirmPassword = confirmPassword;
        mFirstName = firstName;
        mLastName = lastName;
        mAddress = address;
        mRole = role;
        mDepartment = department;
    }

    public RegisterRequest() {
    }

    public String getUserName() {
        return mUserName;
    }

    public void setUserName(String userName) {
        mUserName = userName;
    }

    public String getPassWord() {
        return mPassWord;
    }

    public void setPassWord(String passWord) {
        mPassWord = passWord;
    }

    public String getConfirmPassword() {
        return mConfirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        mConfirmPassword = confirmPassword;
    }

    public String getFirstName() {
        return mFirstName;
    }

    public void setFirstName(String firstName) {
        mFirstName = firstName;
    }

    public String getLastName() {
        return mLastName;
    }

    public void setLastName(String lastName) {
        mLastName = lastName;
    }

    public String getAddress() {
        return mAddress;
    }

    public void setAddress(String address) {
        mAddress = address;
    }

    public String getRole() {
        return mRole;
    }

    public void setRole(String role) {
        mRole = role;
    }

    public String getDepartment() {
        return mDepartment;
    }

    public void setDepartment(String department) {
        mDepartment = department;
    }
}
