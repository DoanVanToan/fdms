package com.framgia.fdms.data.source.remote;

import com.framgia.fdms.data.model.Respone;
import com.framgia.fdms.data.model.Status;
import com.framgia.fdms.data.source.BranchDataSource;
import com.framgia.fdms.data.source.api.service.FDMSApi;
import com.framgia.fdms.utils.Utils;
import java.util.ArrayList;
import java.util.List;
import rx.Observable;
import rx.functions.Func1;

/**
 * Created by MyPC on 13/06/2017.
 */

public class BranchRemoteDataSource extends BaseRemoteDataSource implements BranchDataSource {
    public BranchRemoteDataSource(FDMSApi FDMSApi) {
        super(FDMSApi);
    }

    @Override
    public Observable<List<Status>> getListBranch() {
        return mFDMSApi.getListBranch().flatMap(new Func1<Respone<List<Status>>, Observable<List<Status>>>() {
            @Override
            public Observable<List<Status>> call(Respone<List<Status>> listRespone) {
                return Utils.getResponse(listRespone);
            }
        });
    }
}
