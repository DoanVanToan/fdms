package com.framgia.fdms.screen.selection;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import com.framgia.fdms.R;
import com.framgia.fdms.data.model.Category;
import com.framgia.fdms.data.model.Status;
import com.framgia.fdms.databinding.ActivityStatusSelectionBinding;
import java.util.ArrayList;
import java.util.List;

import static com.framgia.fdms.utils.Constant.BundleConstant.BUNDLE_CATEGORIES;
import static com.framgia.fdms.utils.Constant.BundleConstant.BUNDLE_STATUSES;
import static com.framgia.fdms.utils.Constant.BundleConstant.BUNDLE_TYPE;

/**
 * StatusSelection Screen.
 */
public class StatusSelectionActivity extends AppCompatActivity
        implements SearchView.OnQueryTextListener {

    private StatusSelectionContract.ViewModel mViewModel;
    private List<Category> mCategories;
    private List<Status> mStatuses;
    private StatusSelectionType mSelectionType;

    public static Intent getInstance(Context context, List<Category> categories,
            List<Status> statuses, StatusSelectionType type) {
        Intent intent = new Intent(context, StatusSelectionActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(BUNDLE_CATEGORIES, (ArrayList<Category>) categories);
        bundle.putParcelableArrayList(BUNDLE_STATUSES, (ArrayList<Status>) statuses);
        bundle.putSerializable(BUNDLE_TYPE, type);
        intent.putExtras(bundle);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getDataFromIntent();
        mViewModel = new StatusSelectionViewModel(this, mCategories, mStatuses, mSelectionType);

        StatusSelectionContract.Presenter presenter = new StatusSelectionPresenter(mViewModel);
        mViewModel.setPresenter(presenter);

        ActivityStatusSelectionBinding binding =
                DataBindingUtil.setContentView(this, R.layout.activity_status_selection);
        binding.setViewModel((StatusSelectionViewModel) mViewModel);
    }

    private void getDataFromIntent() {
        Bundle bundle = getIntent().getExtras();
        if (bundle == null) return;
        mCategories = bundle.getParcelableArrayList(BUNDLE_CATEGORIES);
        mStatuses = bundle.getParcelableArrayList(BUNDLE_STATUSES);
        mSelectionType = (StatusSelectionType) bundle.getSerializable(BUNDLE_TYPE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        MenuItem searchMenuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchMenuItem.getActionView();

        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(this);
        return true;
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

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (mViewModel != null) mViewModel.onSearchData(newText);
        return true;
    }
}
