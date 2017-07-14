package com.framgia.fdms.data.source;

import com.framgia.fdms.data.model.Notification;
import java.util.List;
import rx.Observable;

/**
 * Created by Nhahv0902 on 6/1/2017.
 * <></>
 */

public interface NotificationDataSource {
    Observable<List<Notification>> getNotifications();
}
