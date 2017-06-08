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

public class RequestRepository implements RequestRepositoryContract {
    private RequestDataSource.RemoteDataSource mRemoteDataSource;
    private static RequestRepository requestRepository;

    public static RequestRepository getInstant(
            RequestDataSource.RemoteDataSource remoteDataSource) {
        if (requestRepository == null) {
            requestRepository = new RequestRepository(remoteDataSource);
        }
        return requestRepository;
    }

    public RequestRepository(RequestDataSource.RemoteDataSource remoteDataSource) {
        mRemoteDataSource = remoteDataSource;
    }

    @Override
    public Observable<List<Request>> getRequests(int requestType, int requestStatusId,
            int relativeId, int perPage, int page) {
        return mRemoteDataSource.getRequests(requestType, requestStatusId, relativeId, perPage,
                page);
    }

    @Override
    public Observable<List<Status>> getStatus() {
        return mRemoteDataSource.getStatus();
    }

    @Override
    public Observable<List<Dashboard>> getDashboardRequest() {
        return mRemoteDataSource.getDashboardRequest();
    }

    @Override
    public Observable<Request> registerRequest(RequestCreatorRequest request) {
        return mRemoteDataSource.registerRequest(request);
    }

    @Override
    public Observable<List<Request>> getTopRequest(int topRequest) {
        return mRemoteDataSource.getTopRequest(topRequest);
    }

    @Override
    public Observable<Request> updateActionRequest(int requestId, int statusId) {
        return mRemoteDataSource.updateActionRequest(requestId, statusId);
    }
}
