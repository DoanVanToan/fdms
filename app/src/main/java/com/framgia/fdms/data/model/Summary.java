package com.framgia.fdms.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by MyPC on 29/05/2017.
 */

public class Summary {
    @Expose
    @SerializedName("assignment")
    private int mAssignment;
    @Expose
    @SerializedName("available")
    private int mAvailable;
    @Expose
    @SerializedName("total")
    private int mTotal;

    public int getAssignment() {
        return mAssignment;
    }

    public void setAssignment(int assignment) {
        mAssignment = assignment;
    }

    public int getAvailable() {
        return mAvailable;
    }

    public void setAvailable(int available) {
        mAvailable = available;
    }

    public int getTotal() {
        return mTotal;
    }

    public void setTotal(int total) {
        mTotal = total;
    }
}
