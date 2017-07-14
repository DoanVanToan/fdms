package com.framgia.fdms.screen.assignment;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import com.framgia.fdms.R;
import com.framgia.fdms.data.source.RequestRepository;
import com.framgia.fdms.data.source.api.service.FDMSServiceClient;
import com.framgia.fdms.data.source.remote.RequestRemoteDataSource;
import com.framgia.fdms.databinding.ActivityAssignmentBinding;
import com.framgia.fdms.utils.Utils;

import static com.framgia.fdms.utils.Utils.hideSoftKeyboard;

/**
 * Assignment Screen.
 */
public class AssignmentActivity extends AppCompatActivity {

    private AssignmentContract.ViewModel mViewModel;
    private static final String EXTRA_REQUEST_ID = "EXTRA_REQUEST_ID";

    public static Intent getInstance(Context context, int requestId) {
        Intent intent =
                new Intent(context, AssignmentActivity.class).putExtra(EXTRA_REQUEST_ID, requestId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int deviceId = getIntent().getIntExtra(EXTRA_REQUEST_ID, 0);

        mViewModel = new AssignmentViewModel(this);

        AssignmentContract.Presenter presenter = new AssignmentPresenter(mViewModel, deviceId,
                new RequestRepository(
                        new RequestRemoteDataSource(FDMSServiceClient.getInstance())));
        mViewModel.setPresenter(presenter);

        ActivityAssignmentBinding binding =
                DataBindingUtil.setContentView(this, R.layout.activity_assignment);
        binding.setViewModel((AssignmentViewModel) mViewModel);
        setTitle(getString(R.string.title_assignment));
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
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) onBackPressed();
        hideSoftKeyboard(this);
        return super.onOptionsItemSelected(item);
    }
}
