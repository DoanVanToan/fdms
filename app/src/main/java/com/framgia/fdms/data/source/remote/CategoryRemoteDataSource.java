package com.framgia.fdms.data.source.remote;

import com.framgia.fdms.data.model.Category;
import com.framgia.fdms.data.model.Respone;
import com.framgia.fdms.data.source.CategoryDataSource;
import com.framgia.fdms.data.source.api.service.FDMSApi;
import com.framgia.fdms.utils.Utils;
import java.util.List;
import rx.Observable;
import rx.functions.Func1;

/**
 * Created by MyPC on 05/05/2017.
 */

public class CategoryRemoteDataSource extends BaseRemoteDataSource implements CategoryDataSource.RemoteDataSource {
    public CategoryRemoteDataSource(FDMSApi FDMSApi) {
        super(FDMSApi);
    }

    @Override
    public Observable<List<Category>> getListCategory() {
        return mFDMSApi.getListCategory()
                .flatMap(new Func1<Respone<List<Category>>, Observable<List<Category>>>() {
                    @Override
                    public Observable<List<Category>> call(Respone<List<Category>> listRespone) {
                       return Utils.getResponse(listRespone);
                    }
                });
    }
}
