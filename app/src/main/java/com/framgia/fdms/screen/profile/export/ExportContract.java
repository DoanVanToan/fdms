package com.framgia.fdms.screen.profile.export;

import android.graphics.drawable.Drawable;
import com.framgia.fdms.BasePresenter;
import com.framgia.fdms.BaseViewModel;
import com.framgia.fdms.data.model.Device;
import java.util.List;

/**
 * Created by tuanbg on 6/14/17.
 */

public interface ExportContract {
    interface ViewModel extends BaseViewModel<Presenter> {
        void onExportPdfClick();

        void onExportWordClick();

        void onCancelClick();

        void onDestroy();

        void openFilePDF(String filePath);

        String getString(int strResource);

        String[] getStringArray(int strArrResource);

        Drawable getDrawable(int drawableResource);

        void showMessage(String mess);

        void showMessage(int strResource);

        void onExportPdfSuccess(String filePath);

        void onExportDocSuccess(String filePath);

        void openFileDoc(String filePath);
    }

    interface Presenter extends BasePresenter {

        void exportDeviceByPdf(List<Device> list);

        void exportDeviceByDoc(List<Device> list);

        void onDestroy();
    }
}
