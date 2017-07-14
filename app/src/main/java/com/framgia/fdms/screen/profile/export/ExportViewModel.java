package com.framgia.fdms.screen.profile.export;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import com.framgia.fdms.R;
import com.framgia.fdms.data.model.Device;
import com.framgia.fdms.utils.navigator.Navigator;
import java.io.File;
import java.util.List;

import static com.framgia.fdms.utils.Constant.TYPE_PDF;
import static com.framgia.fdms.utils.Constant.TYPE_WORD;
import static com.framgia.fdms.utils.permission.PermissionUtil.checkWritePermission;

/**
 * Created by tuanbg on 6/14/17.
 */

public class ExportViewModel implements ExportContract.ViewModel {
    private ExportContract.Presenter mPresenter;
    private DialogFragment mFragment;
    private List<Device> mDevices;
    private Navigator mNavigator;

    public ExportViewModel(DialogFragment fragment, List<Device> devices) {
        mFragment = fragment;
        mDevices = devices;
        mNavigator = new Navigator(fragment);
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void setPresenter(ExportContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onExportPdfClick() {
        if (checkWritePermission((AppCompatActivity) mFragment.getActivity())) {
            mPresenter.exportDeviceByPdf(mDevices);
            mFragment.dismiss();
        }
    }

    @Override
    public void onExportWordClick() {
        if (checkWritePermission((AppCompatActivity) mFragment.getActivity())) {
            mPresenter.exportDeviceByDoc(mDevices);
            mFragment.dismiss();
        }
    }

    @Override
    public void onCancelClick() {
        mFragment.dismiss();
    }

    @Override
    public void onDestroy() {
        mPresenter.onDestroy();
    }

    @Override
    public void openFilePDF(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) return;
        Intent target = new Intent(Intent.ACTION_VIEW);
        target.setDataAndType(Uri.fromFile(file), TYPE_PDF);
        target.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        Intent intent = Intent.createChooser(target, mFragment.getString(R.string.title_open_with));
        mFragment.startActivity(intent);
    }

    @Override
    public String getString(int strResource) {
        return mFragment.getString(strResource);
    }

    @Override
    public String[] getStringArray(int strArrResource) {
        return mFragment.getResources().getStringArray(strArrResource);
    }

    @Override
    public Drawable getDrawable(int drawableResource) {
        return mFragment.getResources().getDrawable(drawableResource);
    }

    @Override
    public void showMessage(String mess) {
        mNavigator.showToast(mess);
    }

    @Override
    public void showMessage(int strResource) {
        mNavigator.showToast(strResource);
    }

    @Override
    public void onExportPdfSuccess(String filePath) {
        mNavigator.showToast(filePath);
        openFilePDF(filePath);
    }

    @Override
    public void onExportDocSuccess(String filePath) {
        mNavigator.showToast(filePath);
        openFileDoc(filePath);
    }

    @Override
    public void openFileDoc(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) return;
        Intent target = new Intent(Intent.ACTION_VIEW);
        target.setDataAndType(Uri.fromFile(file), TYPE_WORD);
        target.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        Intent intent = Intent.createChooser(target, mFragment.getString(R.string.title_open_with));
        mFragment.startActivity(intent);
    }
}
