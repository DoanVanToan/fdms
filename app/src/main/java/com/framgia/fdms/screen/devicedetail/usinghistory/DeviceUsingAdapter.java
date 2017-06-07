package com.framgia.fdms.screen.devicedetail.usinghistory;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.framgia.fdms.data.model.DeviceUsingHistory;
import com.framgia.fdms.databinding.ItemDeviceUsingBinding;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by framgia on 23/05/2017.
 */

public class DeviceUsingAdapter extends RecyclerView.Adapter<DeviceUsingAdapter.ViewHolder> {

    private Context mContext;
    private List<DeviceUsingHistory> mHistories = new ArrayList<>();

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) mContext = parent.getContext();
        ItemDeviceUsingBinding binding =
                ItemDeviceUsingBinding.inflate(LayoutInflater.from(mContext), parent, false);
        return new ViewHolder(binding);
    }

    public void addData(List<DeviceUsingHistory> deviceUsingHistories) {
        if (deviceUsingHistories == null) return;
        mHistories.addAll(deviceUsingHistories);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bindData(mHistories.get(position));
    }

    @Override
    public int getItemCount() {
        return mHistories != null ? mHistories.size() : 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private ItemDeviceUsingBinding mBinding;

        public ViewHolder(ItemDeviceUsingBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        public void bindData(DeviceUsingHistory item) {
            if (item == null) return;
            mBinding.setDeviceUsingHistory(item);
            mBinding.executePendingBindings();
        }
    }
}
