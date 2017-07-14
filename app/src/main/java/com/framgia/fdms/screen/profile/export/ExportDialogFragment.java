package com.framgia.fdms.screen.profile.export;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import com.framgia.fdms.data.model.Device;
import com.framgia.fdms.data.model.User;
import com.framgia.fdms.databinding.DialogExportBinding;
import java.util.ArrayList;
import java.util.List;

import static com.framgia.fdms.utils.Constant.BundleConstant.BUNDLE_DEVICE;
import static com.framgia.fdms.utils.Constant.BundleConstant.BUNDLE_USER;

/**
 * Created by tuanbg on 6/14/17.
 */

public class ExportDialogFragment extends DialogFragment {
    private DialogExportBinding mBinding;
    private ExportViewModel mViewModel;
    private User mUser;
    private List<Device> mDevices;

    public static ExportDialogFragment newInstance(User user, List<Device> list) {
        Bundle args = new Bundle();
        args.putParcelable(BUNDLE_USER, user);
        args.putParcelableArrayList(BUNDLE_DEVICE, (ArrayList<? extends Parcelable>) list);
        ExportDialogFragment fragment = new ExportDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        mBinding = DialogExportBinding.inflate(inflater, container, false);
        getDataFromIntent();
        mViewModel = new ExportViewModel(this, mDevices);
        ExportContract.Presenter presenter = new ExportPresenter(mUser, mViewModel);
        mViewModel.setPresenter(presenter);
        mBinding.setViewModel(mViewModel);
        return mBinding.getRoot();
    }

    public void getDataFromIntent() {
        Bundle bundle = getArguments();
        if (bundle == null) return;
        mUser = bundle.getParcelable(BUNDLE_USER);
        mDevices = bundle.getParcelableArrayList(BUNDLE_DEVICE);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mViewModel.onDestroy();
    }
}
