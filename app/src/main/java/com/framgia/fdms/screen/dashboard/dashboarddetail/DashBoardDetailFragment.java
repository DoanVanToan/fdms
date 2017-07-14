package com.framgia.fdms.screen.dashboard.dashboarddetail;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.framgia.fdms.R;
import com.framgia.fdms.data.source.DeviceRepository;
import com.framgia.fdms.data.source.RequestRepository;
import com.framgia.fdms.data.source.UserRepository;
import com.framgia.fdms.data.source.api.service.FDMSServiceClient;
import com.framgia.fdms.data.source.local.UserLocalDataSource;
import com.framgia.fdms.data.source.local.sharepref.SharePreferenceImp;
import com.framgia.fdms.data.source.remote.DeviceRemoteDataSource;
import com.framgia.fdms.data.source.remote.RequestRemoteDataSource;
import com.framgia.fdms.databinding.FragmentDashBoardDetailBinding;

/**
 * Scanner Screen.
 */
public class DashBoardDetailFragment extends Fragment {
    public static final String EXTRA_DASHBORAD_TYPE = "EXTRA_DASHBORAD_TYPE";
    public static final int DEVICE_DASHBOARD = 0;
    public static final int REQUEST_DASHBOARD = 1;

    private DashBoardDetailContract.ViewModel mViewModel;

    public static DashBoardDetailFragment newInstance(int dashboradType) {
        DashBoardDetailFragment boardFragment = new DashBoardDetailFragment();
        Bundle args = new Bundle();
        args.putInt(EXTRA_DASHBORAD_TYPE, dashboradType);
        boardFragment.setArguments(args);
        return boardFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int dashboradType = DEVICE_DASHBOARD;
        if (getArguments() != null) {
            dashboradType = getArguments().getInt(EXTRA_DASHBORAD_TYPE);
        }

        mViewModel = new DashBoardDetailViewModel(this, dashboradType);
        DashBoardDetailContract.Presenter presenter = new DashBoardDetailPresenter(mViewModel,
                new DeviceRepository(new DeviceRemoteDataSource(FDMSServiceClient.getInstance())),
                RequestRepository.getInstant(
                        new RequestRemoteDataSource(FDMSServiceClient.getInstance())),
                dashboradType,
                new UserRepository(new UserLocalDataSource(new SharePreferenceImp(getContext()))));
        mViewModel.setPresenter(presenter);
        mViewModel.getData();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {

        FragmentDashBoardDetailBinding binding =
                DataBindingUtil.inflate(inflater, R.layout.fragment_dash_board_detail, container,
                        false);
        binding.setViewModel((DashBoardDetailViewModel) mViewModel);
        return binding.getRoot();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mViewModel.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onStart() {
        mViewModel.onStart();
        super.onStart();
    }

    @Override
    public void onStop() {
        mViewModel.onStop();
        super.onStop();
    }
}
