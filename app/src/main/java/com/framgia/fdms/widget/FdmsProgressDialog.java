package com.framgia.fdms.widget;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.widget.ProgressBar;

/**
 * Created by tuanbg on 3/1/17.
 */
public class FdmsProgressDialog extends ProgressDialog {
    public FdmsProgressDialog(Context context) {
        super(context);
    }

    private static FdmsProgressDialog sFdmsProgressDialog;

    public static FdmsProgressDialog getInstance(Context context) {
        if (sFdmsProgressDialog == null) sFdmsProgressDialog = new FdmsProgressDialog(context);
        return sFdmsProgressDialog;
    }

    @Override
    public void show() {
        super.show();
        if (getWindow() != null) {
            getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        setContentView(new ProgressBar(getContext()));
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }
}
