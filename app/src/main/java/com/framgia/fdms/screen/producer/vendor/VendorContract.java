package com.framgia.fdms.screen.producer.vendor;

import com.framgia.fdms.data.model.Producer;
import com.framgia.fdms.screen.producer.ProducerFunctionContract;

import java.util.List;

/**
 * This specifies the contract between the view and the presenter.
 */
interface VendorContract {
    /**
     * View.
     */
    interface ViewModel extends ProducerFunctionContract.ViewModel<Presenter> {
        void onLoadVendorSuccess(List<Producer> vendors);
        void onLoadVendorFailed();
    }

    /**
     * Presenter.
     */
    interface Presenter extends ProducerFunctionContract.ProducerPresenter {
        void getVendors();
    }
}
