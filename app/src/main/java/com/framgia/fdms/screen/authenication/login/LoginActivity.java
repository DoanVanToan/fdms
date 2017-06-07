package com.framgia.fdms.screen.authenication.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import com.framgia.fdms.R;
import com.framgia.fdms.data.source.UserRepository;
import com.framgia.fdms.data.source.api.service.FDMSServiceClient;
import com.framgia.fdms.data.source.local.sharepref.SharePreferenceImp;
import com.framgia.fdms.data.source.remote.UserRemoteDataSource;
import com.framgia.fdms.databinding.ActivityLoginBinding;

/**
 * Login Screen.
 */
public class LoginActivity extends Activity {

    private LoginContract.ViewModel mViewModel;

    public static Intent getInstance(Context context) {
        return new Intent(context, LoginActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new LoginViewModel(this);
        UserRepository repository =
                new UserRepository(new UserRemoteDataSource(FDMSServiceClient.getInstance()));
        LoginContract.Presenter presenter = new LoginPresenter(mViewModel, repository,
                new SharePreferenceImp(getApplicationContext()));
        mViewModel.setPresenter(presenter);
        ActivityLoginBinding binding =
                DataBindingUtil.setContentView(this, R.layout.activity_login);
        binding.setViewModel((LoginViewModel) mViewModel);
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
