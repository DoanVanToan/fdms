package com.framgia.fdms.utils.permission;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import com.framgia.fdms.R;

/**
 * Created by Nhahv0902 on 6/6/2017.
 * <></>
 */

public class PermissionUtil {

    public static final int MY_PERMISSIONS_REQUEST_CAMERA = 1;
    public static final int MY_PERMISSIONS_REQUEST_WRITE = 2;

    public static boolean checkCameraPermission(final AppCompatActivity activity) {
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                    Manifest.permission.CAMERA)) {
                new AlertDialog.Builder(activity).setMessage(R.string.msg_request_read_camera)
                        .setPositiveButton(R.string.action_agree,
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        ActivityCompat.requestPermissions(activity,
                                                new String[] { Manifest.permission.CAMERA },
                                                MY_PERMISSIONS_REQUEST_CAMERA);
                                    }
                                })
                        .setNegativeButton(R.string.action_dis_agree, null)
                        .show();
            } else {
                ActivityCompat.requestPermissions(activity,
                        new String[] { Manifest.permission.CAMERA }, MY_PERMISSIONS_REQUEST_CAMERA);
            }
            return false;
        }
        return true;
    }

    public static boolean checkWritePermission(final AppCompatActivity activity) {
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                new AlertDialog.Builder(activity).setMessage(R.string.msg_request_write_file)
                        .setPositiveButton(R.string.action_agree,
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        ActivityCompat.requestPermissions(activity, new String[] {
                                                Manifest.permission.WRITE_EXTERNAL_STORAGE
                                        }, MY_PERMISSIONS_REQUEST_WRITE);
                                    }
                                })
                        .setNegativeButton(R.string.action_dis_agree, null)
                        .show();
            } else {
                ActivityCompat.requestPermissions(activity,
                        new String[] { Manifest.permission.WRITE_EXTERNAL_STORAGE },
                        MY_PERMISSIONS_REQUEST_WRITE);
            }
            return false;
        }
        return true;
    }
}
