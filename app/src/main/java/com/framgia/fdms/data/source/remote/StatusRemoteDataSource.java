package com.framgia.fdms.data.source.remote;

import com.framgia.fdms.data.model.Respone;
import com.framgia.fdms.data.model.Status;
import com.framgia.fdms.data.source.StatusDataSource;
import com.framgia.fdms.data.source.api.service.FDMSApi;
import com.framgia.fdms.utils.Utils;
import java.util.List;
import rx.Observable;
import rx.functions.Func1;

/**
 * Created by MyPC on 05/05/2017.
 */

public class StatusRemoteDataSource extends BaseRemoteDataSource
        implements StatusDataSource.RemoteDataSource {

    public StatusRemoteDataSource(FDMSApi FDMSApi) {
        super(FDMSApi);
    }

    @Override
    public Observable<List<Status>> getListStatus() {
        return mFDMSApi.getListStatus()
                .flatMap(new Func1<Respone<List<Status>>, Observable<List<Status>>>() {
                    @Override
                    public Observable<List<Status>> call(Respone<List<Status>> listRespone) {
                        return Utils.getResponse(listRespone);
                    }
                });
    }

    @Override
    public Observable<List<Status>> getListStatusRequest() {
        return mFDMSApi.getListStatusRequest()
                .flatMap(new Func1<Respone<List<Status>>, Observable<List<Status>>>() {
                    @Override
                    public Observable<List<Status>> call(Respone<List<Status>> listRespone) {
                        return Utils.getResponse(listRespone);
                    }
                });
    }

    @Override
    public Observable<List<Status>> getListRelative() {
        return mFDMSApi.getListRelative()
                .flatMap(new Func1<Respone<List<Status>>, Observable<List<Status>>>() {

                    @Override
                    public Observable<List<Status>> call(Respone<List<Status>> listRespone) {
                        return Utils.getResponse(listRespone);
                    }
                });
    }

    @Override
    public Observable<List<Status>> getListAssignee() {
        return mFDMSApi.getListAssign()
                .flatMap(new Func1<Respone<List<Status>>, Observable<List<Status>>>() {

                    @Override
                    public Observable<List<Status>> call(Respone<List<Status>> listRespone) {
                        return Utils.getResponse(listRespone);
                    }
                });
    }
}
