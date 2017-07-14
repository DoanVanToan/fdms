package com.framgia.fdms.screen.selection;

import com.framgia.fdms.BasePresenter;
import com.framgia.fdms.BaseViewModel;
import com.framgia.fdms.data.model.Category;
import com.framgia.fdms.data.model.Status;

/**
 * This specifies the contract between the view and the presenter.
 */
public interface StatusSelectionContract {
    /**
     * View.
     */
    interface ViewModel extends BaseViewModel<Presenter> {
        void onSelectedItem(Category category, Status status, StatusSelectionType type,
                int position);

        void onSearchData(String newText);
    }

    /**
     * Presenter.
     */
    interface Presenter extends BasePresenter {
    }
}
