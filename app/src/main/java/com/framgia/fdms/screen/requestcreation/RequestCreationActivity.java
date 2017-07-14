package com.framgia.fdms.screen.requestcreation;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import com.framgia.fdms.R;
import com.framgia.fdms.data.source.CategoryRepository;
import com.framgia.fdms.data.source.RequestRepository;
import com.framgia.fdms.data.source.StatusRepository;
import com.framgia.fdms.data.source.api.service.FDMSServiceClient;
import com.framgia.fdms.data.source.remote.CategoryRemoteDataSource;
import com.framgia.fdms.data.source.remote.RequestRemoteDataSource;
import com.framgia.fdms.data.source.remote.StatusRemoteDataSource;
import com.framgia.fdms.databinding.ActivityRequestCreationBinding;

/**
 * Requestcreation Screen.
 */
public class RequestCreationActivity extends AppCompatActivity {

    private RequestCreationContract.ViewModel mViewModel;

    public static Intent getInstance(Context context) {
        return new Intent(context, RequestCreationActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewModel = new RequestCreationViewModel(this);

        RequestCreationContract.Presenter presenter = new RequestCreationPresenter(mViewModel,
                new StatusRepository(new StatusRemoteDataSource(FDMSServiceClient.getInstance())),
                new CategoryRepository(
                        new CategoryRemoteDataSource(FDMSServiceClient.getInstance())),
                new RequestRepository(
                        new RequestRemoteDataSource(FDMSServiceClient.getInstance())));
        mViewModel.setPresenter(presenter);

        ActivityRequestCreationBinding binding =
                DataBindingUtil.setContentView(this, R.layout.activity_request_creation);
        binding.setViewModel((RequestCreationViewModel) mViewModel);
        setTitle(getString(R.string.title_create_request));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (mViewModel != null) mViewModel.onActivityResult(requestCode, resultCode, data);
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
