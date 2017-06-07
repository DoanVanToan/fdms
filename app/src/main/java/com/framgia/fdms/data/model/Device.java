package com.framgia.fdms.data.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.os.Parcel;
import android.os.Parcelable;
import com.framgia.fdms.BR;
import com.framgia.fdms.R;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.Date;

import static com.framgia.fdms.utils.Constant.AVAIABLE;
import static com.framgia.fdms.utils.Constant.BROKEN;
import static com.framgia.fdms.utils.Constant.USING;

/**
 * Created by Age on 4/1/2017.
 */
public class Device extends BaseObservable implements Parcelable {
    public static final Creator<Device> CREATOR = new Creator<Device>() {
        @Override
        public Device createFromParcel(Parcel in) {
            return new Device(in);
        }

        @Override
        public Device[] newArray(int size) {
            return new Device[size];
        }
    };
    @Expose
    @SerializedName("id")
    private int mId;
    @Expose
    @SerializedName("device_code")
    private String mDeviceCode;
    @Expose
    @SerializedName("production_name")
    private String mProductionName;
    @Expose
    @SerializedName("device_status_id")
    private int mDeviceStatusId;
    @Expose
    @SerializedName("device_category_id")
    private int mDeviceCategoryId;
    @Expose
    @SerializedName("picture")
    private Picture mPicture;
    @Expose
    @SerializedName("original_price")
    private String mOriginalPrice;
    @Expose
    @SerializedName("bought_date")
    private Date mBoughtDate;
    @Expose
    @SerializedName("printed_code")
    private String mPrintedCode;
    @Expose
    @SerializedName("is_barcode")
    private boolean mIsBarcode;
    @Expose
    @SerializedName("device_status_name")
    private String mDeviceStatusName;
    @Expose
    @SerializedName("device_category_name")
    private String mDeviceCategoryName;
    private String mSerialNumber;
    private String mModelNumber;
    @Expose
    @SerializedName("category_name")
    private String mCategoryName;
    @Expose
    @SerializedName("status")
    private int mStatus;
    @Expose
    @SerializedName("summary")
    private Summary mSummary;
    private boolean mIsSelected;
    @SerializedName("user")
    private UserBorrow mUser;

    public Device() {
    }

    public Device(String deviceCode, String productionName, String deviceCategoryName) {
        mDeviceCode = deviceCode;
        mProductionName = productionName;
        mDeviceCategoryName = deviceCategoryName;
    }

