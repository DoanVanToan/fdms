package com.framgia.fdms.screen.authenication.forgotpassword;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.framgia.fdms.R;
import com.framgia.fdms.databinding.ActivityForgotpasswordBinding;

/**
 * Forgetpassword Screen.
 */
public class ForgotpasswordActivity extends AppCompatActivity {

    private ForgetpasswordContract.ViewModel mViewModel;

    public static Intent getForgetPasswordIntent(Context context) {
        return new Intent(context, ForgotpasswordActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewModel = new ForgotpasswordViewModel(this);
        setTitle(getString(R.string.title_forgot_the_password));

        ForgetpasswordContract.Presenter presenter = new ForgetpasswordPresenter(mViewModel);
        mViewModel.setPresenter(presenter);

        ActivityForgotpasswordBinding binding =
                DataBindingUtil.setContentView(this, R.layout.activity_forgotpassword);
        binding.setViewModel((ForgotpasswordViewModel) mViewModel);
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
