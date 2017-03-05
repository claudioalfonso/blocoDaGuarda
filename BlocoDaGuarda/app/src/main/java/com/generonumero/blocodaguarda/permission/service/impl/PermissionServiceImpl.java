package com.generonumero.blocodaguarda.permission.service.impl;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;

import com.generonumero.blocodaguarda.permission.service.PermissionService;

/**
 * Created by Pedro on 2/22/17.
 */

public class PermissionServiceImpl implements PermissionService{

    public boolean hasNeedAskPermission(Context context, String permission) {
        return ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED;
    }

    public void askPermissionFromFragment(Fragment fragment, String[] permissions, int resultCode) {
        fragment.requestPermissions(permissions, resultCode);
    }

    public int getPermissionStatus(Activity activity, String androidPermissionName) {
        if (ContextCompat.checkSelfPermission(activity, androidPermissionName) != PackageManager.PERMISSION_GRANTED) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(activity, androidPermissionName)) {
                return PermissionService.BLOCKED_OR_NEVER_ASKED;
            }
            return PermissionService.DENIED;
        }
        return PermissionService.GRANTED;
    }
}
