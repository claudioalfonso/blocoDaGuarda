package com.generonumero.blocodaguarda.network.presenter.impl;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.generonumero.blocodaguarda.network.presenter.NetworkPresenter;
import com.generonumero.blocodaguarda.network.repository.NetworkRepository;
import com.generonumero.blocodaguarda.network.view.NetworkView;
import com.generonumero.blocodaguarda.permission.PermissionService;


public class NetworkPresenterImpl implements NetworkPresenter {

    private static final int RESULT_CODE_PERMISSION = 123;

    private NetworkView networkView;
    private NetworkRepository networkRepository;
    private PermissionService permissionService;

    public NetworkPresenterImpl(NetworkView networkView, NetworkRepository networkRepository, PermissionService permissionService) {
        this.networkView = networkView;
        this.networkRepository = networkRepository;
        this.permissionService = permissionService;
    }

    @Override
    public void loadViews() {
        networkView.OnLoadViews(networkRepository.getAllContacts());
    }




    @Override
    public boolean askPermissionIfNeed(Fragment fragment, String permission) {
        if(permissionService.hasNeedAskPermission(fragment.getContext(), permission)) {
            permissionService.askPermissionFromFragment(fragment, new String[]{permission}, RESULT_CODE_PERMISSION);
            return true;
        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(Activity activity, int requestCode, @NonNull int[] grantResults, String permission) {
        switch (requestCode) {

            case RESULT_CODE_PERMISSION:
                if (grantResults.length <= 0 || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    if (permissionService.getPermissionStatus(activity, permission) == PermissionService.BLOCKED_OR_NEVER_ASKED) {
                        networkView.showAlertPermissionDesable();
                    } else {
                        networkView.showAlertPermissionDenied();
                    }
                }
                break;

        }
    }
}
