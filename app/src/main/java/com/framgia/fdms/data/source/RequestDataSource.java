package com.framgia.fdms.data.source;

import com.framgia.fdms.data.model.Dashboard;
import com.framgia.fdms.data.model.Request;
import com.framgia.fdms.data.model.Status;
import com.framgia.fdms.data.source.api.request.RequestCreatorRequest;
import java.util.List;
import rx.Observable;

/**
 * Created by beepi on 11/05/2017.
 */

public interface RequestDataSource {
    interface LocalDataSource {
    }

    interface RemoteDataSource {
        Observable<List<Dashboard>> getDashboardRequest();

        Observable<List<Request>> getRequests(int requestType, int requestStatusId, int relativeId,
                int perPage, int page);

        Observable<List<Status>> getStatus();

        Observable<Request> registerRequest(RequestCreatorRequest request);

        Observable<List<Request>> getTopRequest(int topRequest);

        Observable<List<Request>> sendAction(int requestId, int actionId);
    }
}
