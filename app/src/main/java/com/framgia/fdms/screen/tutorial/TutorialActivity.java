package com.framgia.fdms.screen.tutorial;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.framgia.fdms.R;
import com.framgia.fdms.data.source.local.sharepref.SharePreferenceImp;
import com.framgia.fdms.databinding.ActivityTutorialBinding;

/**
 * Tutorial Screen.
 */
public class TutorialActivity extends AppCompatActivity {

    private TutorialContract.ViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewModel = new TutorialViewModel(this);

        TutorialContract.Presenter presenter =
                new TutorialPresenter(mViewModel, new SharePreferenceImp(getApplicationContext()));
        mViewModel.setPresenter(presenter);

        ActivityTutorialBinding binding =
                DataBindingUtil.setContentView(this, R.layout.activity_tutorial);
        binding.setViewModel((TutorialViewModel) mViewModel);
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
