package com.framgia.fdms.screen.producer.marker;

import com.framgia.fdms.data.model.Producer;
import com.framgia.fdms.screen.producer.ProducerFunctionContract;

import java.util.List;

/**
 * This specifies the contract between the view and the presenter.
 */
interface MarkerContract {
    /**
     * View.
     */
    interface ViewModel extends ProducerFunctionContract.ViewModel<Presenter> {
        void onLoadMakerFail();
        void onLoadMakerSucessfully(List<Producer> producers);
    }

    /**
     * Presenter.
     */
    interface Presenter extends ProducerFunctionContract.ProducerPresenter {
        void getMakers(int page, int perPage);
    }
}
