package com.jiajun.demo.base;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;

import com.jiajun.demo.R;
import com.jiajun.demo.util.DeviceUtil;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

/**
 * dialog从底部弹出
 * Created by cai.jia on 2016/3/19 0019.
 */
public abstract class BottomDialogFragment extends BaseDialogFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.translateDialogStyle);
    }

    public void setWindowParams() {
        WindowManager.LayoutParams params = this.getDialog().getWindow().getAttributes();
        params.dimAmount = 0.4f;
        params.gravity = Gravity.BOTTOM;
        this.getDialog().getWindow().setAttributes(params);
        getDialog().setCanceledOnTouchOutside(true);
        getDialog().getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        getDialog().getWindow().setLayout(MATCH_PARENT, WRAP_CONTENT);

        final View view = getView();
        if (view != null) {
            view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    int height = view.getMeasuredHeight();
                    int maxHeight = (int) (DeviceUtil.getScreenHeight(getContext()) * getHeightPercent());
                    if (height > maxHeight) {
                        getDialog().getWindow().setLayout(MATCH_PARENT, maxHeight);
                    }
                    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN) {
                        view.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    } else {
                        view.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    }
                }
            });
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setWindowParams();
    }

    /**
     * 高度为屏幕高度的百分比
     *
     * @return
     */
    public float getHeightPercent() {
        return 0.7f;
    }
}
