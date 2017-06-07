package com.framgia.fdms.data.source;

import com.framgia.fdms.data.model.Category;
import com.framgia.fdms.data.source.remote.CategoryRemoteDataSource;
import com.framgia.fdms.data.source.remote.StatusRemoteDataSource;
import java.util.List;
import rx.Observable;

/**
 * Created by MyPC on 05/05/2017.
 */

public class CategoryRepository {
    private CategoryRemoteDataSource mCategoryRemoteDataSource;

    public CategoryRepository(CategoryRemoteDataSource categoryRemoteDataSource) {
        mCategoryRemoteDataSource = categoryRemoteDataSource;
    }

    public Observable<List<Category>> getListCategory(){
        return mCategoryRemoteDataSource.getListCategory();
    }
}
