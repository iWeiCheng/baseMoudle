package com.jiajun.demo.util;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import static android.support.v4.app.ActivityCompat.requestPermissions;

/**
 * 判断系统权限
 * Created by Administrator on 2016/7/14 0014.
 */
public class PermissionHelper {

    public static final int CODE_REQUEST_PERMISSION = 200;
    private static final String PACKAGE = "package:";

    private static volatile PermissionHelper helper;

    public static PermissionHelper getInstance() {
        if (helper == null) {
            synchronized (PermissionHelper.class) {
                if (helper == null) {
                    helper = new PermissionHelper();
                }
            }
        }
        return helper;
    }

    private PermissionHelper() {
    }

    /**
     * 判断权限集合
     *
     * @param permissions 检测权限的集合
     * @return 权限已全部获取返回true，未全部获取返回false
     */
    public boolean checkPermissions(Activity activity, String... permissions) {
        for (String permission : permissions) {
            if (!checkPermission(activity, permission)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断权限是否获取
     *
     * @param permission 权限名称
     * @return 已授权返回true，未授权返回false
     */
    public boolean checkPermission(Activity activity, String permission) {
        return ContextCompat.checkSelfPermission(activity, permission) == PackageManager.PERMISSION_GRANTED;
    }


    /**
     * 申请权限
     *
     * @param activity    activity
     * @param requestCode 请求code
     * @param permission  权限
     */
    public void requestPermission(Activity activity, int requestCode, String... permission) {
        if (permission == null) {
            return;
        }

        for (String p : permission) {
            requestPermission(activity, requestCode, p, null);
        }
    }

    /**
     * 请求单个权限,有权限的时候在run()里面做相应处理
     *
     * @param activity    activity
     * @param permission  权限
     * @param requestOnly 只请求一次,不考虑用户拒绝的情况,适合那些无关紧要的权限
     * @param runnable    如果有权限在run()方法里面进行相应操作
     */
    public void requestPermission(Activity activity, int requestCode,
                                  String permission, boolean requestOnly, Runnable runnable) {
        if (permission == null) {
            return;
        }

        if (ActivityCompat.checkSelfPermission(activity,
                permission) != PackageManager.PERMISSION_GRANTED) {

            if (requestOnly) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                    //如果用户以前拒绝过改权限申请，则给用户提示

                } else {
                    ActivityCompat.requestPermissions(activity, new String[]{permission}, requestCode);
                }

            } else {
                ActivityCompat.requestPermissions(activity, new String[]{permission}, requestCode);
            }

        } else {

            if (runnable != null) {
                runnable.run();
            }
        }
    }

    /**
     * 请求单个权限,有权限的时候在run()里面做相应处理
     *
     * @param activity   activity
     * @param permission 权限
     * @param runnable   如果有权限在run()方法里面进行相应操作
     */
    public void requestPermission(Activity activity, int requestCode,
                                  String permission, Runnable runnable) {
        requestPermission(activity, requestCode, permission, false, runnable);
    }

    /**
     * 获取权限
     *
     * @param permission
     * @param resultCode
     * @return
     */
    public void permissionsCheck(Activity activity, String permission, int resultCode) {
        // 注意这里要使用shouldShowRequestPermissionRationale而不要使用requestPermission方法
        // 因为requestPermissions方法会显示不在询问按钮
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
            //如果用户以前拒绝过改权限申请，则给用户提示
        } else {
            //进行权限请求
            requestPermissions(activity,
                    new String[]{permission},
                    resultCode);
        }
    }

    /**
     * 一次获取多个权限
     *
     * @param resultCode
     * @return
     */
    public void permissionsCheck(Activity activity, final String[] permissionList, final int resultCode) {
        ActivityCompat.requestPermissions(activity, permissionList, resultCode);
    }


    /**
     * 启动应用的设置
     *
     * @param activity
     */
    public void startAppSettings(Activity activity) {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse(PACKAGE + activity.getPackageName()));
        activity.startActivityForResult(intent, CODE_REQUEST_PERMISSION);
    }

}
