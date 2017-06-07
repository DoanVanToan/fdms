package com.framgia.fdms.utils.binding;

import android.content.res.Resources;
import android.databinding.BindingAdapter;
import android.databinding.InverseBindingAdapter;
import android.databinding.InverseBindingListener;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.format.DateUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.framgia.fdms.R;
import com.framgia.fdms.data.model.Category;
import com.framgia.fdms.screen.dashboard.DashboardViewModel;
import com.framgia.fdms.screen.devicedetail.DeviceDetailViewModel;
import com.framgia.fdms.screen.listdevice.ListDeviceViewModel;
import com.framgia.fdms.screen.main.MainViewModel;
import com.framgia.fdms.screen.requestcreation.RequestCreationViewModel;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.formatter.PercentFormatter;
import java.util.Calendar;
import java.util.Date;

import static com.framgia.fdms.screen.dashboard.DashboardViewModel.Tab.TAB_DEVIVE_DASH_BOARD;
import static com.framgia.fdms.screen.dashboard.DashboardViewModel.Tab.TAB_REQUEST_DASH_BOARD;
import static com.framgia.fdms.screen.main.MainViewModel.Tab.TAB_DASH_BOARD;
import static com.framgia.fdms.screen.main.MainViewModel.Tab.TAB_DEVICE_MANAGER;
import static com.framgia.fdms.screen.main.MainViewModel.Tab.TAB_PROFILE;
import static com.framgia.fdms.screen.main.MainViewModel.Tab.TAB_REQUEST_MANAGER;
import static com.framgia.fdms.utils.Constant.DeviceStatus.APPROVED;
import static com.framgia.fdms.utils.Constant.DeviceStatus.CANCELLED;
import static com.framgia.fdms.utils.Constant.DeviceStatus.DONE;
import static com.framgia.fdms.utils.Constant.DeviceStatus.WAITING_APPROVE;
import static com.framgia.fdms.utils.Constant.DeviceStatus.WAITING_DONE;

/**
 * Created by Age on 4/3/2017.
 */
public final class BindingUtils {
    private static final float TEXT_LABLE_SIZE = 11f;
    private static final float TEXT_CENTER_SIZE = 15f;
    private static final int ROTATION_ANGLE = 0;
    private static final int ANIMATE_DURATION = 2000;
    private static final int CIRCLE_ALPHA = 110;
    private static final float DECELERATION_FRICTION_COEF = 0.95f;

    private BindingUtils() {
        // No-op
    }

    @BindingAdapter({ "recyclerAdapter" })
    public static void setAdapterForRecyclerView(RecyclerView recyclerView,
            RecyclerView.Adapter adapter) {
        recyclerView.setAdapter(adapter);
    }

    @BindingAdapter(value = { "app:imageUrl", "app:error" }, requireAll = false)
    public static void loadImage(ImageView view, String imageUrl, Drawable error) {
        if (error == null) {
            Glide.with(view.getContext())
                    .load(imageUrl)
                    .centerCrop()
                    .placeholder(R.drawable.ic_no_image)
                    .into(view);
        } else {
            Glide.with(view.getContext()).load(imageUrl).centerCrop().placeholder(error).into(view);
        }
    }

    @BindingAdapter("errorText")
    public static void setErrorText(TextInputLayout layout, String text) {
        layout.setError(text);
    }

    @BindingAdapter({ "spinnerAdapter" })
    public static void setAdapterForSpinner(AppCompatSpinner spinner,
            ArrayAdapter<String> adapter) {
        spinner.setAdapter(adapter);
    }

    @BindingAdapter({ "bind:font" })
    public static void setFont(TextView textView, String fontName) {
        textView.setTypeface(Typeface.createFromAsset(textView.getContext().getAssets(), fontName));
    }

    @BindingAdapter({ "scrollListenner" })
    public static void setScrollListenner(RecyclerView recyclerView,
            RecyclerView.OnScrollListener listener) {
        recyclerView.addOnScrollListener(listener);
    }

