package com.framgia.fdms.screen.devicedetail;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import com.framgia.fdms.R;
import com.framgia.fdms.data.model.Device;
import com.framgia.fdms.data.source.DeviceRepository;
import com.framgia.fdms.data.source.api.service.FDMSServiceClient;
import com.framgia.fdms.data.source.remote.DeviceRemoteDataSource;
import com.framgia.fdms.databinding.ActivityDeviceDetailBinding;

import static com.framgia.fdms.FDMSApplication.sUpdatedDevice;

/**
 * Devicedetail Screen.
 */
public class DeviceDetailActivity extends AppCompatActivity {

    private DeviceDetailContract.ViewModel mViewModel;
    public static Intent getInstance(Context context, Device device) {
        sUpdatedDevice = device;
        return new Intent(context, DeviceDetailActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Device device = sUpdatedDevice;
        mViewModel = new DeviceDetailViewModel(this, device);

        DeviceDetailContract.Presenter presenter = new DeviceDetailPresenter(mViewModel,
                new DeviceRepository(new DeviceRemoteDataSource(FDMSServiceClient.getInstance())),
                device);
        mViewModel.setPresenter(presenter);

        ActivityDeviceDetailBinding binding =
                DataBindingUtil.setContentView(this, R.layout.activity_device_detail);
        binding.setViewModel((DeviceDetailViewModel) mViewModel);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) onBackPressed();
        return super.onOptionsItemSelected(item);
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
