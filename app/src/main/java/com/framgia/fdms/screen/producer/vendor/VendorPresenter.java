package com.framgia.fdms.screen.producer.vendor;

import com.framgia.fdms.data.model.Producer;
import com.framgia.fdms.data.source.VendorRepository;
import java.util.List;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Listens to user actions from the UI ({@link VendorFragment}), retrieves the data and updates
 * the UI as required.
 */
final class VendorPresenter implements VendorContract.Presenter {
    private static final String TAG = VendorPresenter.class.getName();

    private final VendorContract.ViewModel mViewModel;
    private VendorRepository mRepository = VendorRepository.getInstances();
    private CompositeSubscription mSubscription;

    public VendorPresenter(VendorContract.ViewModel viewModel) {
        mViewModel = viewModel;
        mSubscription = new CompositeSubscription();
    }

    @Override
    public void onStart() {
        getVendors();
    }

    @Override
    public void onStop() {
    }

    @Override
    public void getVendors() {
        Subscription subscription = mRepository.getListVendor()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Producer>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        mViewModel.onLoadVendorFailed();
                    }

                    @Override
                    public void onNext(List<Producer> vendors) {
                        mViewModel.onLoadVendorSuccess(vendors);
                    }
                });
        mSubscription.add(subscription);
    }
}
