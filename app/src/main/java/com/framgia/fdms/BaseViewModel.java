package com.framgia.fdms;

/**
 * Created by levutantuan on 4/3/17.
 */
public interface BaseViewModel<T extends BasePresenter> {
    void onStart();

    void onStop();

    void setPresenter(T presenter);
}
