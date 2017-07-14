package com.framgia.fdms.screen.request.userrequest;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.framgia.fdms.BR;
import com.framgia.fdms.BaseRecyclerViewAdapter;
import com.framgia.fdms.R;
import com.framgia.fdms.data.model.Request;
import com.framgia.fdms.data.model.User;
import com.framgia.fdms.databinding.ItemRequestManagerAdapterBinding;
import com.framgia.fdms.screen.request.OnRequestClickListenner;
import java.util.ArrayList;
import java.util.List;

import static com.framgia.fdms.utils.Constant.DeviceStatus.WAITING_DONE;

/**
 * Created by beepi on 09/05/2017.
 */

public class UserRequestAdapter
        extends BaseRecyclerViewAdapter<Request, UserRequestAdapter.ViewHolder> {
    private List<Request> mRequests = new ArrayList<>();
    private OnRequestClickListenner mListenner;
    private static final int FIRST_ITEM = 0;
    private User mUser;

    public UserRequestAdapter(Context context, List<Request> requests,
            OnRequestClickListenner listenner, User user) {
        super(context);
        mRequests = requests;
        mListenner = listenner;
        mUser = user;
    }
    @Override
    public void onUpdatePage(List<Request> datas) {
        if (datas != null) {
            mRequests.addAll(datas);
            notifyDataSetChanged();
        }
    }

    public void updateItem(Request request) {
        for (int i = 0; i < mRequests.size(); i++) {
            if (request.getId() == mRequests.get(i).getId()) {
                mRequests.set(i, request);
                notifyItemChanged(i);
                break;
            }
        }
    }

    public void updateUser(User user) {
        if (user == null) return;
        mUser = user;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemRequestManagerAdapterBinding binding =
                DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                        R.layout.item_request_manager_adapter, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(new RequestModel(mRequests.get(position)));
    }

    @Override
    public int getItemCount() {
        return mRequests == null ? 0 : mRequests.size();
    }

    public void setRequests(List<Request> requests) {
        mRequests = requests;
    }

    public void clear() {
        if (mRequests != null) mRequests.clear();
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ItemRequestManagerAdapterBinding mBinding;

        public ViewHolder(ItemRequestManagerAdapterBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        public void bind(RequestModel requestModel) {
            mBinding.setRequestModel(requestModel);
            mBinding.setListenner(mListenner);
            mBinding.executePendingBindings();
        }
    }

    public class RequestModel extends BaseObservable {
        private Request mRequest;
        private boolean mIsShowAddDevice;

        public RequestModel(Request request) {
            mRequest = request;
            mIsShowAddDevice =
                    mUser.isBoStaff() && mRequest.getRequestStatus().equals(WAITING_DONE);
        }

        @Bindable
        public Request getRequest() {
            return mRequest;
        }

        public void setRequest(Request request) {
            mRequest = request;
            notifyPropertyChanged(BR.request);
        }

        @Bindable
        public boolean isShowAddDevice() {
            return mIsShowAddDevice;
        }

        public void setShowAddDevice(boolean showAddDevice) {
            mIsShowAddDevice = showAddDevice;
            notifyPropertyChanged(BR.showAddDevice);
        }
    }
}
