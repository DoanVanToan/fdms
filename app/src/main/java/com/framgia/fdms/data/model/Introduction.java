package com.framgia.fdms.data.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;

import com.framgia.fdms.BR;

/**
 * Created by MyPC on 04/07/2017.
 */
public class Introduction extends BaseObservable implements Parcelable {
    public static final Parcelable.Creator<Introduction> CREATOR =
        new Parcelable.Creator<Introduction>() {
            @Override
            public Introduction createFromParcel(Parcel in) {
                return new Introduction(in);
            }

            @Override
            public Introduction[] newArray(int size) {
                return new Introduction[size];
            }
        };
    private String mTitle;
    private String mHeader;
    private String mContent;
    private Drawable mImage;
    private int mColor;

    protected Introduction(Parcel in) {
        mTitle = in.readString();
        mHeader = in.readString();
        mContent = in.readString();
        mColor = in.readInt();
    }

    public Introduction(String title, String header, String content, Drawable image, int color) {
        mTitle = title;
        mHeader = header;
        mContent = content;
        mImage = image;
        mColor = color;
    }

    @Bindable
    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
        notifyPropertyChanged(BR.title);
    }

    @Bindable
    public String getHeader() {
        return mHeader;
    }

    public void setHeader(String header) {
        mHeader = header;
        notifyPropertyChanged(BR.header);
    }

    @Bindable
    public String getContent() {
        return mContent;
    }

    public void setContent(String content) {
        mContent = content;
        notifyPropertyChanged(BR.content);
    }

    @Bindable
    public Drawable getImage() {
        return mImage;
    }

    public void setImage(Drawable image) {
        mImage = image;
        notifyPropertyChanged(BR.image);
    }

    @Bindable
    public int getColor() {
        return mColor;
    }

    public void setColor(int color) {
        mColor = color;
        notifyPropertyChanged(BR.color);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mTitle);
        parcel.writeString(mHeader);
        parcel.writeString(mContent);
        parcel.writeInt(mColor);
    }

    public static class Builder {
        private String mNestedTitle;
        private String mNestedHeader;
        private String mNestedContent;
        private Drawable mNestedImage;
        private int mNestedColor;

        public Builder setTitle(String title) {
            mNestedTitle = title;
            return this;
        }

        public Builder setHeader(String header) {
            mNestedHeader = header;
            return this;
        }

        public Builder setContent(String content) {
            mNestedContent = content;
            return this;
        }

        public Builder setImage(Drawable image) {
            mNestedImage = image;
            return this;
        }

        public Builder setColor(int color) {
            mNestedColor = color;
            return this;
        }

        public Introduction create() {
            return new Introduction(mNestedTitle, mNestedHeader, mNestedContent, mNestedImage,
                mNestedColor);
        }
    }
}
