package com.framgia.fdms.screen.profile.chooseexport;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import com.framgia.fdms.R;
import com.framgia.fdms.data.model.User;
import com.framgia.fdms.data.source.DeviceReturnRepository;
import com.framgia.fdms.databinding.ActivityChooseExportBinding;

import static com.framgia.fdms.utils.Constant.BundleConstant.BUNDLE_USER;

/**
 * Created by tuanbg on 6/15/17.
 */

public class ChooseExportActivity extends AppCompatActivity {
    private ChooseExportViewModel mViewModel;
    private User mUser;

    public static Intent newInstance(Context context, User user) {
        Intent intent = new Intent(context, ChooseExportActivity.class);
        intent.putExtra(BUNDLE_USER, user);
        return intent;
    }

    public void getDataFromIntent() {
        Bundle bundle = getIntent().getExtras();
        if (bundle == null) return;
        mUser = bundle.getParcelable(BUNDLE_USER);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityChooseExportBinding binding =
                DataBindingUtil.setContentView(this, R.layout.activity_choose_export);
        getDataFromIntent();
        mViewModel = new ChooseExportViewModel(this, mUser);
        ChooseExportContract.Presenter presenter =
                new ChooseExportPresenter(mViewModel, new DeviceReturnRepository());
        mViewModel.setPresenter(presenter);
        binding.setViewModel(mViewModel);
        mViewModel.initToolbar(binding.toolbar);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mViewModel.onStart();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.item_using:
                // TODO: 6/15/17 getList Using
                return true;
            case R.id.item_used:
                // TODO: 6/15/17 getList Used
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
