package com.google.android.exoplayer2.demo;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.io.File;

/**
 * Created by Tiantian on 2018/8/27.
 */

public class FirstActivity extends Activity {
    final String[] PERMISSIONS = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,  Manifest.permission.WRITE_EXTERNAL_STORAGE};
    final int REQUEST_CODE_PERMISSIONS = 1000;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //无title
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_first);
        PermissionUtils.checkAndRequestMorePermissions(this, PERMISSIONS, REQUEST_CODE_PERMISSIONS, null);


        findViewById(R.id.mediaPlayer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toPlay(0);
            }
        });
        findViewById(R.id.exoPlayer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toPlay(1);
            }
        });

        toPlay(1);
        finish();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            for (int result : grantResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {

                    return;
                }
            }
        }
    }
    private void toPlay(int type) {
        File file = new File("/storage/emulated/0/demosys-4k/USB 4K/动态画面对比演示.mp4");
        if (file != null && file.exists()) {
            if (type == 0) {
                startActivity(new Intent(FirstActivity.this, DemoMediaPlayActivity.class));
            } else if (type == 1) {
                startActivity(new Intent(FirstActivity.this, DemoPlayerActivity.class));
            }
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("文件不存在");
            builder.setMessage("内存中 “demosys-4k/USB 4K/动态画面对比演示.mp4” 文件不存在！\n请拷贝文件到内存指定目录下");
            builder.setPositiveButton("确定", null);
            builder.setCancelable(false);
            builder.create().show();
        }

    }
}
