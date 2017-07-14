package com.framgia.fdms.data.source;

import com.framgia.fdms.data.model.Producer;

import java.util.List;

import rx.Observable;

/**
 * Created by beepi on 05/07/2017.
 */
public interface MakerRepositoryContract {
    Observable<List<Producer>> getMakers(int page, int perpage);
}
