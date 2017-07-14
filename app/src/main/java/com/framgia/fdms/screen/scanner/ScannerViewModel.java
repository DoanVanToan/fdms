package com.framgia.fdms.screen.scanner;

import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;
import me.dm7.barcodescanner.zbar.Result;
import me.dm7.barcodescanner.zbar.ZBarScannerView;

import static android.app.Activity.RESULT_OK;
import static com.framgia.fdms.utils.Constant.BundleConstant.BUNDLE_CONTENT;

/**
 * Exposes the data to be used in the Scanner screen.
 */

public class ScannerViewModel implements ScannerContract.ViewModel, ZBarScannerView.ResultHandler {

    private final ScannerActivity mActivity;
    private ScannerContract.Presenter mPresenter;
    private ZBarScannerView mScannerView;

    public ScannerViewModel(ScannerActivity activity) {
        mActivity = activity;
    }

    @Override
    public void bindView(FrameLayout view) {
        mScannerView = new ZBarScannerView(mActivity);
        view.addView(mScannerView);
    }

    @Override
    public void onStart() {
        mPresenter.onStart();
    }

    @Override
    public void onResume() {
        if (mScannerView != null) {
            mScannerView.setResultHandler(this);
            mScannerView.startCamera();
        }
    }

    @Override
    public void handleResult(Result result) {
        mScannerView.stopCamera();
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putString(BUNDLE_CONTENT, result.getContents());
        intent.putExtras(bundle);
        mActivity.setResult(RESULT_OK, intent);
        mActivity.finish();
    }

    @Override
    public void onPause() {
        if (mScannerView != null) {
            mScannerView.stopCamera();
        }
    }

    @Override
    public void onStop() {
        mPresenter.onStop();
    }

    @Override
    public void setPresenter(ScannerContract.Presenter presenter) {
        mPresenter = presenter;
    }
}
