package com.jiajun.demo.util;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.jiajun.demo.R;


/**
 * toast工具类，包括自定义toast
 * Created by cai.jia on 2016/7/27 0027.
 */

public class ToastUtil {

    /**
     * 弹出toast
     *
     * @param context
     * @param msg
     */
    public static void showToast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * 弹出系统设置-清除缓存后弹出的toast
     *
     * @param context
     */
    public static void showClearCacheToast(Context context) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.system_setting_clear_cache_toast, new LinearLayout(context), false);
        Toast toast = new Toast(context);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(view);
        toast.show();
    }
}
