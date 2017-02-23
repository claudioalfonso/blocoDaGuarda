package com.generonumero.blocodaguarda.permission;


import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;

public interface PermissionService {

    int GRANTED = 0;
    int DENIED = 1;
    int BLOCKED_OR_NEVER_ASKED = 2;

    boolean hasNeedAskPermission(Context context, String permission);

    void askPermissionFromFragment(Fragment fragment, String[] permissions, int resultCode);

    int getPermissionStatus(Activity activity, String androidPermissionName);
}
