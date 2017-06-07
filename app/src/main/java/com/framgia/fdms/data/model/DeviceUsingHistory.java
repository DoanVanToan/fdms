package com.framgia.fdms.data.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import com.framgia.fdms.BR;

/**
 * Created by framgia on 23/05/2017.
 */

public class DeviceUsingHistory extends BaseObservable {
    // TODO: 23/05/2017 fix when api be updated
    private String mStaffName;
    private String mStartDate;
    private String mEndDate;

    public DeviceUsingHistory(String staffName, String startDate, String endDate) {
        mStaffName = staffName;
        mStartDate = startDate;
        mEndDate = endDate;
    }

    @Bindable
    public String getStaffName() {
        return mStaffName;
    }

    public void setStaffName(String staffName) {
        mStaffName = staffName;
        notifyPropertyChanged(BR.staffName);
    }

    @Bindable
    public String getStartDate() {
        return mStartDate;
    }

    public void setStartDate(String startDate) {
        mStartDate = startDate;
        notifyPropertyChanged(BR.startDate);
    }

    @Bindable
    public String getEndDate() {
        return mEndDate;
    }

    public void setEndDate(String endDate) {
        mEndDate = endDate;
        notifyPropertyChanged(BR.endDate);
    }
}
