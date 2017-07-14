package com.framgia.fdms.screen.scanner;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.framgia.fdms.R;
import com.framgia.fdms.databinding.ActivityScannerBinding;

/**
 * Scanner Screen.
 */
public class ScannerActivity extends AppCompatActivity {

    private ScannerContract.ViewModel mViewModel;

    public static Intent newIntent(Context context) {
        return new Intent(context, ScannerActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityScannerBinding binding =
                DataBindingUtil.setContentView(this, R.layout.activity_scanner);
        mViewModel = new ScannerViewModel(this);

        ScannerContract.Presenter presenter = new ScannerPresenter(mViewModel);
        mViewModel.setPresenter(presenter);

        binding.setViewModel((ScannerViewModel) mViewModel);
        mViewModel.bindView(binding.frameScanner);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mViewModel.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mViewModel != null) mViewModel.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mViewModel != null) mViewModel.onPause();
    }

    @Override
    protected void onStop() {
        mViewModel.onStop();
        super.onStop();
    }
}
