package com.framgia.fdms.screen.requestdetail;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import com.framgia.fdms.R;
import com.framgia.fdms.data.model.Request;
import com.framgia.fdms.databinding.ActivityRequestDetailBinding;
import com.github.clans.fab.FloatingActionMenu;

import static com.framgia.fdms.utils.Constant.BundleRequest.BUND_REQUEST;

/**
 * Created by tuanbg on 5/24/17.
 */

public class RequestDetailActivity extends AppCompatActivity {
    private ActivityRequestDetailBinding mBinding;
    private Toolbar mToolbar;
    private RequestDetailContract.ViewModel mViewModel;
    private FloatingActionMenu mFloatingActionsMenu;
    private Request mRequest;

    public static Intent newInstance(Context context, Request request) {
        Intent intent = new Intent(context, RequestDetailActivity.class);
        intent.putExtra(BUND_REQUEST, request);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getRequestFromIntent();
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_request_detail);
        mViewModel = new RequestDetailViewModel(this, mRequest.getDevices(),
                mRequest.getRequestActionList(), mRequest.getRequestStatus(), mRequest);
        mBinding.setViewModel((RequestDetailViewModel) mViewModel);
        RequestDetailContract.Presenter presenter = new RequestDetailPresenter(mViewModel);
        mBinding.setRequest(mRequest);
        mViewModel.setPresenter(presenter);
        mFloatingActionsMenu = mBinding.floatActionMenu;
        setFloatingActionButton();
        initToolbar();
    }

    public void getRequestFromIntent() {
        mRequest = (Request) getIntent().getSerializableExtra(BUND_REQUEST);
    }

    public void initToolbar() {
        mToolbar = mBinding.toolbar;
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() == null) return;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mViewModel.onStart();
    }

    @Override
    public void onBackPressed() {
        if (mViewModel.onBackPressed()) {
            super.onBackPressed();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mViewModel.onActivityResult(requestCode, resultCode, data);
    }

    public void setFloatingActionButton() {
        mViewModel.initFloatActionButton(mFloatingActionsMenu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        mViewModel.onOptionsItemSelected(item);
        return super.onOptionsItemSelected(item);
    }
}
