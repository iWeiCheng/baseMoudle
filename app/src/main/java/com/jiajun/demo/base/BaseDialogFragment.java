package com.jiajun.demo.base;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.util.LongSparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.jiajun.demo.R;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;

public abstract class BaseDialogFragment extends DialogFragment implements BaseView {

    /**
     * Activity是否销毁
     */
    private boolean mIsDestroy;

    /**
     * 网络或者延时加载时对话框
     */
    private ProgressDialog mProgressDialog;

    /**
     * 布局layout
     */
    private int mLayoutId;

    /**
     * 接口数据返回完成(当一个接口需要别的接口返回后的结果时,可以根据list来判断是否完成请求)
     *
     * @key 接口对应的唯一码
     * @value 是否成功
     */
    private LongSparseArray<Boolean> apiRequestFinish;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.Dialog_Style);
        apiRequestFinish = new LongSparseArray<>();

        if (supportEventBus()) {
            if (!EventBus.getDefault().isRegistered(this)) {
                EventBus.getDefault().register(this);
            }
        }

        //获取传递过来的参数
        Bundle args = getArguments();
        if (args != null) {
            getExtra(args);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //加载布局文件
        loadLayout();
        View child = LayoutInflater.from(getContext()).inflate(mLayoutId, container, false);
        ButterKnife.bind(this, child);
        return child;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        int screenWidth = getContext().getResources().getDisplayMetrics().widthPixels;
        Window window = getDialog().getWindow();
        if (window != null) {
            getDialog().setCanceledOnTouchOutside(false);
            getDialog().getWindow().setLayout((int) (screenWidth * 0.85f),
                    ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        //初始化一些对象
        initialize(view, savedInstanceState);

        //简单逻辑处理
        processLogic(view, savedInstanceState);

        //监听事件
        setListener(view, savedInstanceState);
    }

    /**
     * 设置fragment的布局
     *
     * @param mLayoutId 布局id
     */
    public void setContentView(@LayoutRes int mLayoutId) {
        this.mLayoutId = mLayoutId;
    }

    /**
     * 一些简单的逻辑处理
     */
    protected void processLogic(View view, Bundle savedInstanceState) {

    }

    /**
     * 是否需要EventBus通知功能,如果需要支持eventBus,重写这个方法,返回true
     */
    protected boolean supportEventBus() {
        return false;
    }

    /**
     * 设置布局
     */
    protected abstract void loadLayout();

    /**
     * 得到传递过来的参数
     *
     * @param bundle bundle
     */
    protected void getExtra(@NonNull Bundle bundle) {

    }

    /**
     * 初始化view或者其它组件
     */
    protected void initialize(View view, Bundle savedInstanceState) {

    }

    /**
     * 设置一些简单的监听事件
     */
    protected void setListener(View view, Bundle savedInstanceState) {

    }

    /**
     * activity是否销毁
     *
     * @return
     */
    @Override
    public boolean isDestroy() {
        return mIsDestroy;
    }

    /**
     * @param requestId 每个接口对应的唯一标识
     * @param code      错误码
     * @param message   错误消息
     */
    @Override
    public void onHttpFailure(long requestId, String code, String message) {
        //子类重写处理错误
        dismissDialog();
    }

    @Override
    public void onHttpComplete(boolean success, long requestId, String code, String message) {
        //表示一次请求完成
        apiRequestFinish.put(requestId, success);
    }

    /**
     * 判断哪个http请求完成
     *
     * @param requestId 每个接口对应的唯一标识
     * @return
     */
    protected boolean isApiFinish(long requestId) {
        int size = apiRequestFinish.size();
        for (int i = 0; i < size; i++) {
            long id = apiRequestFinish.keyAt(i);
            if (id == requestId) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断哪个http请求成功
     *
     * @param requestId 每个接口对应的唯一标识
     * @return
     */
    protected boolean isApiSuccess(long requestId) {
        int size = apiRequestFinish.size();
        for (int i = 0; i < size; i++) {
            Boolean success = apiRequestFinish.valueAt(i);
            if (success != null && success) {
                return true;
            }
        }
        return false;
    }

    /**
     * 显示进度对话框
     */
    protected void showProgressDialog() {
        if (!isDestroy() && mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(getActivity());
        }

        if (!isDestroy() && !mProgressDialog.isShowing()) {
            mProgressDialog.show();
        }
    }

    /**
     * 销毁进度对话框
     */
    protected void dismissDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    /**
     * 每个页面中网络请求的tag,当页面销毁时取消掉请求
     *
     * @return
     */
    @Override
    public Object tag() {
        return this.getClass().getCanonicalName();
    }

    @Override
    public void onDestroy() {
        mIsDestroy = true;
        apiRequestFinish.clear();
        dismissDialog();
        if (supportEventBus()) {
            EventBus.getDefault().unregister(this);
        }
        super.onDestroy();
    }
}
