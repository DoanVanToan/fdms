package com.framgia.fdms.screen.notification;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import com.framgia.fdms.R;
import com.framgia.fdms.databinding.ActivityNotificationBinding;

/**
 * Notification Screen.
 */
public class NotificationActivity extends AppCompatActivity {

    private NotificationContract.ViewModel mViewModel;

    public static Intent getInstances(Context context) {
        return new Intent(context, NotificationActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewModel = new NotificationViewModel(this);

        NotificationContract.Presenter presenter = new NotificationPresenter(mViewModel);
        mViewModel.setPresenter(presenter);

        ActivityNotificationBinding binding =
                DataBindingUtil.setContentView(this, R.layout.activity_notification);
        binding.setViewModel((NotificationViewModel) mViewModel);
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
        return super.onOptionsItemSelected(item);
    }
}
