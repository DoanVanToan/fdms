package com.framgia.fdms.data.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import com.framgia.fdms.BR;
import com.framgia.fdms.R;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static com.framgia.fdms.utils.Constant.AVAIABLE;
import static com.framgia.fdms.utils.Constant.BROKEN;
import static com.framgia.fdms.utils.Constant.USING;

/**
 * Created by Hoang Van Nha on 5/23/2017.
 * <></>
 */

public class DeviceHistoryDetail extends BaseObservable {
    private final String STATUS_USING = "using";
    private final String STATUS_AVAILABLE = "available";
    private final String STATUS_BROKEN = "broken";

    @Expose
    @SerializedName("date_time")
    private Date mDateTime;
    @Expose
    @SerializedName("description")
    private String mDescription;
    @Expose
    @SerializedName("device_status")
    private String mDeviceStatus;

    @Bindable
    public Date getDateTime() {
        return mDateTime;
    }

    public void setDateTime(Date dateTime) {
        mDateTime = dateTime;
        notifyPropertyChanged(BR.dateTime);
    }

    @Bindable
    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
        notifyPropertyChanged(BR.description);
    }

    @Bindable
    public String getDeviceStatus() {
        return mDeviceStatus;
    }

    public void setDeviceStatus(String deviceStatus) {
        mDeviceStatus = deviceStatus;
        notifyPropertyChanged(BR.deviceStatus);
    }

    public int getStatusImage() {
        switch (mDeviceStatus) {
            case STATUS_USING:
                return R.drawable.ic_using;
            case STATUS_AVAILABLE:
                return R.drawable.ic_avaiable;
            case STATUS_BROKEN:
                return R.drawable.ic_broken;
            default:
                return R.drawable.ic_avaiable;
        }
    }

    private Date convertToDate(String dateString) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        try {
            return dateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
