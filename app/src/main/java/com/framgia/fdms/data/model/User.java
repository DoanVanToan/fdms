package com.framgia.fdms.data.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.support.annotation.StringDef;
import com.android.databinding.library.baseAdapters.BR;
import com.framgia.fdms.utils.Constant;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.Date;

import static com.framgia.fdms.data.model.User.Role.STAFF;
import static com.framgia.fdms.utils.Constant.Role.BO_MANAGER;
import static com.framgia.fdms.utils.Constant.Role.BO_STAFF;

/**
 * Created by levutantuan on 3/31/17.
 */
public class User extends BaseObservable {
    @Expose
    @SerializedName("id")
    private int mId;
    @Expose
    @SerializedName("first_name")
    private String mFirstName;
    @Expose
    @SerializedName("last_name")
    private String mLastName;
    @Expose
    @SerializedName("email")
    private String mEmail;
    @Expose
    @SerializedName("address")
    private String mAddress;
    @Expose
    @SerializedName("password_digest")
    private String mPasswordDigest;
    @Expose
    @SerializedName("reset_digest")
    private String mResetDigest;
    @Expose
    @SerializedName("created_by")
    private String mCreatedBy;
    @Expose
    @SerializedName("updated_by")
    private String mUpdatedBy;
    @Expose
    @SerializedName("created_at")
    private Date mCreatedAt;
    @Expose
    @SerializedName("updated_at")
    private Date mUpdatedAt;
    @Expose
    @SerializedName("remember_digest")
    private String mRememberDigest;
    @Expose
    @SerializedName("avatar")
    private Picture mAvatar;
    @Expose
    @SerializedName("from_excel")
    private boolean mFromExcel;
    @Expose
    @SerializedName("gender")
    private String mGender;
    @Expose
    @SerializedName("role")
    private String mRole;
    @Expose
    @SerializedName("birthday")
    private Date mBirthday;
    @Expose
    @SerializedName("employee_code")
    private String mEmployeeCode;
    @Expose
    @SerializedName("status")
    private String mStatus;
    @Expose
    @SerializedName("contract_date")
    private Date mContractDate;
    @Expose
    @SerializedName("start_probation_date")
    private Date mStartProbationDate;
    @Expose
    @SerializedName("end_probation_date")
    private Date mEndProbationDate;
    @Expose
    @SerializedName("token")
    private String mToken;

    public User() {
    }

    @Bindable
    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
        notifyPropertyChanged(BR.id);
    }

    @Bindable
    public String getFirstName() {
        return mFirstName;
    }

    public void setFirstName(String firstName) {
        mFirstName = firstName;
        notifyPropertyChanged(BR.firstName);
    }

    @Bindable
    public String getLastName() {
        return mLastName;
    }

    public void setLastName(String lastName) {
        mLastName = lastName;
        notifyPropertyChanged(BR.lastName);
    }

    @Bindable
    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
        notifyPropertyChanged(BR.email);
    }

    @Bindable
    public String getAddress() {
        return mAddress;
    }

    public void setAddress(String address) {
        mAddress = address;
        notifyPropertyChanged(BR.address);
    }

    @Bindable
    public String getPasswordDigest() {
        return mPasswordDigest;
    }

    public void setPasswordDigest(String passwordDigest) {
        mPasswordDigest = passwordDigest;
        notifyPropertyChanged(BR.passwordDigest);
    }

    @Bindable
    public String getResetDigest() {
        return mResetDigest;
    }

    public void setResetDigest(String resetDigest) {
        mResetDigest = resetDigest;
        notifyPropertyChanged(BR.resetDigest);
    }

    @Bindable
    public String getCreatedBy() {
        return mCreatedBy;
    }

    public void setCreatedBy(String createdBy) {
        mCreatedBy = createdBy;
        notifyPropertyChanged(BR.createdBy);
    }

    @Bindable
    public String getUpdatedBy() {
        return mUpdatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        mUpdatedBy = updatedBy;
        notifyPropertyChanged(BR.updatedBy);
    }

    @Bindable
    public Date getCreatedAt() {
        return mCreatedAt;
    }

    public void setCreatedAt(Date createdAt) {
        mCreatedAt = createdAt;
        notifyPropertyChanged(BR.createdAt);
    }

    @Bindable
    public Date getUpdatedAt() {
        return mUpdatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        mUpdatedAt = updatedAt;
        notifyPropertyChanged(BR.updatedAt);
    }

    @Bindable
    public String getRememberDigest() {
        return mRememberDigest;
    }

    public void setRememberDigest(String rememberDigest) {
        mRememberDigest = rememberDigest;
        notifyPropertyChanged(BR.rememberDigest);
    }

    @Bindable
    public Picture getAvatar() {
        return mAvatar;
    }

    public void setAvatar(Picture avatar) {
        mAvatar = avatar;
        notifyPropertyChanged(BR.avatar);
    }

    @Bindable
    public boolean isFromExcel() {
        return mFromExcel;
    }

    public void setFromExcel(boolean fromExcel) {
        mFromExcel = fromExcel;
        notifyPropertyChanged(BR.fromExcel);
    }

    @Bindable
    public String getGender() {
        return mGender;
    }

    public void setGender(String gender) {
        mGender = gender;
        notifyPropertyChanged(BR.gender);
    }

    @Bindable
    public String getRole() {
        return mRole;
    }

    public void setRole(String role) {
        mRole = role;
        notifyPropertyChanged(BR.role);
    }

    @Bindable
    public Date getBirthday() {
        return mBirthday;
    }

    public void setBirthday(Date birthday) {
        mBirthday = birthday;
        notifyPropertyChanged(BR.birthday);
    }

    @Bindable
    public String getEmployeeCode() {
        return mEmployeeCode;
    }

    public void setEmployeeCode(String employeeCode) {
        mEmployeeCode = employeeCode;
        notifyPropertyChanged(BR.employeeCode);
    }

    @Bindable
    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        mStatus = status;
        notifyPropertyChanged(BR.status);
    }

    @Bindable
    public Date getContractDate() {
        return mContractDate;
    }

    public void setContractDate(Date contractDate) {
        mContractDate = contractDate;
        notifyPropertyChanged(BR.contractDate);
    }

    @Bindable
    public Date getStartProbationDate() {
        return mStartProbationDate;
    }

    public void setStartProbationDate(Date startProbationDate) {
        mStartProbationDate = startProbationDate;
        notifyPropertyChanged(BR.startProbationDate);
    }

    @Bindable
    public Date getEndProbationDate() {
        return mEndProbationDate;
    }

    public void setEndProbationDate(Date endProbationDate) {
        mEndProbationDate = endProbationDate;
        notifyPropertyChanged(BR.endProbationDate);
    }

    @Bindable
    public String getToken() {
        return mToken;
    }

    public void setToken(String token) {
        mToken = token;
        notifyPropertyChanged(BR.token);
    }

    @StringDef({ STAFF })
    public @interface Role {
        String STAFF = "staff";
    }

    public boolean isBo() {
        return mRole.equals(BO_MANAGER) || mRole.equals(BO_STAFF) || mRole.equals(
                Constant.Role.STAFF);
    }
}
