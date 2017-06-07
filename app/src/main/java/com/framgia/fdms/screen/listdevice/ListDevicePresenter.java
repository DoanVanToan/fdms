package com.framgia.fdms.screen.listdevice;

import com.framgia.fdms.data.model.Category;
import com.framgia.fdms.data.model.Device;
import com.framgia.fdms.data.model.Status;
import com.framgia.fdms.data.model.User;
import com.framgia.fdms.data.source.CategoryRepository;
import com.framgia.fdms.data.source.DeviceRepository;
import com.framgia.fdms.data.source.StatusRepository;
import com.framgia.fdms.data.source.UserRepository;
import java.util.List;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

import static com.framgia.fdms.utils.Constant.FIRST_PAGE;
import static com.framgia.fdms.utils.Constant.NOT_SEARCH;
import static com.framgia.fdms.utils.Constant.OUT_OF_INDEX;
import static com.framgia.fdms.utils.Constant.PER_PAGE;

/**
 * Listens to user actions from the UI ({@link ListDeviceFragment}), retrieves the data and updates
 * the UI as required.
 */
final class ListDevicePresenter implements ListDeviceContract.Presenter {
    private CompositeSubscription mCompositeSubscriptions = new CompositeSubscription();
    private int mPage = FIRST_PAGE;

    private final ListDeviceContract.ViewModel mViewModel;
    private DeviceRepository mDeviceRepository;
    private CategoryRepository mCategoryRepository;
    private StatusRepository mStatusRepository;
    private String mKeyWord = NOT_SEARCH;
    private int mCategoryId = OUT_OF_INDEX;
    private int mStatusId = OUT_OF_INDEX;
    private UserRepository mUserRepository;

    public ListDevicePresenter(ListDeviceContract.ViewModel viewModel,
            DeviceRepository deviceRepository, CategoryRepository categoryRepository,
            StatusRepository statusRepository, UserRepository userRepository) {
        mViewModel = viewModel;
        mDeviceRepository = deviceRepository;
        mCategoryRepository = categoryRepository;
        mStatusRepository = statusRepository;
        mUserRepository = userRepository;
    }

    @Override
    public void onStart() {
    }

    @Override
    public void onStop() {
        mCompositeSubscriptions.clear();
    }

    public void getListDevice(String deviceName, int categoryId, int statusId, int page,
            int perPage) {
        Subscription subscription =
                mDeviceRepository.getListDevices(deviceName, categoryId, statusId, page, perPage)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe(new Action0() {
                            @Override
                            public void call() {
                                mViewModel.showProgressbar();
                            }
                        })
                        .subscribe(new Subscriber<List<Device>>() {
                            @Override
                            public void onCompleted() {
                                mViewModel.hideProgressbar();
                            }

                            @Override
                            public void onError(Throwable e) {
                                mViewModel.onError(e.getCause().getMessage());
                            }

                            @Override
                            public void onNext(List<Device> devices) {
                                mViewModel.onDeviceLoaded(devices);
                            }
                        });
        mCompositeSubscriptions.add(subscription);
    }

    public void getListCategories() {
        Subscription subscription = mCategoryRepository.getListCategory()
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mViewModel.showProgressbar();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<Category>>() {
                    @Override
                    public void call(List<Category> categories) {
                        mViewModel.onDeviceCategoryLoaded(categories);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mViewModel.onError(throwable.getCause().getMessage());
                    }
                }, new Action0() {
                    @Override
                    public void call() {
                        mViewModel.hideProgressbar();
                    }
                });
        mCompositeSubscriptions.add(subscription);
    }

    public void getListStatuses() {
        Subscription subscription = mStatusRepository.getListStatus()
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mViewModel.showProgressbar();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<Status>>() {
                    @Override
                    public void call(List<Status> statuses) {
                        mViewModel.onDeviceStatusLoaded(statuses);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mViewModel.onError(throwable.getCause().getMessage());
                    }
                }, new Action0() {
                    @Override
                    public void call() {
                        mViewModel.hideProgressbar();
                    }
                });
        mCompositeSubscriptions.add(subscription);
    }

    @Override
    public void loadMoreData() {
        mPage++;
        getListDevice(mKeyWord, mCategoryId, mStatusId, mPage, PER_PAGE);
    }

    @Override
    public void getData(String keyWord, Category category, Status status) {
        mPage = FIRST_PAGE;
        if (category != null) {
            mCategoryId = category.getId();
        }
        if (status != null) {
            mStatusId = status.getId();
        }
        if (keyWord != null) {
            mKeyWord = keyWord;
        }
        getListDevice(mKeyWord, mCategoryId, mStatusId, mPage, PER_PAGE);
        getListCategories();
        getListStatuses();
        getCurrentUser();
    }

    @Override
    public void getCurrentUser() {
        Subscription subscription = mUserRepository.getCurrentUser()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<User>() {
                    @Override
                    public void call(User user) {
                        mViewModel.setupFloatingActionsMenu(user);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mViewModel.onError(throwable.getMessage());
                    }
                });
        mCompositeSubscriptions.add(subscription);
    }
}
