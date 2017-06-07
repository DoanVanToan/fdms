package com.framgia.fdms.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by MyPC on 10/05/2017.
 */

public class Dashboard {
    @Expose
    @SerializedName("title")
    private String mTitle;
    @Expose
    @SerializedName("count")
    private int mCount;
    @Expose
    @SerializedName("color")
    private String mColor;
    @Expose
    @SerializedName("background_color")
    private String mBackgroundColor;
    @Expose
    @SerializedName("hover_background_color")
    private String mHoverBackgroundColor;

    public Dashboard(String title, int count, String color, String backgroundColor,
            String hoverBackgroundColor) {
        mTitle = title;
        mCount = count;
        mColor = color;
        mBackgroundColor = backgroundColor;
        mHoverBackgroundColor = hoverBackgroundColor;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public int getCount() {
        return mCount;
    }

    public void setCount(int count) {
        mCount = count;
    }

    public String getColor() {
        return mColor;
    }

    public void setColor(String color) {
        mColor = color;
    }

    public String getBackgroundColor() {
        return mBackgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        mBackgroundColor = backgroundColor;
    }

    public String getHoverBackgroundColor() {
        return mHoverBackgroundColor;
    }

    public void setHoverBackgroundColor(String hoverBackgroundColor) {
        mHoverBackgroundColor = hoverBackgroundColor;
    }
}
