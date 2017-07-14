package com.framgia.fdms.screen.devicedetail.history;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.framgia.fdms.data.model.DeviceHistoryDetail;
import com.framgia.fdms.databinding.ItemHistoryDetailBinding;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hoang Van Nha on 5/23/2017.
 * <></>
 */

public class DeviceDetailHistoryAdapter
        extends RecyclerView.Adapter<DeviceDetailHistoryAdapter.HistoryDetailHolder> {
    private List<DeviceHistoryDetail> mDeviceHistoryDetails = new ArrayList<>();
    private LayoutInflater mInflater;

    public void addData(List<DeviceHistoryDetail> deviceHistoryDetails) {
        if (deviceHistoryDetails == null) return;
        mDeviceHistoryDetails.addAll(deviceHistoryDetails);
        notifyDataSetChanged();
    }

    @Override
    public HistoryDetailHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mInflater == null) mInflater = LayoutInflater.from(parent.getContext());
        ItemHistoryDetailBinding binding =
                ItemHistoryDetailBinding.inflate(mInflater, parent, false);
        return new HistoryDetailHolder(binding);
    }

    @Override
    public void onBindViewHolder(HistoryDetailHolder holder, int position) {
        DeviceHistoryDetail deviceHistoryDetail = mDeviceHistoryDetails.get(position);
        if (mDeviceHistoryDetails != null) holder.bind(deviceHistoryDetail, position);
    }

    @Override
    public int getItemCount() {
        return mDeviceHistoryDetails == null ? 0 : mDeviceHistoryDetails.size();
    }

    public class HistoryDetailHolder extends RecyclerView.ViewHolder {

        private final ItemHistoryDetailBinding mBinding;

        public HistoryDetailHolder(ItemHistoryDetailBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        private void bind(DeviceHistoryDetail deviceHistoryDetail, int position) {
            mBinding.setDeviceHistoryDetail(deviceHistoryDetail);
            mBinding.setPosition(position);
            mBinding.executePendingBindings();
        }
    }
}
