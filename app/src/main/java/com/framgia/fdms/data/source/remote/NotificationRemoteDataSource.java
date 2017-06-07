package com.framgia.fdms.data.source.remote;

import com.framgia.fdms.data.model.Notification;
import com.framgia.fdms.data.source.NotificationDataSource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import rx.Observable;

/**
 * Created by Nhahv0902 on 6/1/2017.
 * <></>
 */

public class NotificationRemoteDataSource implements NotificationDataSource {

    private static NotificationRemoteDataSource sInstances;

    private NotificationRemoteDataSource() {
    }

    public static NotificationRemoteDataSource getInstances() {
        if (sInstances == null) {
            sInstances = new NotificationRemoteDataSource();
        }
        return sInstances;
    }

    @Override
    public Observable<List<Notification>> getNotifications() {
        List<Notification> notifications = new ArrayList<>();
        notifications.add(new Notification("Để làm ứng dụng Android", "Hoang Van Nha", new Date()));
        notifications.add(new Notification("Để làm ứng dụng IOS", "Nguyen Van Tuan", new Date()));
        notifications.add(new Notification("Để làm ứng dụng WEB", "Nguyễn Hà Phan", new Date()));
        notifications.add(new Notification("Để làm ứng dụng Ruby", "Tran Hiếu", new Date()));
        notifications.add(
                new Notification("Để làm ứng dụng Test Android", "Tran Dinh Sang", new Date()));
        notifications.add(new Notification("Để làm ứng dụng Android", "Hoang Van Nha", new Date()));
        notifications.add(new Notification("Để làm ứng dụng Android", "Hoang Van Nha", new Date()));
        return Observable.just(notifications);
    }
}
