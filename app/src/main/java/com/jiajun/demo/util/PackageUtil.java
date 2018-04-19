package com.jiajun.demo.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import java.util.List;

/**
 * Created by cai.jia on 2016/6/23 0023.
 */
public class PackageUtil {

    /**
     * 获取apk文件的版本号
     *
     * @param context
     * @param apkFilePath apk文件路径
     * @return
     */
    public static int getApkVersionCode(Context context, String apkFilePath) {
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo info = pm.getPackageArchiveInfo(apkFilePath, PackageManager.GET_ACTIVITIES);
            if (info != null) {
                return info.versionCode;
            }
        } catch (Exception e) {

        }
        return -1;
    }

    /**
     * 获取apk文件的包名
     *
     * @param context
     * @param apkFilePath apk文件路径
     * @return
     */
    public static String getApkPackageName(Context context, String apkFilePath) {
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo info = pm.getPackageArchiveInfo(apkFilePath, PackageManager.GET_ACTIVITIES);
            if (info != null) {
                return info.applicationInfo.packageName;
            }
        } catch (Exception e) {

        }
        return null;
    }

    /**
     * 判断是否安装
     *
     * @param context
     * @param packageName 包名
     * @return
     */
    public static boolean isInstalled(Context context, String packageName) {
        final PackageManager packageManager = context.getPackageManager();
        // 获取所有已安装程序的包信息
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        for (int i = 0; i < pinfo.size(); i++) {
            if (pinfo.get(i).packageName.equalsIgnoreCase(packageName))
                return true;
        }
        return false;
    }

    /**
     * 获取程序版本号
     *
     * @param context
     * @return
     */
    public static int getAppVersionCode(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(
                    context.getPackageName(), Context.MODE_PRIVATE).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * 获取程序版本名称
     *
     * @param context
     * @return
     */
    public static String getAppVersionName(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(
                    context.getPackageName(), Context.MODE_PRIVATE).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }
}
