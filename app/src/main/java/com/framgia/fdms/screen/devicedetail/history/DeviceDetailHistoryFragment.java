package com.framgia.fdms.screen.devicedetail.history;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.framgia.fdms.data.source.DeviceRepository;
import com.framgia.fdms.data.source.api.service.FDMSServiceClient;
import com.framgia.fdms.data.source.remote.DeviceRemoteDataSource;
import com.framgia.fdms.databinding.FragmentHistoryDetailBinding;

import static com.framgia.fdms.utils.Constant.BundleConstant.EXTRA_DEVICE_ID;

/**
 * DeviceHistoryDetail Screen.
 */
public class DeviceDetailHistoryFragment extends Fragment {

    private DeviceDetailHistoryContract.ViewModel mViewModel;

    public static DeviceDetailHistoryFragment newInstance() {
        return new DeviceDetailHistoryFragment();
    }

    public static DeviceDetailHistoryFragment newInstance(int deviceId) {
        DeviceDetailHistoryFragment fragment = new DeviceDetailHistoryFragment();
        Bundle args = new Bundle();
        args.putInt(EXTRA_DEVICE_ID, deviceId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new DeviceDetailHistoryViewModel();

        DeviceRepository repository =
                new DeviceRepository(new DeviceRemoteDataSource(FDMSServiceClient.getInstance()));
        DeviceDetailHistoryContract.Presenter presenter =
                new DeviceDetailHistoryPresenter(mViewModel, repository,
                        getArguments().getInt(EXTRA_DEVICE_ID));
        mViewModel.setPresenter(presenter);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {

        FragmentHistoryDetailBinding binding =
                FragmentHistoryDetailBinding.inflate(inflater, container, false);
        binding.setViewModel((DeviceDetailHistoryViewModel) mViewModel);
        return binding.getRoot();
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
