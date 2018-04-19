package com.jiajun.demo.views;

/**
 * 小红点
 * Created by dan on 2016/11/2.
 */

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.jiajun.demo.R;


public class CircleView extends View {

    private final Paint paint;
    private final Context context;

    public CircleView(Context context) {

        // TODO Auto-generated constructor stub
        this(context, null);
    }

    public CircleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
        this.context = context;
        this.paint = new Paint();
        this.paint.setAntiAlias(true); //消除锯齿
        this.paint.setStyle(Paint.Style.FILL); //绘制圆
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.CircleView);
        paint.setColor(Color.parseColor("#FF3C4B"));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        canvas.drawCircle(15, 15,15, this.paint);
        super.onDraw(canvas);
    }


    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}

