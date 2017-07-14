package com.framgia.fdms.screen.assignment;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.framgia.fdms.BR;
import com.framgia.fdms.BaseRecyclerViewAdapter;
import com.framgia.fdms.R;
import com.framgia.fdms.data.model.Device;
import com.framgia.fdms.data.source.api.request.DeviceRequest;
import com.framgia.fdms.databinding.ItemAssignmentBinding;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by MyPC on 09/06/2017.
 */

public class AssignmentAdapter
        extends BaseRecyclerViewAdapter<Device, AssignmentAdapter.ViewHolder> {
    private List<Device> mDevices;
    private AssignmentViewModel mViewModel;

    protected AssignmentAdapter(@NonNull Context context, @NonNull AssignmentViewModel viewModel) {
        super(context);
        mViewModel = viewModel;
        mDevices = new ArrayList<>();
    }

    public List<Device> getData(){
        return mDevices == null ? new ArrayList<Device>() : mDevices;
    }

    public void addItem() {
        mDevices.add(new Device());
        notifyItemInserted(mDevices.size() - 1);
    }

    @Override
    public void onUpdatePage(List<Device> data) {
        if (data == null) {
            return;
        }
        mDevices.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemAssignmentBinding binding =
                DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                        R.layout.item_assignment, parent, false);
        return new ViewHolder(binding, mViewModel);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bindData(mDevices.get(position));
    }

    @Override
    public int getItemCount() {
        return mDevices == null ? 0 : mDevices.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private ItemAssignmentBinding mBinding;
        private AssignmentViewModel mViewModel;

        public ViewHolder(ItemAssignmentBinding binding, AssignmentViewModel viewModel) {
            super(binding.getRoot());
            mBinding = binding;
            mViewModel = viewModel;
        }

        void bindData(Device device) {
            if (device == null) {
                return;
            }

            ViewHolderModel model = new ViewHolderModel(device, mViewModel, this);
            mBinding.setViewHolderModel(model);
            mBinding.executePendingBindings();
        }

        @Override
        public void onClick(View view) {
            if (getAdapterPosition() >= 0 && getAdapterPosition() < mDevices.size()) {
                mDevices.remove(getAdapterPosition());
                notifyItemRemoved(getAdapterPosition());
                notifyItemRangeChanged(getAdapterPosition(), mDevices.size());
            }
        }
    }

    public class ViewHolderModel extends BaseObservable {
        private Device mDevice;
        private AssignmentViewModel mViewModel;
        private View.OnClickListener mOnDeleteClick;

        public ViewHolderModel(Device device, AssignmentViewModel viewModel,
                View.OnClickListener onDelteClick) {
            mDevice = device;
            mViewModel = viewModel;
            mOnDeleteClick = onDelteClick;
        }

        @Bindable
        public Device getDeviceRequest() {
            return mDevice;
        }

        public void setDeviceRequest(Device device) {
            mDevice = device;
            notifyPropertyChanged(BR.device);
        }

        @Bindable
        public AssignmentViewModel getViewModel() {
            return mViewModel;
        }

        public void setViewModel(AssignmentViewModel viewModel) {
            mViewModel = viewModel;
            notifyPropertyChanged(BR.viewModel);
        }

        @Bindable
        public View.OnClickListener getOnDelteClick() {
            return mOnDeleteClick;
        }

        public void setOnDelteClick(View.OnClickListener onDelteClick) {
            mOnDeleteClick = onDelteClick;
            notifyPropertyChanged(BR.onDelteClick);
        }
    }
}
