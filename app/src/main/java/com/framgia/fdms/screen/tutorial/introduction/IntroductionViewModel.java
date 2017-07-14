package com.framgia.fdms.screen.tutorial.introduction;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import com.framgia.fdms.BR;
import com.framgia.fdms.data.model.Introduction;

/**
 * Exposes the data to be used in the Introduction screen.
 */

public class IntroductionViewModel extends BaseObservable
        implements IntroductionContract.ViewModel {

    private IntroductionContract.Presenter mPresenter;
    private Introduction mIntroduction;

    public IntroductionViewModel(Introduction introduction) {
        mIntroduction = introduction;
    }

    @Override
    public void onStart() {
        mPresenter.onStart();
    }

    @Override
    public void onStop() {
        mPresenter.onStop();
    }

    @Override
    public void setPresenter(IntroductionContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Bindable
    public Introduction getIntroduction() {
        return mIntroduction;
    }

    public void setIntroduction(Introduction introduction) {
        mIntroduction = introduction;
        notifyPropertyChanged(BR.introduction);
    }
}
