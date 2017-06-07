package com.framgia.fdms.screen.selection;

import android.app.Activity;
import android.content.Intent;
import android.databinding.ObservableField;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.framgia.fdms.data.model.Category;
import com.framgia.fdms.data.model.Status;
import java.util.List;

import static com.framgia.fdms.utils.Constant.BundleConstant.BUNDLE_CATEGORY;
import static com.framgia.fdms.utils.Constant.BundleConstant.BUNDLE_STATUE;
import static com.framgia.fdms.utils.Constant.BundleConstant.BUNDLE_TYPE;

/**
 * Exposes the data to be used in the StatusSelection screen.
 */

public class StatusSelectionViewModel implements StatusSelectionContract.ViewModel {

    private AppCompatActivity mActivity;
    private List<Category> mCategories;
    private List<Status> mStatuses;
    private StatusSelectionType mSelectionType;
    private StatusSelectionContract.Presenter mPresenter;
    private ObservableField<StatusSelectionAdapter> mAdapter = new ObservableField<>();

    public StatusSelectionViewModel(AppCompatActivity activity, List<Category> categories,
            List<Status> statuses, StatusSelectionType selectionType) {
        mActivity = activity;
        mCategories = categories;
        mStatuses = statuses;
        mSelectionType = selectionType;
        if (mSelectionType == StatusSelectionType.CATEGORY) {
            mAdapter.set(new StatusSelectionAdapter(this, categories, mSelectionType));
        } else {
            mAdapter.set(new StatusSelectionAdapter(this, mStatuses));
        }
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
    public void setPresenter(StatusSelectionContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onSelectedItem(Category category, Status status, StatusSelectionType type,
            int position) {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putParcelable(BUNDLE_CATEGORY, category);
        bundle.putParcelable(BUNDLE_STATUE, status);
        bundle.putSerializable(BUNDLE_TYPE, type);
        intent.putExtras(bundle);
        mActivity.setResult(Activity.RESULT_OK, intent);
        mActivity.finish();
    }

    @Override
    public void onSearchData(String newText) {
        mAdapter.get().filter(newText);
    }

    public ObservableField<StatusSelectionAdapter> getAdapter() {
        return mAdapter;
    }

    public AppCompatActivity getActivity() {
        return mActivity;
    }

    public StatusSelectionType getSelectionType() {
        return mSelectionType;
    }
}