    @BindingAdapter(value = { "bind:adapter", "model" }, requireAll = false)
    public static void setupViewPager(final ViewPager viewPager, FragmentPagerAdapter adapter,
            final MainViewModel viewModel) {
        viewPager.setAdapter(adapter);
        if (viewModel == null) return;
        viewPager.setOffscreenPageLimit(adapter.getCount());
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset,
                    int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                viewModel.setTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    @BindingAdapter("tabPostion")
    public static void onChangeTint(ImageView image, int tab) {
        changeImageColor(image, R.color.color_black);
        switch (tab) {
            case TAB_DASH_BOARD:
                if (image.getId() == R.id.image_dash_board) {
                    changeImageColor(image, R.color.colorPrimaryDark);
                }
                break;
            case TAB_REQUEST_MANAGER:
                if (image.getId() == R.id.image_request_manager) {
                    changeImageColor(image, R.color.colorPrimaryDark);
                }
                break;
            case TAB_DEVICE_MANAGER:
                if (image.getId() == R.id.image_device_manager) {
                    changeImageColor(image, R.color.colorPrimaryDark);
                }
                break;
            case TAB_PROFILE:
                if (image.getId() == R.id.image_profile) {
                    changeImageColor(image, R.color.colorPrimaryDark);
                }
                break;
            default:
                break;
        }
    }

    private static void changeImageColor(ImageView image, int colorRes) {
        image.getDrawable()
                .setColorFilter(image.getContext().getResources().getColor(colorRes),
                        PorterDuff.Mode.SRC_IN);
    }

    @BindingAdapter({ "bind:activity" })
    public static void setupViewPager(Toolbar view, AppCompatActivity activity) {
        activity.setSupportActionBar(view);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @BindingAdapter({ "pieData", "totalValue", "description" })
    public static void setData(PieChart pieChart, PieData pieData, int total, String description) {
        Resources resources = pieChart.getContext().getResources();

        if (pieData.getDataSetCount() > 0) {
            pieChart.setUsePercentValues(true);
            pieChart.setDrawEntryLabels(false);
            pieChart.getDescription().setEnabled(false);
            pieChart.setDragDecelerationFrictionCoef(DECELERATION_FRICTION_COEF);

            pieChart.setDrawHoleEnabled(true);
            pieChart.setHoleColor(Color.WHITE);
            pieChart.setTransparentCircleColor(Color.WHITE);
            pieChart.setTransparentCircleAlpha(CIRCLE_ALPHA);

            pieChart.setDrawCenterText(true);
            pieChart.setRotationAngle(ROTATION_ANGLE);

            pieChart.setRotationEnabled(true);
            pieChart.setHighlightPerTapEnabled(true);
            pieChart.setCenterText(resources.getString(R.string.title_total) + total);
            pieChart.setCenterTextColor(Color.BLUE);
            pieChart.setCenterTextSize(TEXT_CENTER_SIZE);
            pieChart.animateY(ANIMATE_DURATION, Easing.EasingOption.EaseInOutQuad);

            pieChart.getLegend().setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
            pieChart.getLegend().setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
            pieChart.getLegend().setOrientation(Legend.LegendOrientation.VERTICAL);
            pieChart.getLegend().setDrawInside(false);

            pieChart.setEntryLabelColor(Color.WHITE);
            pieChart.setEntryLabelTextSize(TEXT_LABLE_SIZE);
            pieData.setValueFormatter(new PercentFormatter());
            pieData.setValueTextSize(TEXT_LABLE_SIZE);
            pieData.setValueTextColor(Color.WHITE);

            pieChart.setData(pieData);
            pieChart.invalidate();
        }
    }

    /*
    * set Toolbar Activity device return
    * */

    @BindingAdapter({ "view", "titleToolbar" })
    public static void bindToolbar(Toolbar view, AppCompatActivity activity, String resTitle) {
        if (activity == null) return;
        activity.setSupportActionBar(view);
        if (activity.getSupportActionBar() != null) {
            activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        activity.setTitle(resTitle);
    }

    @BindingAdapter({ "model" })
    public static void setupViewPagerDashBorad(final ViewPager viewPager,
            final DashboardViewModel viewModel) {
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset,
                    int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                viewModel.setTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    @BindingAdapter("tabDashBoard")
    public static void onChangeImage(TextView image, int tab) {
        image.setBackgroundResource(R.drawable.bg_circle_grey);

        switch (tab) {
            case TAB_DEVIVE_DASH_BOARD:
                if (image.getId() == R.id.image_device_dashboard) {
                    image.setBackgroundResource(R.drawable.bg_circle_red);
                }
                break;
            case TAB_REQUEST_DASH_BOARD:
                if (image.getId() == R.id.image_request_dashboard) {
                    image.setBackgroundResource(R.drawable.bg_circle_red);
                }
                break;
            default:
                break;
        }
    }

    @BindingAdapter("bind:textForHtml")
    public static void setText(TextView view, String title) {
        view.setText(Html.fromHtml(title));
    }

    @BindingAdapter("bind:dateCreate")
    public static void setDate(TextView view, Date dateTime) {
        if (dateTime == null) {
            view.setText(view.getContext().getString(R.string.title_unknown));
            return;
        }
        String niceDateStr = String.valueOf(DateUtils.getRelativeTimeSpanString(dateTime.getTime(),
                Calendar.getInstance().getTimeInMillis(), DateUtils.MINUTE_IN_MILLIS));
        view.setText(niceDateStr);
    }

    @BindingAdapter("bind:deviceStatus")
    public static void setTextColor(TextView view, String status) {
        switch (status) {
            case CANCELLED:
                view.setTextColor(view.getResources().getColor(R.color.color_red_500));
                break;
            case WAITING_APPROVE:
                view.setTextColor(view.getResources().getColor(R.color.color_blue_600));
                break;
            case APPROVED:
                view.setTextColor(view.getResources().getColor(R.color.color_green));
                break;
            case WAITING_DONE:
                view.setTextColor(view.getResources().getColor(R.color.color_teal_700));
                break;
            case DONE:
                view.setTextColor(view.getResources().getColor(R.color.color_orange_800));
                break;
            default:
                break;
        }
    }

    @BindingAdapter({ "model" })
    public static void onSearch(final SearchView view, final ListDeviceViewModel viewModel) {
        view.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                viewModel.onSearch(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.equals("")) viewModel.onReset();
                return true;
            }
        });

        view.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                viewModel.onReset();
                return true;
            }
        });
    }

    @BindingAdapter({ "resourceId" })
    public static void setImage(ImageView view, int resource) {
        view.setImageResource(resource);
    }

    @InverseBindingAdapter(attribute = "deviceCategoryId", event = "deviceCategoryIdAttrChanged")
    public static int captureDeviceCategoryId(AppCompatSpinner spinner) {
        Object selectedItem = spinner.getSelectedItem();
        return ((Category) selectedItem).getId();
    }

    @BindingAdapter(value = {
            "deviceCategoryId", "deviceCategoryIdAttrChanged"
    }, requireAll = false)
    public static void setCategoryId(AppCompatSpinner view, int newSelectedValue,
            final InverseBindingListener bindingListener) {
        AdapterView.OnItemSelectedListener listener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                bindingListener.onChange();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                bindingListener.onChange();
            }
        };
        view.setOnItemSelectedListener(listener);
    }

    @BindingAdapter({ "position", "viewModel" })
    public static void setAdapterSpinner(AppCompatSpinner spinner, final int position,
            final RequestCreationViewModel viewModel) {
        spinner.setAdapter(viewModel.getAdapterCategory());
        spinner.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                /*viewModel.onAddRequestDetailClick(position);*/
                return false;
            }
        });
    }

    /*
    * bind Spinner Adapter
    * in Activity Return Device
    * */
    @BindingAdapter("spinnerAdapter")
    public static void spinnerAdapter(AppCompatSpinner spinner, ArrayAdapter adapter) {
        spinner.setAdapter(adapter);
    }

    /*
    * bind ViewPager to Model
    * Device Detail ViewModel
    * */

    @BindingAdapter("bindViewModel")
    public static void bindViewModel(ViewPager view, final DeviceDetailViewModel viewModel) {
        view.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset,
                    int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                viewModel.updateFloadtingVisible(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @BindingAdapter("visibleAnim")
    public static void setVisibleFab(FloatingActionButton fab, boolean isVisible) {
        if (isVisible) {
            fab.show();
        } else {
            fab.hide();
        }
    }
}
