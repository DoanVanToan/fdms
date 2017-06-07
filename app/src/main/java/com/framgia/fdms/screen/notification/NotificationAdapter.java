package com.framgia.fdms.screen.notification;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.framgia.fdms.data.model.Notification;
import com.framgia.fdms.databinding.ItemNotificationBinding;
import java.util.List;

/**
 * Created by Nhahv0902 on 6/1/2017.
 * <></>
 */

public class NotificationAdapter
        extends RecyclerView.Adapter<NotificationAdapter.NotificationHolder> {

    private final NotificationContract.ViewModel mViewModel;
    private List<Notification> mNotifications;
    private LayoutInflater mInflater;

    public NotificationAdapter(NotificationContract.ViewModel viewModel,
            List<Notification> notifications) {
        mViewModel = viewModel;
        mNotifications = notifications;
    }

    @Override
    public NotificationHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mInflater == null) {
            mInflater = LayoutInflater.from(parent.getContext());
        }
        ItemNotificationBinding binding = ItemNotificationBinding.inflate(mInflater, parent, false);
        binding.setViewModel(mViewModel);
        return new NotificationHolder(binding);
    }

    @Override
    public void onBindViewHolder(NotificationHolder holder, int position) {
        Notification notification = mNotifications.get(position);
        if (notification != null) {
            holder.bind(notification, position);
        }
    }

    @Override
    public int getItemCount() {
        return mNotifications != null ? mNotifications.size() : 0;
    }

    public class NotificationHolder extends RecyclerView.ViewHolder {
        private ItemNotificationBinding mBinding;

        public NotificationHolder(ItemNotificationBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        public void bind(Notification notification, int position) {
            mBinding.setNotification(notification);
            mBinding.setPosition(position);
            mBinding.executePendingBindings();
        }
    }
}
