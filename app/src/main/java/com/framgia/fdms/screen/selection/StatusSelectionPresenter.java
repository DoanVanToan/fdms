package com.framgia.fdms.screen.selection;

/**
 * Listens to user actions from the UI ({@link StatusSelectionActivity}), retrieves the data and
 * updates
 * the UI as required.
 */
public final class StatusSelectionPresenter implements StatusSelectionContract.Presenter {
    private static final String TAG = StatusSelectionPresenter.class.getName();

    private final StatusSelectionContract.ViewModel mViewModel;

    public StatusSelectionPresenter(StatusSelectionContract.ViewModel viewModel) {
        mViewModel = viewModel;
    }

    @Override
    public void onStart() {
    }

    @Override
    public void onStop() {
    }
}
