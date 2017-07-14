package com.framgia.fdms.data.source.api.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * Created by MyPC on 18/05/2017.
 */

public class RequestCreatorRequest extends BaseRequest {
    @SerializedName("request[title]")
    private String mTitle;
    @SerializedName("request[description]")
    private String mDescription;
    @SerializedName("request[for_user_id]")
    private int mForUserId;
    @SerializedName("request[assignee_id]")
    private int mAssigneeId;
    @SerializedName("request[request_details_attributes]")
    private List<DeviceRequest> mDeviceRequests;

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public int getForUserId() {
        return mForUserId;
    }

    public void setForUserId(int forUserId) {
        mForUserId = forUserId;
    }

    public int getAssigneeId() {
        return mAssigneeId;
    }

    public void setAssigneeId(int assigneeId) {
        mAssigneeId = assigneeId;
    }

    public List<DeviceRequest> getDeviceRequests() {
        return mDeviceRequests;
    }

    public void setDeviceRequests(List<DeviceRequest> deviceRequests) {
        mDeviceRequests = deviceRequests;
    }
}
