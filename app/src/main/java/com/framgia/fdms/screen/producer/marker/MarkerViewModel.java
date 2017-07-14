package com.framgia.fdms.screen.producer.marker;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import com.android.databinding.library.baseAdapters.BR;
import com.arlib.floatingsearchview.FloatingSearchView;
import com.framgia.fdms.R;
import com.framgia.fdms.data.model.Producer;
import com.framgia.fdms.screen.producer.ProducerDialog;
import com.framgia.fdms.screen.producer.ProducerFunctionContract;

import java.util.List;

import static com.framgia.fdms.utils.Constant.OUT_OF_INDEX;

/**
 * Exposes the data to be used in the MarkerFragment screen.
 */
public class MarkerViewModel extends BaseObservable
    implements MarkerContract.ViewModel, FloatingSearchView.OnQueryChangeListener {
    private static final String TAG_MAKER_DIALOG = "MAKER_DIALOG";
    private ProducerFunctionContract.ProducerPresenter mPresenter;
    private MakerApdater mAdapter;
    private FragmentActivity mActivity;
    private ProducerDialog mDialog;
    private List<Producer> mMakers;
    private int mPositionScroll = OUT_OF_INDEX;
    public MarkerViewModel(FragmentActivity activity) {
        mActivity = activity;
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
    public void setPresenter(ProducerFunctionContract.ProducerPresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onLoadMakerFail() {
        Snackbar.make(mActivity.findViewById(android.R.id.content), R.string.error_load_makers,
            Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onLoadMakerSucessfully(List<Producer> makers) {
        mMakers = makers;
        mAdapter = new MakerApdater(makers, this);
        setAdapter(mAdapter);
    }

    @Bindable
    public MakerApdater getAdapter() {
        return mAdapter;
    }

    public void setAdapter(MakerApdater adapter) {
        mAdapter = adapter;
        notifyPropertyChanged(BR.adapter);
    }

    @Override
    public void onSearchTextChanged(String oldQuery, String newQuery) {
        //// TODO: 06/07/2017 later
    }

    @Override
    public void onEditProducerClick(Producer maker) {
        mDialog = ProducerDialog.newInstant(maker, mActivity.getResources()
            .getString(R.string.action_edit));
        mDialog.show(mActivity.getSupportFragmentManager(), TAG_MAKER_DIALOG);
    }

    @Override
    public void onDeleteProducerClick(final Producer maker) {
        if(mMakers == null || maker == null) {
            return;
        }
        final int indexRemove = mMakers.indexOf(maker);
        mMakers.remove(maker);
        mAdapter.notifyItemRemoved(indexRemove);
        Snackbar.make(mActivity.findViewById(android.R.id.content), R.string.title_vendor_delete,
            Snackbar.LENGTH_LONG)
            .setAction(R.string.title_undo, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mMakers.add(indexRemove, maker);
                    mAdapter.notifyItemInserted(indexRemove);
                    setPositionScroll(indexRemove);
                }
            })
            .addCallback(new Snackbar.Callback() {
                @Override
                public void onDismissed(Snackbar transientBottomBar, int event) {
                    super.onDismissed(transientBottomBar, event);
                    setPositionScroll(OUT_OF_INDEX);
                    // TODO: next task
                }
            })
            .show();
    }

    @Override
    public void onEditSubmitClick(Producer maker) {
    }

    @Override
    public void onAddProducerClick() {
    }

    @Bindable
    public int getPositionScroll() {
        return mPositionScroll;
    }

    public void setPositionScroll(int positionScroll) {
        mPositionScroll = positionScroll;
        notifyPropertyChanged(BR.positionScroll);
    }
}
