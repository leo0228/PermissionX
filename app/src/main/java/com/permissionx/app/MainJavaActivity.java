package com.permissionx.app;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.permissionx.tianjiaodev.java.IInvisibleListener;
import com.permissionx.tianjiaodev.java.PermissionX;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainJavaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.makeCallBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Map<String, Boolean> permissions = new HashMap<>();
                permissions.put(Manifest.permission.CAMERA, true);
                permissions.put(Manifest.permission.CALL_PHONE, true);
                permissions.put(Manifest.permission.WRITE_EXTERNAL_STORAGE, true);

                PermissionX.init(MainJavaActivity.this, permissions, new IInvisibleListener() {
                    @Override
                    public void onGrant(boolean allGranted, List<String> deniedList) {
                        if (allGranted) {
                            call();
                        } else {
                            Toast.makeText(MainJavaActivity.this, "未授权：" + deniedList.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    private void call() {
        try {
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:10086"));
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
