package com.permissionx.tianjiaodev.java;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import java.util.Map;

public class PermissionX {

    public static final String TAG = "EachPermissionFragment";

    /**
     * 申请权限
     * @param activity
     * @param permissions String表示要申请的权限，Boolean表示对应权限是否必须
     * @param callback
     */
    public static void init(FragmentActivity activity, Map<String, Boolean> permissions, IInvisibleListener callback) {

        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        Fragment existedFragment = fragmentManager.findFragmentByTag(TAG);
        Fragment fragment;

        if (existedFragment != null) {
            fragment = existedFragment;
        } else {
            InvisibleFragment invisibleFragment = new InvisibleFragment();
            fragmentManager.beginTransaction().add(invisibleFragment, TAG).commitNow();
            fragment = invisibleFragment;
        }

        ((InvisibleFragment) fragment).requestEachPermissions(permissions, callback);
    }
}
