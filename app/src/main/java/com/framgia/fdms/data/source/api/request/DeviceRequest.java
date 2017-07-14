package com.framgia.fdms.data.source.api.request;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.graphics.Color;
import android.widget.NumberPicker;
import com.framgia.fdms.BR;
import com.framgia.fdms.data.model.Category;
import com.framgia.fdms.screen.requestcreation.RequestCreationViewModel;
import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by MyPC on 19/05/2017.
 */

public class DeviceRequest extends BaseObservable {
    @Expose
    @SerializedName("description")
    private String mDescription;
    @Expose
    @SerializedName("device_category_id")
    private int mDeviceCategoryId;
    @Expose
    @SerializedName("number")
    private int mNumber;
    private Category mCategory;

    public DeviceRequest() {
    }

    public DeviceRequest(int number) {
        mNumber = number;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public int getDeviceCategoryId() {
        return mDeviceCategoryId;
    }

    public void setDeviceCategoryId(int deviceCategoryId) {
        mDeviceCategoryId = deviceCategoryId;
    }

    @Bindable
    public int getNumber() {
        return mNumber;
    }

    public void setNumber(int number) {
        mNumber = number;
        notifyPropertyChanged(BR.number);
    }

    @Bindable
    public Category getCategory() {
        return mCategory;
    }

    public void setCategory(Category category) {
        mCategory = category;
        notifyPropertyChanged(BR.category);
    }

    public void onDecrement() {
        if (getNumber() > 1) {
            setNumber(getNumber() - 1);
        }
    }

    public void onIncrement() {
        setNumber(getNumber() + 1);
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
