package com.framgia.fdms.screen.producer.vendor;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.framgia.fdms.R;
import com.framgia.fdms.databinding.FragmentVendorBinding;

/**
 * Vendor Screen.
 */
public class VendorFragment extends Fragment {
    private VendorContract.ViewModel mViewModel;

    public static VendorFragment newInstance() {
        return new VendorFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new VendorViewModel(getActivity());
        VendorContract.Presenter presenter = new VendorPresenter(mViewModel);
        mViewModel.setPresenter(presenter);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        FragmentVendorBinding binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_vendor, container, false);
        binding.setViewModel((VendorViewModel) mViewModel);
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
