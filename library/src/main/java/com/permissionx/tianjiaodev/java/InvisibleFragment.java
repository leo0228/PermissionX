package com.permissionx.tianjiaodev.java;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class InvisibleFragment extends Fragment {

    private IInvisibleListener mEachCallback;
    private FragmentActivity mActivity;

    private static final int PERMISSION_CODE = 1;
    private static final int SETTING_CODE = 2;

    private Map<String, Boolean> mPermission;

    public void requestEachPermissions(@NonNull Map<String, Boolean> permissions, IInvisibleListener callback) {
        mEachCallback = callback;
        mPermission = permissions;

        Set<String> keys = mPermission.keySet();
        String[] p = keys.toArray(new String[keys.size()]);
        requestPermissions(p, PERMISSION_CODE);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        mActivity = getActivity();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_CODE) {
            List<String> deniedList = new ArrayList<>();
            for (int i = 0; i < grantResults.length; i++) {
                int grantResult = grantResults[i];
                String name = permissions[i];
                if (grantResult != PackageManager.PERMISSION_GRANTED) {
                    deniedList.add(name);
                    //如果权限是必要的
                    if (mPermission.get(name)) {
                        showTipDialog();
                    } else {
                        if (!ActivityCompat.shouldShowRequestPermissionRationale(mActivity, name)) {
                            //拒绝，不要再询问
                        }
                    }
                }
            }

            boolean allGranted = deniedList.isEmpty();
            mEachCallback.onGrant(allGranted, deniedList);
        }
    }

    private void showTipDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        builder.setTitle("Tips");

        builder.setMessage("Authorization is required to enable the functionality.\nPlease go ahead and set up.");
        builder.setPositiveButton("Go", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                settingToActivity();
            }
        });
        builder.setNegativeButton("Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                mActivity.finish();
                System.exit(0);
            }
        });

        builder.show();
    }

    private void settingToActivity() {
        Intent intent = new Intent();
        intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
        intent.setData(Uri.parse("package:" + mActivity.getPackageName()));
        mActivity.startActivityForResult(intent, SETTING_CODE);
    }

}
