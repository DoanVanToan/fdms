package com.framgia.fdms.screen.devicecreation;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import com.framgia.fdms.R;
import com.framgia.fdms.data.model.Device;
import com.framgia.fdms.data.source.BranchRepository;
import com.framgia.fdms.data.source.CategoryRepository;
import com.framgia.fdms.data.source.DeviceRepository;
import com.framgia.fdms.data.source.StatusRepository;
import com.framgia.fdms.data.source.api.service.FDMSServiceClient;
import com.framgia.fdms.data.source.remote.BranchRemoteDataSource;
import com.framgia.fdms.data.source.remote.CategoryRemoteDataSource;
import com.framgia.fdms.data.source.remote.DeviceRemoteDataSource;
import com.framgia.fdms.data.source.remote.StatusRemoteDataSource;
import com.framgia.fdms.databinding.ActivityCreatedeviceBinding;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;
import static com.framgia.fdms.utils.Constant.BundleConstant.BUNDLE_DEVICE;
import static com.framgia.fdms.utils.Constant.BundleConstant.BUNDLE_TYPE;

/**
 * Createdevice Screen.
 */
public class CreateDeviceActivity extends AppCompatActivity {

    private CreateDeviceContract.ViewModel mViewModel;
    private Device mDevice;
    private DeviceStatusType mDeviceStatusType;

    public static Intent getInstance(Context context, DeviceStatusType type) {
        Intent intent = new Intent(context, CreateDeviceActivity.class);
        intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
        Bundle bundle = new Bundle();
        bundle.putSerializable(BUNDLE_TYPE, type);
        intent.putExtras(bundle);
        return intent;
    }

    public static Intent getInstance(Context context, Device device, DeviceStatusType type) {
        Intent intent = new Intent(context, CreateDeviceActivity.class);
        intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
        Bundle bundle = new Bundle();
        bundle.putParcelable(BUNDLE_DEVICE, device);
        bundle.putSerializable(BUNDLE_TYPE, type);
        intent.putExtras(bundle);
        return intent;
    }

    private void getDataFromIntent() {
        Bundle bundle = getIntent().getExtras();
        if (bundle == null) return;
        mDevice = bundle.getParcelable(BUNDLE_DEVICE);
        mDeviceStatusType = (DeviceStatusType) bundle.getSerializable(BUNDLE_TYPE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getDataFromIntent();
        mViewModel = new CreateDeviceViewModel(this, mDevice, mDeviceStatusType);

        DeviceRepository deviceRepository =
                new DeviceRepository(new DeviceRemoteDataSource(FDMSServiceClient.getInstance()));
        StatusRepository statusRepository =
                new StatusRepository(new StatusRemoteDataSource(FDMSServiceClient.getInstance()));
        CategoryRepository categoryRepository = new CategoryRepository(
                new CategoryRemoteDataSource(FDMSServiceClient.getInstance()));
        CreateDeviceContract.Presenter presenter =
                new CreateDevicePresenter(mViewModel, deviceRepository, statusRepository,
                        categoryRepository, new BranchRepository(
                        new BranchRemoteDataSource(FDMSServiceClient.getInstance())));
        mViewModel.setPresenter(presenter);

        ActivityCreatedeviceBinding binding =
                DataBindingUtil.setContentView(this, R.layout.activity_createdevice);
        binding.setViewModel((CreateDeviceViewModel) mViewModel);
        setTitle(R.string.title_create_device);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        mViewModel.onActivityResult(requestCode, resultCode, data);
    }
}
