package com.framgia.fdms.data.source.remote;

import com.framgia.fdms.data.model.Producer;
import com.framgia.fdms.data.source.MakerDataSource;
import com.framgia.fdms.data.source.api.service.FDMSApi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;

import static com.framgia.fdms.utils.Constant.ApiParram.PAGE;
import static com.framgia.fdms.utils.Constant.ApiParram.PER_PAGE;
import static com.framgia.fdms.utils.Constant.OUT_OF_INDEX;

/**
 * Created by beepi on 05/07/2017.
 */
public class MakerRemoteDataSource extends BaseRemoteDataSource
    implements MakerDataSource.RemoteDataSource {
    public MakerRemoteDataSource(FDMSApi fdmsApi) {
        super(fdmsApi);
    }

    @Override
    public Observable<List<Producer>> getListMarkers(int page, int perPage) {
        /**
         * todo later
         */
        List<Producer> list = new ArrayList<>();
        list.add(new Producer("Dell", "Dell vostro 14"));
        list.add(new Producer("HP", "HP"));
        list.add(new Producer("Dell", "Dell vostro 15"));
        list.add(new Producer("Dell", "Dell vostro 14"));
        list.add(new Producer("HP", "HP"));
        list.add(new Producer("Dell", "Dell vostro 15"));
        list.add(new Producer("Dell", "Dell vostro 14"));
        list.add(new Producer("HP", "HP"));
        list.add(new Producer("Dell", "Dell vostro 15"));
        list.add(new Producer("Dell", "Dell vostro 14"));
        list.add(new Producer("HP", "HP"));
        list.add(new Producer("Dell", "Dell vostro 15"));
        list.add(new Producer("Dell", "Dell vostro 14"));
        list.add(new Producer("HP", "HP"));
        list.add(new Producer("Dell", "Dell vostro 15"));
        return Observable.just(list);
    }

    private Map<String, String> getParams(int page, int perPage) {
        Map<String, String> parrams = new HashMap<>();
        if (page != OUT_OF_INDEX) {
            parrams.put(PAGE, String.valueOf(page));
        }
        if (perPage != OUT_OF_INDEX) {
            parrams.put(PER_PAGE, String.valueOf(perPage));
        }
        return parrams;
    }
}
