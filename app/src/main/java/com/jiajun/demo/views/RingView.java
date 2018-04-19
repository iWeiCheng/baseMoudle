package com.jiajun.demo.views;

/**
 * 圆环
 * Created by dan on 2016/11/7.
 */
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class RingView extends View {

    private final  Paint paint;
    private final Context context;

    public RingView(Context context) {
        this(context, null);
    }

    public RingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        this.paint = new Paint();
        this.paint.setAntiAlias(true); //消除锯齿
        this.paint.setStyle(Paint.Style.STROKE); //绘制空心圆
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        int center = getWidth()/2;
        int innerCircle = dip2px(context, 83); //设置内圆半径
        int ringWidth = dip2px(context, 10); //设置圆环宽度

        //绘制内圆
        this.paint.setARGB(155, 167, 190, 206);
        this.paint.setStrokeWidth(10);//设置内圆的厚度
        canvas.drawCircle(center,center, innerCircle, this.paint);//以该圆为半径向内外扩展至厚度为10px

        //绘制圆环,设置圆环的颜色修改画笔的颜色
//		this.paint.setARGB(255, 212 ,225, 233);
        this.paint.setARGB(255, 255, 0, 0);
        this.paint.setStrokeWidth(ringWidth);//设置圆环宽度
        canvas.drawCircle(center,center, innerCircle+1+ringWidth/2, this.paint);//圆环宽度为中间圆

        //绘制外圆
        this.paint.setARGB(155, 167, 190, 206);
        this.paint.setStrokeWidth(2);
        canvas.drawCircle(center,center, innerCircle+ringWidth, this.paint);

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

