package com.framgia.fdms;

/**
 * Created by beepi on 09/05/2017.
 */

import android.databinding.BaseObservable;
import android.databinding.ObservableField;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import java.util.List;

public abstract class BaseFragmentModel extends BaseObservable
        implements BaseFragmentContract.ViewModel {
    protected ObservableField<Boolean> mIsLoadMore = new ObservableField<>(false);
    private ObservableField<Integer> mProgressBarVisibility = new ObservableField<>();
    protected BaseFragmentContract.Presenter mPresenter;

    private RecyclerView.OnScrollListener mScrollListenner = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            if (dy <= 0) {
                return;
            }

            LinearLayoutManager layoutManager =
                    (LinearLayoutManager) recyclerView.getLayoutManager();

            int visibleItemCount = layoutManager.getChildCount();
            int totalItemCount = layoutManager.getItemCount();
            int pastVisiblesItems = layoutManager.findFirstVisibleItemPosition();

            if (!mIsLoadMore.get() && (visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                mIsLoadMore.set(true);
                mPresenter.onLoadMore();
            }
        }
    };

    public RecyclerView.OnScrollListener getScrollListenner() {
        return mScrollListenner;
    }

    @Override
    public void showProgressbar() {
        mProgressBarVisibility.set(View.VISIBLE);
    }


    @Override
    public void hideProgressbar() {
        mProgressBarVisibility.set(View.GONE);
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void setPresenter(BaseFragmentContract.Presenter presenter) {
        mPresenter = presenter;
    }

    public ObservableField<Integer> getProgressBarVisibility() {
        return mProgressBarVisibility;
    }

}
