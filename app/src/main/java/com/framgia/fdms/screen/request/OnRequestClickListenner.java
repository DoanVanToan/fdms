package com.framgia.fdms.screen.request;

import android.view.View;
import com.framgia.fdms.data.model.Request;
import com.framgia.fdms.screen.request.userrequest.UserRequestAdapter;

/**
 * Created by framgia on 22/05/2017.
 */

public interface OnRequestClickListenner {
    void onMenuClick(View v, UserRequestAdapter.RequestModel request);

    void onDetailRequestClick(Request request);

    void onAddDeviceClick(int requestId);
}
