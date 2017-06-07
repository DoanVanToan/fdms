package com.framgia.fdms.data.source.api.request;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.graphics.Color;
import android.widget.NumberPicker;
import com.framgia.fdms.BR;
import com.framgia.fdms.data.model.Category;
import com.framgia.fdms.screen.requestcreation.RequestCreationViewModel;

/**
 * Created by MyPC on 19/05/2017.
 */

public class DeviceRequest extends BaseObservable {
    private String mDescription;
    private int mDeviceCategoryId;
    private int mNumber;
    private Category mCategory;

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
}
