package com.framgia.fdms.screen.tutorial.introduction;

/**
 * Listens to user actions from the UI ({@link IntroductionFragment}), retrieves the data and
 * updates
 * the UI as required.
 */
public class IntroductionPresenter implements IntroductionContract.Presenter {
    private static final String TAG = IntroductionPresenter.class.getName();

    private final IntroductionContract.ViewModel mViewModel;

    public IntroductionPresenter(IntroductionContract.ViewModel viewModel) {
        mViewModel = viewModel;
    }

    @Override
    public void onStart() {
    }

    @Override
    public void onStop() {
    }
}
