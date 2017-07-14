package com.framgia.fdms.screen.returndevice;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import com.framgia.fdms.R;
import com.framgia.fdms.data.model.Device;
import com.framgia.fdms.data.source.DeviceRepository;
import com.framgia.fdms.data.source.DeviceReturnRepository;
import com.framgia.fdms.data.source.StatusRepository;
import com.framgia.fdms.data.source.api.service.FDMSServiceClient;
import com.framgia.fdms.data.source.remote.DeviceRemoteDataSource;
import com.framgia.fdms.data.source.remote.StatusRemoteDataSource;
import com.framgia.fdms.databinding.ActivityReturnDeviceBinding;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

/**
 * ReturnDevice Screen.
 */
public class ReturnDeviceActivity extends AppCompatActivity {

    private ReturnDeviceContract.ViewModel mViewModel;

    private ActivityReturnDeviceBinding mBinding;

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, ReturnDeviceActivity.class);
        intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new ReturnDeviceViewModel(this);

        ReturnDeviceContract.Presenter presenter = new ReturnDevicePresenter(mViewModel,
                new StatusRepository(new StatusRemoteDataSource(FDMSServiceClient.getInstance())),
                new DeviceReturnRepository(),
                new DeviceRepository(new DeviceRemoteDataSource(FDMSServiceClient.getInstance())));
        mViewModel.setPresenter(presenter);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_return_device);
        mBinding.setViewModel((ReturnDeviceViewModel) mViewModel);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (mViewModel != null) mViewModel.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_scaner_of_return_device, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.action_scanner:
                mViewModel.onStartScannerDevice();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
            @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (mViewModel != null) {
            mViewModel.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    public void show(String name, Device device) {
        Snackbar.make(mBinding.coordinatorLayout,
                getString(R.string.msg_not_device_in_device_brorows, name,
                        device.getUser().getName()), Snackbar.LENGTH_LONG).show();
    }
}
