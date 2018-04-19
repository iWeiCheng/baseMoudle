package com.jiajun.demo.base;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

//import butterknife.ButterKnife;

public abstract class BaseDialog extends Dialog {

    private DialogConfig config;

    private View contentView;

    public BaseDialog(Context context) {
        super(context);
        this.config = getDefaultConfig();
    }

    public DialogConfig getDefaultConfig() {
        return new DialogConfig.Builder()
                .cancelable(false)
                .allowBounds(true)
                .gravity(Gravity.CENTER)
                .dimAmount(-1)
                .widthPercent(0.9f)
                .heightPercent(0.9f)
                .style(0)
                .build();
    }

    public BaseDialog(Context context, DialogConfig config) {
        super(context, config.getStyle());
        this.config = config;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        initView();
    }

    private void setDialogParams() {
        contentView.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                int measuredHeight = contentView.getMeasuredHeight();
                int measuredWidth = contentView.getMeasuredWidth();
                int heightPixels = getContext().getResources().getDisplayMetrics().heightPixels;
                int widthPixels = getContext().getResources().getDisplayMetrics().widthPixels;
                Window window = BaseDialog.this.getWindow();
                window.setBackgroundDrawableResource(android.R.color.transparent);
                WindowManager.LayoutParams params = window.getAttributes();

                if (config.isAllowBounds() && measuredHeight >= heightPixels
                        * config.getHeightPercent()) {
                    params.height = (int) (heightPixels * config.getHeightPercent());
                }

                if (config.isAllowBounds() && measuredWidth >= widthPixels
                        * config.getWidthPercent()) {
                    params.width = (int) (widthPixels * config.getWidthPercent());
                }

                if (config.getDimAmount() != -1) {
                    params.dimAmount = config.getDimAmount();
                }

                params.gravity = config.getGravity();
                window.setAttributes(params);

                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                    contentView.getViewTreeObserver().removeGlobalOnLayoutListener(this);

                } else {
                    contentView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
            }
        });
        setCanceledOnTouchOutside(config.isCancelable());
    }

    private void initView() {
        loadLayout();
        initialize();
        processLogic();
        setListener();
    }

    public void setContentView(int layoutResID) {
        View view = getLayoutInflater().inflate(layoutResID, new LinearLayout(getContext()),false);
        setContentView(view);
    }

    @Override
    public void setContentView(View view) {
        contentView = view;
        setDialogParams();
        super.setContentView(view);
//        ButterKnife.bind(this, view);
    }

    public View findViewById(int resID) {
        return super.findViewById(resID);
    }

    protected void initialize() {

    }

    protected  void processLogic() {

    }

    protected  void setListener() {

    }

    protected abstract void loadLayout();
}
