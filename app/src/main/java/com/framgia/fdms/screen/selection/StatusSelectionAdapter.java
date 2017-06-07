package com.framgia.fdms.screen.selection;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.framgia.fdms.data.model.Category;
import com.framgia.fdms.data.model.Status;
import com.framgia.fdms.databinding.ItemStatusSelectionBinding;
import java.util.ArrayList;
import java.util.List;

import static com.framgia.fdms.utils.Constant.ACTION_CLEAR;
import static com.framgia.fdms.utils.Constant.OUT_OF_INDEX;

/**
 * Created by Hoang Van Nha on 5/24/2017.
 * <></>
 */

public class StatusSelectionAdapter
        extends RecyclerView.Adapter<StatusSelectionAdapter.SelectionHolder> {

    private StatusSelectionContract.ViewModel mViewModel;
    private StatusSelectionType mStatusType = StatusSelectionType.STATUS;
    private LayoutInflater mInflater;
    private List<Category> mCategories;
    private List<Category> mCategoriesTemp = new ArrayList<>();
    private List<Status> mStatuses;
    private List<Status> mStatusesTemp = new ArrayList<>();
    public static final int FIRST_INDEX = 0;

    public StatusSelectionAdapter(StatusSelectionContract.ViewModel viewModel,
            List<Category> categories, StatusSelectionType type) {
        mViewModel = viewModel;
        mCategories = categories;
        mCategories.add(FIRST_INDEX, new Category(OUT_OF_INDEX, ACTION_CLEAR));
        mCategoriesTemp.addAll(categories);
        mStatusType = type;
    }

    public StatusSelectionAdapter(StatusSelectionContract.ViewModel viewModel,
            List<Status> statuses) {
        mViewModel = viewModel;
        mStatuses = statuses;
        mStatuses.add(FIRST_INDEX, new Status(OUT_OF_INDEX, ACTION_CLEAR));
        mStatusesTemp.addAll(statuses);
    }

    @Override
    public SelectionHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mInflater == null) mInflater = LayoutInflater.from(parent.getContext());
        ItemStatusSelectionBinding binding =
                ItemStatusSelectionBinding.inflate(mInflater, parent, false);
        binding.setViewModel(mViewModel);
        binding.setType(mStatusType);
        return new SelectionHolder(binding);
    }

    @Override
    public void onBindViewHolder(SelectionHolder holder, int position) {
        switch (mStatusType) {
            case CATEGORY:
                Category category = mCategories.get(position);
                if (category != null) holder.bind(category, position);
                break;
            case STATUS:
            default:
                Status status = mStatuses.get(position);
                if (status != null) holder.bind(status, position);
                break;
        }
    }

    @Override
    public int getItemCount() {
        switch (mStatusType) {
            case CATEGORY:
                return mCategories == null ? 0 : mCategories.size();
            case STATUS:
            default:
                return mStatuses == null ? 0 : mStatuses.size();
        }
    }

    public void filter(String newText) {
        switch (mStatusType) {
            case CATEGORY:
                mCategories.clear();
                if (TextUtils.isEmpty(newText)) {
                    mCategories.addAll(mCategoriesTemp);
                } else {
                    newText = newText.toLowerCase();
                    for (Category category : mCategoriesTemp) {
                        if (category.getName().toLowerCase().contains(newText)) {
                            mCategories.add(category);
                        }
                    }
                }
                notifyDataSetChanged();
                break;
            case STATUS:
            default:
                mStatuses.clear();
                if (TextUtils.isEmpty(newText)) {
                    mStatuses.addAll(mStatusesTemp);
                } else {
                    newText = newText.toLowerCase();
                    for (Status status : mStatusesTemp) {
                        if (status.getName().toLowerCase().contains(newText)) {
                            mStatuses.add(status);
                        }
                    }
                }
                notifyDataSetChanged();
                break;
        }
    }

    public class SelectionHolder extends RecyclerView.ViewHolder {
        private final ItemStatusSelectionBinding mBinding;

        public SelectionHolder(ItemStatusSelectionBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        private void bind(Category category, int position) {
            mBinding.setCategory(category);
            mBinding.setPosition(position);
            mBinding.executePendingBindings();
        }

        private void bind(Status status, int position) {
            mBinding.setStatus(status);
            mBinding.setPosition(position);
            mBinding.executePendingBindings();
        }
    }
}
