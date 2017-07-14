package com.framgia.fdms.data.source;

import com.framgia.fdms.data.model.Notification;
import com.framgia.fdms.data.source.remote.NotificationRemoteDataSource;
import java.util.List;
import rx.Observable;

/**
 * Created by Nhahv0902 on 6/1/2017.
 * <></>
 */

public class NotificationRepository implements NotificationDataSource {

    private static NotificationRepository sInstances;
    private NotificationRemoteDataSource mDataSource;

    private NotificationRepository() {
        mDataSource = NotificationRemoteDataSource.getInstances();
    }

    public static NotificationRepository getInstances() {
        if (sInstances == null) {
            sInstances = new NotificationRepository();
        }
        return sInstances;
    }

    @Override
    public Observable<List<Notification>> getNotifications() {
        return mDataSource.getNotifications();
    }
}
