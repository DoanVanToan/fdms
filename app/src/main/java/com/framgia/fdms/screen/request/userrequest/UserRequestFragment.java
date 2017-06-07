package com.framgia.fdms.screen.request.userrequest;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.framgia.fdms.R;
import com.framgia.fdms.data.source.RequestRepository;
import com.framgia.fdms.data.source.StatusRepository;
import com.framgia.fdms.data.source.api.service.FDMSServiceClient;
import com.framgia.fdms.data.source.remote.RequestRemoteDataSource;
import com.framgia.fdms.data.source.remote.StatusRemoteDataSource;
import com.framgia.fdms.databinding.FragmentUserRequestBinding;

/**
 * RequestManager Screen.
 */
public class UserRequestFragment extends Fragment {

    private UserRequestContract.ViewModel mViewModel;

    public static UserRequestFragment newInstance() {
        return new UserRequestFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewModel = new UserRequestViewModel(getActivity(), this);

        UserRequestContract.Presenter presenter = new UserRequestPresenter(mViewModel,
                RequestRepository.getInstant(
                        new RequestRemoteDataSource(FDMSServiceClient.getInstance())),
                new StatusRepository(new StatusRemoteDataSource(FDMSServiceClient.getInstance())));
        mViewModel.setPresenter(presenter);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {

        FragmentUserRequestBinding binding =
                DataBindingUtil.inflate(inflater, R.layout.fragment_user_request, container, false);
        binding.setViewModel((UserRequestViewModel) mViewModel);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mViewModel.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onStart() {
        super.onStart();
        mViewModel.onStart();
    }

    @Override
    public void onStop() {
        mViewModel.onStop();
        super.onStop();
    }
}
