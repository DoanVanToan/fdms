package com.framgia.fdms.screen.listdevice;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.framgia.fdms.BaseRecyclerViewAdapter;
import com.framgia.fdms.R;
import com.framgia.fdms.data.model.Device;
import com.framgia.fdms.databinding.ItemListDataBinding;
import java.util.List;

/**
 * Created by MyPC on 26/04/2017.
 */

public class ListDeviceAdapter
        extends BaseRecyclerViewAdapter<Device, ListDeviceAdapter.ViewHolder> {
    private List<Device> mDevices;
    private ListDeviceViewModel mViewModel;

    protected ListDeviceAdapter(@NonNull Context context, @NonNull List<Device> devices,
            @NonNull ListDeviceViewModel viewModel) {
        super(context);
        mDevices = devices;
        mViewModel = viewModel;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemListDataBinding binding =
                DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                        R.layout.item_listdevice, parent, false);
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

    @Override
    public void onUpdatePage(List<Device> datas) {
        if (datas == null) {
            return;
        }
        mDevices.addAll(datas);
        notifyDataSetChanged();
    }

    public void clear() {
        mDevices.clear();
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private ItemListDataBinding mBinding;
        private ListDeviceViewModel mViewModel;

        public ViewHolder(ItemListDataBinding binding, ListDeviceViewModel viewModel) {
            super(binding.getRoot());
            mBinding = binding;
            mViewModel = viewModel;
        }

        void bindData(Device device) {
            if (device == null) {
                return;
            }

            mBinding.setViewModel(mViewModel);
            mBinding.setItem(device);
            mBinding.executePendingBindings();
        }
    }
}
