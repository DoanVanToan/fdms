package com.framgia.fdms.data.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.os.Parcel;
import android.os.Parcelable;
import com.framgia.fdms.BR;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import static com.framgia.fdms.utils.Constant.END_POINT_URL;
import static com.framgia.fdms.utils.binding.BindingUtils.URL_ASSETS;

/**
 * Created by MyPC on 26/04/2017.
 */

public class Picture extends BaseObservable implements Parcelable {

    @Expose
    @SerializedName("url")
    private String mUrl;

    public Picture(String url) {
        mUrl = url;
    }

    protected Picture(Parcel in) {
        mUrl = in.readString();
    }

    public static final Creator<Picture> CREATOR = new Creator<Picture>() {
        @Override
        public Picture createFromParcel(Parcel in) {
            return new Picture(in);
        }

        @Override
        public Picture[] newArray(int size) {
            return new Picture[size];
        }
    };

    @Bindable
    public String getUrl() {
        return mUrl.contains(URL_ASSETS) ? (END_POINT_URL + mUrl) : mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
        notifyPropertyChanged(BR.url);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mUrl);
    }
}
