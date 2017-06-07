package com.framgia.fdms.screen.main;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.support.annotation.IntDef;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import com.framgia.fdms.BR;
import com.framgia.fdms.R;
import com.framgia.fdms.data.model.Device;
import com.framgia.fdms.screen.devicedetail.DeviceDetailActivity;
import com.framgia.fdms.screen.scanner.ScannerActivity;
import com.framgia.fdms.utils.permission.PermissionUtil;

import static android.app.Activity.RESULT_OK;
import static com.framgia.fdms.screen.main.MainViewModel.Tab.TAB_DASH_BOARD;
import static com.framgia.fdms.screen.main.MainViewModel.Tab.TAB_DEVICE_MANAGER;
import static com.framgia.fdms.screen.main.MainViewModel.Tab.TAB_REQUEST_MANAGER;
import static com.framgia.fdms.utils.Constant.BundleConstant.BUNDLE_CONTENT;
import static com.framgia.fdms.utils.Constant.RequestConstant.REQUEST_SCANNER;
import static com.framgia.fdms.utils.permission.PermissionUtil.MY_PERMISSIONS_REQUEST_CAMERA;

/**
 * Exposes the data to be used in the Newmain screen.
 */
public class MainViewModel extends BaseObservable implements MainContract.ViewModel {
    private MainContract.Presenter mPresenter;
    private ViewPagerAdapter mPagerAdapter;
    private int mTab = TAB_DASH_BOARD;
    private AppCompatActivity mActivity;

    public MainViewModel(ViewPagerAdapter adapter, AppCompatActivity activity) {
        mPagerAdapter = adapter;
        mActivity = activity;
    }

    @Override
    public void onStart() {
        mPresenter.onStart();
    }

    @Override
    public void onStop() {
        mPresenter.onStop();
    }

    @Override
    public void setPresenter(MainContract.Presenter presenter) {
        mPresenter = presenter;
    }

    public ViewPagerAdapter getPagerAdapter() {
        return mPagerAdapter;
    }

    @Bindable
    public int getTab() {
        return mTab;
    }

    public void setTab(int tab) {
        mTab = tab;
        notifyPropertyChanged(BR.tab);
    }

    public void onDirectChildTab(@Tab int tab, ViewPager viewPager) {
        if (mPagerAdapter == null) return;
        viewPager.setCurrentItem(tab);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode != REQUEST_SCANNER
                || resultCode != RESULT_OK
                || data == null
                || data.getExtras() == null) {
            return;
        }
        getResult(data.getExtras().getString(BUNDLE_CONTENT));
    }

    public void onStartScannerQrCode() {
        if (PermissionUtil.checkCameraPermission(mActivity)) {
            startScannerActivity();
        }
    }

    @Override
    public void onGetDecodeSuccess(Device device) {
        mActivity.startActivity(DeviceDetailActivity.getInstance(mActivity, device.getId()));
    }

    private void startScannerActivity() {
        mActivity.startActivityForResult(ScannerActivity.newIntent(mActivity), REQUEST_SCANNER);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
            int[] grantResults) {
        if (requestCode == MY_PERMISSIONS_REQUEST_CAMERA
                && grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            startScannerActivity();
        } else {
            Snackbar.make(mActivity.findViewById(android.R.id.content),
                    R.string.msg_denied_read_camera, Snackbar.LENGTH_LONG).show();
        }
    }

    @Override
    public void onGetDeviceError(String error) {
        Snackbar.make(mActivity.findViewById(android.R.id.content), error, Snackbar.LENGTH_LONG)
                .show();
    }

    @Override
    public void getResult(String resultQrCode) {
        if (mPresenter == null) return;
        mPresenter.getDevice(resultQrCode);
    }

    @IntDef({ TAB_DASH_BOARD, TAB_REQUEST_MANAGER, TAB_DEVICE_MANAGER, Tab.TAB_PROFILE })
    public @interface Tab {
        int TAB_DASH_BOARD = 0;
        int TAB_REQUEST_MANAGER = 1;
        int TAB_DEVICE_MANAGER = 2;
        int TAB_PROFILE = 3;
    }
}
