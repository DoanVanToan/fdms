package com.framgia.fdms.screen.device.listdevice;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.framgia.fdms.R;
import com.framgia.fdms.data.source.CategoryRepository;
import com.framgia.fdms.data.source.DeviceRepository;
import com.framgia.fdms.data.source.DeviceReturnRepository;
import com.framgia.fdms.data.source.StatusRepository;
import com.framgia.fdms.data.source.UserRepository;
import com.framgia.fdms.data.source.api.service.FDMSServiceClient;
import com.framgia.fdms.data.source.local.UserLocalDataSource;
import com.framgia.fdms.data.source.local.sharepref.SharePreferenceImp;
import com.framgia.fdms.data.source.remote.CategoryRemoteDataSource;
import com.framgia.fdms.data.source.remote.DeviceRemoteDataSource;
import com.framgia.fdms.data.source.remote.StatusRemoteDataSource;
import com.framgia.fdms.databinding.FragmentListDeviceBinding;
import com.framgia.fdms.screen.device.DeviceViewModel;

import static com.framgia.fdms.utils.Constant.BundleConstant.BUNDLE_TAB;

/**
 * ListDevice Screen.
 */
public class ListDeviceFragment extends Fragment {
    private ListDeviceContract.ViewModel mViewModel;

    public static ListDeviceFragment newInstance(@DeviceViewModel.Tab int tabDevice) {
        ListDeviceFragment fragment = new ListDeviceFragment();
        Bundle args = new Bundle();
        args.putInt(BUNDLE_TAB, tabDevice);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new ListDeviceViewModel(this, getArguments().getInt(BUNDLE_TAB));
        ListDeviceContract.Presenter presenter = new ListDevicePresenter(mViewModel,
                new DeviceRepository(new DeviceRemoteDataSource(FDMSServiceClient.getInstance())),
                new CategoryRepository(
                        new CategoryRemoteDataSource(FDMSServiceClient.getInstance())),
                new StatusRepository(new StatusRemoteDataSource(FDMSServiceClient.getInstance())),
                new UserRepository(new UserLocalDataSource(new SharePreferenceImp(getContext()))),
                new DeviceReturnRepository());
        mViewModel.setPresenter(presenter);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {

        FragmentListDeviceBinding binding =
                DataBindingUtil.inflate(inflater, R.layout.fragment_list_device, container, false);
        binding.setViewModel((ListDeviceViewModel) mViewModel);
        mViewModel.loadData();
        return binding.getRoot();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mViewModel.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onStart() {
        super.onStart();
        mViewModel.onStart();
    }

    @Override
    public void onStop() {
        mViewModel.onStop();
        super.onStop();
    }
}
