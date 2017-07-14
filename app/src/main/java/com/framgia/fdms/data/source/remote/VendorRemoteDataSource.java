package com.framgia.fdms.data.source.remote;

import com.framgia.fdms.data.model.Producer;
import com.framgia.fdms.data.source.VendorDataSource;
import java.util.ArrayList;
import java.util.List;
import rx.Observable;

/**
 * Created by framgia on 03/07/2017.
 */

public final class VendorRemoteDataSource implements VendorDataSource.RemoteDataSource {
    private static VendorRemoteDataSource sInstances;

    private VendorRemoteDataSource() {
    }

    public static VendorRemoteDataSource getInstances() {
        if (sInstances == null) {
            sInstances = new VendorRemoteDataSource();
        }
        return sInstances;
    }

    @Override
    public Observable<List<Producer>> getListVendor() {
        // // TODO: later
        List<Producer> list = new ArrayList<>();
        list.add(new Producer("laptop", "mini"));
        list.add(new Producer("PC", "Cau hinh cao"));
        list.add(new Producer("modern", "dat"));
        list.add(new Producer("mobile", "re"));
        list.add(new Producer("PC2", "Cau hinh cao1"));
        list.add(new Producer("PC3", "Cau hinh cao2"));
        list.add(new Producer("laptop4", "mini7"));
        list.add(new Producer("PC5", "Cau hinh cao3"));
        list.add(new Producer("PC6", "Cau hinh cao4"));
        list.add(new Producer("laptop7", "mini2"));
        list.add(new Producer("PC8", "Cau hinh cao5"));
        list.add(new Producer("PC9", "Cau hinh cao6"));
        return Observable.just(list);
    }
}
