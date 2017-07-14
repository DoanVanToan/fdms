package com.framgia.fdms.data.source;

import com.framgia.fdms.data.model.Status;
import java.util.List;
import rx.Observable;

/**
 * Created by MyPC on 13/06/2017.
 */

public interface BranchDataSource {
    Observable<List<Status>> getListBranch();
}
