package com.framgia.fdms.screen.scanner;

/**
 * Listens to user actions from the UI ({@link ScannerActivity}), retrieves the data and updates
 * the UI as required.
 */
final class ScannerPresenter implements ScannerContract.Presenter {
    private static final String TAG = ScannerPresenter.class.getName();

    private final ScannerContract.ViewModel mViewModel;

    public ScannerPresenter(ScannerContract.ViewModel viewModel) {
        mViewModel = viewModel;
    }

    @Override
    public void onStart() {
    }

    @Override
    public void onStop() {
    }
}
