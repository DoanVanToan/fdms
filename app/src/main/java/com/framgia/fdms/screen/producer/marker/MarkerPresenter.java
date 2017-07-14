package com.framgia.fdms.screen.producer.marker;

import com.framgia.fdms.data.model.Producer;
import com.framgia.fdms.data.source.MakerRepository;
import com.framgia.fdms.data.source.MakerRepositoryContract;

import java.util.List;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

import static com.framgia.fdms.utils.Constant.FIRST_PAGE;
import static com.framgia.fdms.utils.Constant.PER_PAGE;

/**
 * Listens to user actions from the UI ({@link MarkerFragment}), retrieves the data and
 * updates
 * the UI as required.
 */
public class MarkerPresenter implements MarkerContract.Presenter {
    private final MarkerContract.ViewModel mViewModel;
    private MakerRepositoryContract mRepository;
    private CompositeSubscription mSubscription;

    public MarkerPresenter(MarkerContract.ViewModel viewModel, MakerRepository repository) {
        mViewModel = viewModel;
        mRepository = repository;
        mSubscription = new CompositeSubscription();
        getMakers(FIRST_PAGE, PER_PAGE);
    }

    @Override
    public void onStart() {
    }

    @Override
    public void onStop() {
        mSubscription.clear();
    }

    @Override
    public void getMakers(int page, int perPage) {
        Subscription subscription = mRepository.getMakers(page, perPage)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Subscriber<List<Producer>>() {
                @Override
                public void onCompleted() {
                }

                @Override
                public void onError(Throwable e) {
                    mViewModel.onLoadMakerFail();
                }

                @Override
                public void onNext(List<Producer> makers) {
                    mViewModel.onLoadMakerSucessfully(makers);
                }
            });
        mSubscription.add(subscription);
    }
}
