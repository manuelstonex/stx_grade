package com.van.fileselector.activity;

import android.Manifest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.van.fileselector.util.DynamicPermissionCompat;

import java.util.List;

public abstract class BasePermissionActivity extends AppCompatActivity {
    private DynamicPermissionCompat dynamicPermissionCompat;
    protected String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};
    protected int requestCode = 1000;

    @Override
    protected void onResume() {
        super.onResume();
        requestPermissionAfterOnCreate();
    }

    protected void requestPermissionAfterOnCreate() {
        dynamicPermissionCompat = DynamicPermissionCompat.getInstanceIfNull(this, dynamicPermissionCompat);
        dynamicPermissionCompat.setOnPermissionListener(permissionListener)
                .setPermissions(permissions)
                .setRequestCode(requestCode)
                .start();
    }

    private DynamicPermissionCompat.OnPermissionListener permissionListener = new DynamicPermissionCompat.OnPermissionListener() {
        @Override
        public void success(int requestCode) {
            onPermissionSuccess();
        }

        @Override
        public void failAndTipUser(int requestCode, List<String> deniedPermissions) {
            onPermissionFailure();
        }

        @Override
        public void alwaysDenied(int requestCode, List<String> deniedPermissions) {
            onPermissionNotTopForever();
        }
    };

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == this.requestCode && dynamicPermissionCompat != null) {
            dynamicPermissionCompat.onRequestPermissionsResult(permissions, grantResults);
        }
    }

    /**
     * 权限申请成功
     */
    protected abstract void onPermissionSuccess();

    /**
     * 权限申请失败
     */
    protected void onPermissionFailure() {
    }

    /**
     * 权限申请失败，永远拒绝
     */
    protected void onPermissionNotTopForever() {
    }
}
