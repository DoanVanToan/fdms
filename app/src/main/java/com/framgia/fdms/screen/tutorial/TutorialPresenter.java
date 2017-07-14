package com.framgia.fdms.screen.tutorial;

import com.framgia.fdms.data.source.local.sharepref.SharePreferenceApi;

import static com.framgia.fdms.data.source.local.sharepref.SharePreferenceKey.IS_SHOW_TUTORIAL;

/**
 * Listens to user actions from the UI ({@link TutorialActivity}), retrieves the data and updates
 * the UI as required.
 */
public class TutorialPresenter implements TutorialContract.Presenter {
    private static final String TAG = TutorialPresenter.class.getName();

    private final TutorialContract.ViewModel mViewModel;
    private SharePreferenceApi mSharedPreferences;

    public TutorialPresenter(TutorialContract.ViewModel viewModel,
            SharePreferenceApi sharedPreferences) {
        mViewModel = viewModel;
        mSharedPreferences = sharedPreferences;
    }

    @Override
    public void onStart() {
        if (mSharedPreferences.get(IS_SHOW_TUTORIAL, Boolean.class)) {
            mViewModel.onSkipClick();
        } else {
            saveFirstLogin();
        }
    }

    @Override
    public void onStop() {
    }

    public void saveFirstLogin() {
        mSharedPreferences.put(IS_SHOW_TUTORIAL, true);
    }
}
