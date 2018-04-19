package com.jiajun.demo.base;

import android.app.Activity;
import android.content.Context;

public interface BaseView {

    /**
     * 页面是否销毁
     *
     * @return
     */
    boolean isDestroy();

    /**
     * 得到上下文
     *
     * @return
     */
    Context getContext();

    /**
     * 获取Activity
     * @return
     */
    Activity getActivity();

    /**
     * http请求tag
     *
     * @return
     */
    Object tag();

    /**
     * http请求错误
     *
     * @param requestId 每个接口对应的唯一标识
     * @param code      错误码
     * @param message   错误消息
     */
    void onHttpFailure(long requestId, String code, String message);

    /**
     * http请求完成
     *
     * @param success   网络请求是否成功
     * @param requestId 每个接口对应的唯一标识
     * @param code      返回码
     * @param message   提示消息
     */
    void onHttpComplete(boolean success, long requestId, String code, String message);
}