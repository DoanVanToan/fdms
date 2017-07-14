package com.framgia.fdms.screen.producer.marker;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.framgia.fdms.R;
import com.framgia.fdms.data.source.MakerRepository;
import com.framgia.fdms.data.source.api.service.FDMSServiceClient;
import com.framgia.fdms.data.source.remote.MakerRemoteDataSource;
import com.framgia.fdms.databinding.FragmentMarkerBinding;
import com.framgia.fdms.screen.device.DeviceViewModel;

import static com.framgia.fdms.utils.Constant.BundleConstant.BUNDLE_TAB;

/**
 * MarkerFragment Screen.
 */
public class MarkerFragment extends Fragment {
    private MarkerContract.ViewModel mViewModel;

    public static MarkerFragment newInstance(@DeviceViewModel.Tab int tabDevice) {
        MarkerFragment fragment = new MarkerFragment();
        Bundle args = new Bundle();
        args.putInt(BUNDLE_TAB, tabDevice);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new MarkerViewModel(getActivity());
        MarkerContract.Presenter presenter = new MarkerPresenter(mViewModel, MakerRepository
            .getInstant(new MakerRemoteDataSource(FDMSServiceClient.getInstance())));
        mViewModel.setPresenter(presenter);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        FragmentMarkerBinding binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_marker, container, false);
        binding.setViewModel((MarkerViewModel) mViewModel);
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
