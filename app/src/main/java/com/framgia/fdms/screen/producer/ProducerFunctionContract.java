package com.framgia.fdms.screen.producer;

import com.framgia.fdms.BasePresenter;
import com.framgia.fdms.BaseViewModel;
import com.framgia.fdms.data.model.Producer;

/**
 * Created by beepi on 06/07/2017.
 */
public interface ProducerFunctionContract {
    interface ViewModel<T extends ProducerFunctionContract.ProducerPresenter>
        extends BaseViewModel<ProducerFunctionContract.ProducerPresenter> {
        void onEditProducerClick(Producer producer);
        void onDeleteProducerClick(Producer producer);
        void onEditSubmitClick(Producer producer);
        void onAddProducerClick();
    }

    interface ProducerPresenter extends BasePresenter {
    }
}
