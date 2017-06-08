package com.framgia.fdms.screen.requestdetail;

import com.framgia.fdms.data.model.Category;
import com.framgia.fdms.data.model.Request;
import com.framgia.fdms.data.source.CategoryRepository;
import com.framgia.fdms.data.source.RequestRepository;
import com.framgia.fdms.data.source.api.service.FDMSServiceClient;
import com.framgia.fdms.data.source.remote.CategoryRemoteDataSource;
import com.framgia.fdms.data.source.remote.RequestRemoteDataSource;
import java.util.List;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by tuanbg on 5/30/17.
 */

public class RequestDetailPresenter implements RequestDetailContract.Presenter {
    private RequestDetailContract.ViewModel mViewModel;
    private CategoryRepository mCategoryRepository;
    private CompositeSubscription mSubscription;
    private RequestRepository mRequestRepository;

    public RequestDetailPresenter(RequestDetailContract.ViewModel viewModel) {
        mViewModel = viewModel;
        mSubscription = new CompositeSubscription();
        mCategoryRepository = new CategoryRepository(
                new CategoryRemoteDataSource(FDMSServiceClient.getInstance()));
        mRequestRepository =
                new RequestRepository(new RequestRemoteDataSource(FDMSServiceClient.getInstance()));
        getListCategory();
    }

    public void getListCategory() {
        Subscription subscription = mCategoryRepository.getListCategory()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mViewModel.showProgressbar();
                    }
                })
                .subscribe(new Subscriber<List<Category>>() {
                    @Override
                    public void onCompleted() {
                        mViewModel.hideProgressbar();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mViewModel.hideProgressbar();
                        mViewModel.onLoadError(e.getMessage());
                    }

                    @Override
                    public void onNext(List<Category> categories) {
                        mViewModel.onGetCategorySuccess(categories);
                    }
                });
        mSubscription.add(subscription);
    }

    public void updateActionRequest(int requestId, int actionId) {
        Subscription subscription = mRequestRepository.updateActionRequest(requestId, actionId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mViewModel.showProgressbar();
                    }
                })
                .subscribe(new Subscriber<Request>() {
                    @Override
                    public void onCompleted() {
                        mViewModel.hideProgressbar();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mViewModel.hideProgressbar();
                        mViewModel.onLoadError(e.getMessage());
                    }

                    @Override
                    public void onNext(Request request) {
                        mViewModel.editActionSuccess();
                    }
                });
        mSubscription.add(subscription);
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {
        mSubscription.clear();
    }
}
