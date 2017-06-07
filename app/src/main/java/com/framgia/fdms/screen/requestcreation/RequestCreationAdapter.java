package com.framgia.fdms.screen.requestcreation;

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
import com.framgia.fdms.data.source.api.request.DeviceRequest;
import com.framgia.fdms.databinding.ItemRequestCreationBinding;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by MyPC on 18/05/2017.
 */

public class RequestCreationAdapter
        extends BaseRecyclerViewAdapter<DeviceRequest, RequestCreationAdapter.ViewHolder> {
    private List<DeviceRequest> mDeviceRequests;
    private RequestCreationViewModel mViewModel;
    private static final int NUMBER_DEFAULT = 1;

    public RequestCreationAdapter(@NonNull Context context,
            @NonNull RequestCreationViewModel viewModel) {
        super(context);
        mViewModel = viewModel;
        mDeviceRequests = new ArrayList<>();
    }

    public void addItem() {
        mDeviceRequests.add(new DeviceRequest(NUMBER_DEFAULT));
        notifyItemInserted(mDeviceRequests.size() - 1);
    }

    public List<DeviceRequest> getData() {
        List<DeviceRequest> deviceRequests = new ArrayList<>();
        for (DeviceRequest deviceRequest : mDeviceRequests) {
            if (deviceRequest.getDescription() != null && deviceRequest.getDeviceCategoryId() > 0) {
                deviceRequests.add(deviceRequest);
            }
        }
        return deviceRequests;
    }

    @Override
    public void onUpdatePage(List<DeviceRequest> data) {
        if (data == null) {
            return;
        }
        mDeviceRequests.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public RequestCreationAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemRequestCreationBinding binding =
                DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                        R.layout.item_request_creation, parent, false);
        return new ViewHolder(binding, mViewModel);
    }

    @Override
    public void onBindViewHolder(final RequestCreationAdapter.ViewHolder holder,
            final int position) {
        holder.bindData(mDeviceRequests.get(position));
    }

    @Override
    public int getItemCount() {
        return mDeviceRequests == null ? 0 : mDeviceRequests.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ItemRequestCreationBinding mBinding;
        private RequestCreationViewModel mViewModel;

        public ViewHolder(ItemRequestCreationBinding binding, RequestCreationViewModel viewModel) {
            super(binding.getRoot());
            mBinding = binding;
            mViewModel = viewModel;
        }

        void bindData(DeviceRequest deviceRequest) {
            if (deviceRequest == null) {
                return;
            }

            ViewHolderModel model = new ViewHolderModel(deviceRequest, mViewModel, this);
            mBinding.setViewHolderModel(model);
            mBinding.executePendingBindings();
        }

        @Override
        public void onClick(View view) {
            if (getAdapterPosition() >= 0 && getAdapterPosition() < mDeviceRequests.size()) {
                mDeviceRequests.remove(getAdapterPosition());
                notifyItemRemoved(getAdapterPosition());
                notifyItemRangeChanged(getAdapterPosition(), mDeviceRequests.size());
            }
        }
    }

    public class ViewHolderModel extends BaseObservable {
        private DeviceRequest mDeviceRequest;
        private RequestCreationViewModel mViewModel;
        private View.OnClickListener mOnDelteClick;

        public ViewHolderModel(DeviceRequest deviceRequest, RequestCreationViewModel viewModel,
                View.OnClickListener onDelteClick) {
            mDeviceRequest = deviceRequest;
            mViewModel = viewModel;
            mOnDelteClick = onDelteClick;
        }

        @Bindable
        public DeviceRequest getDeviceRequest() {
            return mDeviceRequest;
        }

        public void setDeviceRequest(DeviceRequest deviceRequest) {
            mDeviceRequest = deviceRequest;
            notifyPropertyChanged(BR.deviceRequest);
        }

        @Bindable
        public RequestCreationViewModel getViewModel() {
            return mViewModel;
        }

        public void setViewModel(RequestCreationViewModel viewModel) {
            mViewModel = viewModel;
            notifyPropertyChanged(BR.viewModel);
        }

        @Bindable
        public View.OnClickListener getOnDelteClick() {
            return mOnDelteClick;
        }

        public void setOnDelteClick(View.OnClickListener onDelteClick) {
            mOnDelteClick = onDelteClick;
            notifyPropertyChanged(BR.onDelteClick);
        }
    }
}
