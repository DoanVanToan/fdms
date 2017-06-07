package com.framgia.fdms.screen.authenication.register;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.framgia.fdms.R;
import com.framgia.fdms.data.source.UserRepository;
import com.framgia.fdms.data.source.api.service.FDMSServiceClient;
import com.framgia.fdms.data.source.remote.UserRemoteDataSource;
import com.framgia.fdms.databinding.ActivityRegisterBinding;

/**
 * Register Screen.
 */
public class RegisterActivity extends AppCompatActivity {

    private RegisterContract.ViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new RegisterViewModel(this);

        setTitle(getString(R.string.title_register));
        UserRepository repository =
                new UserRepository(new UserRemoteDataSource(FDMSServiceClient.getInstance()));
        RegisterContract.Presenter presenter = new RegisterPresenter(mViewModel, repository);
        mViewModel.setPresenter(presenter);
        ActivityRegisterBinding binding =
                DataBindingUtil.setContentView(this, R.layout.activity_register);
        binding.setViewModel((RegisterViewModel) mViewModel);
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
