package com.framgia.fdms.screen.historydetail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.framgia.fdms.databinding.FragmentHistoryDetailBinding;

/**
 * HistoryDetail Screen.
 */
public class DeviceDetailHistoryFragment extends Fragment {

    private DeviceDetailHistoryContract.ViewModel mViewModel;

    public static DeviceDetailHistoryFragment newInstance() {
        return new DeviceDetailHistoryFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new DeviceDetailHistoryViewModel();

        DeviceDetailHistoryContract.Presenter presenter = new DeviceDetailHistoryPresenter(mViewModel);
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