    protected Device(Parcel in) {
        mId = in.readInt();
        mDeviceCode = in.readString();
        mProductionName = in.readString();
        mDeviceStatusId = in.readInt();
        mDeviceCategoryId = in.readInt();
        mPicture = in.readParcelable(Picture.class.getClassLoader());
        mOriginalPrice = in.readString();
        mPrintedCode = in.readString();
        mIsBarcode = in.readByte() != 0;
        mDeviceStatusName = in.readString();
        mDeviceCategoryName = in.readString();
        mSerialNumber = in.readString();
        mModelNumber = in.readString();
        mCategoryName = in.readString();
        mStatus = in.readInt();
        mIsSelected = in.readByte() != 0;
        mUser = in.readParcelable(UserBorrow.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mId);
        dest.writeString(mDeviceCode);
        dest.writeString(mProductionName);
        dest.writeInt(mDeviceStatusId);
        dest.writeInt(mDeviceCategoryId);
        dest.writeParcelable(mPicture, flags);
        dest.writeString(mOriginalPrice);
        dest.writeString(mPrintedCode);
        dest.writeByte((byte) (mIsBarcode ? 1 : 0));
        dest.writeString(mDeviceStatusName);
        dest.writeString(mDeviceCategoryName);
        dest.writeString(mSerialNumber);
        dest.writeString(mModelNumber);
        dest.writeString(mCategoryName);
        dest.writeInt(mStatus);
        dest.writeByte((byte) (mIsSelected ? 1 : 0));
        dest.writeParcelable(mUser, flags);
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
    public String getDeviceCode() {
        return mDeviceCode;
    }

    public void setDeviceCode(String deviceCode) {
        mDeviceCode = deviceCode;
        notifyPropertyChanged(BR.deviceCode);
    }

    @Bindable
    public String getProductionName() {
        return mProductionName;
    }

    public void setProductionName(String productionName) {
        mProductionName = productionName;
        notifyPropertyChanged(BR.productionName);
    }

    @Bindable
    public int getDeviceStatusId() {
        return mDeviceStatusId;
    }

    public void setDeviceStatusId(int deviceStatusId) {
        mDeviceStatusId = deviceStatusId;
        notifyPropertyChanged(BR.deviceStatusId);
    }

    @Bindable
    public int getDeviceCategoryId() {
        return mDeviceCategoryId;
    }

    public void setDeviceCategoryId(int deviceCategoryId) {
        mDeviceCategoryId = deviceCategoryId;
        notifyPropertyChanged(BR.deviceCategoryId);
    }

    @Bindable
    public Picture getPicture() {
        return mPicture;
    }

    public void setPicture(Picture picture) {
        mPicture = picture;
        notifyPropertyChanged(BR.picture);
    }

    @Bindable
    public String getOriginalPrice() {
        return mOriginalPrice;
    }

    public void setOriginalPrice(String originalPrice) {
        mOriginalPrice = originalPrice;
        notifyPropertyChanged(BR.originalPrice);
    }

    @Bindable
    public Date getBoughtDate() {
        return mBoughtDate;
    }

    public void setBoughtDate(Date boughtDate) {
        mBoughtDate = boughtDate;
        notifyPropertyChanged(BR.boughtDate);
    }

    @Bindable
    public String getPrintedCode() {
        return mPrintedCode;
    }

    public void setPrintedCode(String printedCode) {
        mPrintedCode = printedCode;
        notifyPropertyChanged(BR.printedCode);
    }

    @Bindable
    public boolean isBarcode() {
        return mIsBarcode;
    }

    public void setBarcode(boolean barcode) {
        mIsBarcode = barcode;
        notifyPropertyChanged(BR.barcode);
    }

    @Bindable
    public String getDeviceStatusName() {
        return mDeviceStatusName;
    }

    public void setDeviceStatusName(String deviceStatusName) {
        mDeviceStatusName = deviceStatusName;
    }

    @Bindable
    public String getDeviceCategoryName() {
        return mDeviceCategoryName;
    }

    public void setDeviceCategoryName(String deviceCategoryName) {
        mDeviceCategoryName = deviceCategoryName;
        notifyPropertyChanged(BR.deviceCategoryName);
    }

    @Bindable
    public String getSerialNumber() {
        return mSerialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        mSerialNumber = serialNumber;
        notifyPropertyChanged(BR.serialNumber);
    }

    @Bindable
    public String getModelNumber() {
        return mModelNumber;
    }

    public void setModelNumber(String modelNumber) {
        mModelNumber = modelNumber;
        notifyPropertyChanged(BR.modelNumber);
    }

    public String getCategoryName() {
        return mCategoryName;
    }

    public void setCategoryName(String categoryName) {
        mCategoryName = categoryName;
    }

    public int getStatus() {
        return mStatus;
    }

    public void setStatus(int status) {
        mStatus = status;
    }

    public Summary getSummary() {
        return mSummary;
    }

    public void setSummary(Summary summary) {
        mSummary = summary;
    }

    @Bindable
    public boolean isSelected() {
        return mIsSelected;
    }

    public void setSelected(boolean selected) {
        mIsSelected = selected;
        notifyPropertyChanged(BR.selected);
    }

    @Bindable
    public UserBorrow getUser() {
        return mUser;
    }

    public void setUser(UserBorrow user) {
        mUser = user;
        notifyPropertyChanged(BR.user);
    }

    @Bindable
    public int getResourceId() {
        switch (mDeviceStatusId) {
            case USING:
                return R.drawable.ic_using;
            case AVAIABLE:
                return R.drawable.ic_avaiable;
            case BROKEN:
                return R.drawable.ic_broken;
            default:
                return R.drawable.ic_avaiable;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public class UserBorrow implements Parcelable {
        public final Creator<UserBorrow> CREATOR = new Creator<UserBorrow>() {
            @Override
            public UserBorrow createFromParcel(Parcel in) {
                return new UserBorrow(in);
            }

            @Override
            public UserBorrow[] newArray(int size) {
                return new UserBorrow[size];
            }
        };
        @SerializedName("id")
        private String mId;
        @SerializedName("name")
        private String mName;

        protected UserBorrow(Parcel in) {
            mId = in.readString();
            mName = in.readString();
        }

        public String getId() {
            return mId;
        }

        public void setId(String id) {
            mId = id;
        }

        public String getName() {
            return mName;
        }

        public void setName(String name) {
            mName = name;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(mId);
            dest.writeString(mName);
        }
    }
}
