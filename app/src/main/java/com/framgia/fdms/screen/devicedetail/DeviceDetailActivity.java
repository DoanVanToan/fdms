package com.framgia.fdms.screen.devicedetail;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.framgia.fdms.R;
import com.framgia.fdms.databinding.ActivityDeviceDetailBinding;

/**
 * Devicedetail Screen.
 */
public class DeviceDetailActivity extends AppCompatActivity {

    private DeviceDetailContract.ViewModel mViewModel;
    private static final String EXTRA_DEVICE_ID = "EXTRA_DEVICE_ID";

    public static Intent getInstance(Context context, int id) {
        return new Intent(context, DeviceDetailActivity.class).putExtra(EXTRA_DEVICE_ID, id)
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewModel = new DeviceDetailViewModel(this);

        DeviceDetailContract.Presenter presenter = new DeviceDetailPresenter(mViewModel);
        mViewModel.setPresenter(presenter);

        ActivityDeviceDetailBinding binding =
                DataBindingUtil.setContentView(this, R.layout.activity_device_detail);
        binding.setViewModel((DeviceDetailViewModel) mViewModel);
        setTitle(getString(R.string.title_device_detail));
    }

    @Override
    protected void onStart() {
        super.onStart();
        mViewModel.onStart();
    }

    @Override
    protected void onStop() {
        mViewModel.onStop();
        super.onStop();
    }
}