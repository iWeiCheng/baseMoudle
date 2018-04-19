package com.jiajun.demo.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * Created by cai.jia on 2016/3/15 0004.
 */
public class SharedPreferenceUtil {

    public static Editor getEditor(Context context) {
        SharedPreferences preferences = getSharedPreferences(context);
        return preferences.edit();
    }

    public static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences("properties", Context.MODE_PRIVATE);
    }

    public static SharedPreferences getAccountSharePerferences(Context context) {
        return context.getSharedPreferences("account_properties", Context.MODE_PRIVATE);
    }

    public static void saveToken(Context context, String token) {
        SharedPreferenceUtil.getEditor(context).putString("token", token).apply();
    }

    public static String getToken(Context context) {
        return SharedPreferenceUtil.getSharedPreferences(context).getString("token", "");
    }

    /**
     * 保存提示音
     *
     * @param context
     * @param name    提示音名称
     * @return
     */
    public static void saveNoticeMusic(Context context, String name) {
        getEditor(context).putString("system_notice_music", name).apply();
    }

    /**
     * 获取提示音
     *
     * @param context
     * @return 提示音名称
     */
    public static String getNoticeMusic(Context context) {
        return getSharedPreferences(context).getString("system_notice_music", "");
    }

}
