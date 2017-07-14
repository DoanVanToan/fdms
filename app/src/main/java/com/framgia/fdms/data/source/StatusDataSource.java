package com.framgia.fdms.data.source;

import com.framgia.fdms.data.model.Status;
import java.util.List;
import rx.Observable;

/**
 * Created by MyPC on 05/05/2017.
 */

public class StatusDataSource {
    public interface LocalDataSource {
    }

    public interface RemoteDataSource {
        Observable<List<Status>> getListStatus();

        Observable<List<Status>> getListStatusRequest();

        Observable<List<Status>> getListRelative();

        Observable<List<Status>> getListAssignee();
    }
}
