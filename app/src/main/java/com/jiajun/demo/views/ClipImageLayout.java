package com.jiajun.demo.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.jiajun.demo.R;
import com.jiajun.demo.util.BitmapUtil;


/**
 * http://blog.csdn.net/lmj623565791/article/details/39761281
 *
 * @author zhy
 */
public class ClipImageLayout extends RelativeLayout {

    public static final int MAX_WIDTH = 600;
    public static final int MAX_HEIGHT = 800;

    private ClipZoomImageView mZoomImageView;
    private ClipImageBorderView mClipImageView;

    private int clipDrawableId;

    /**
     * 这里测试，直接写死了大小，真正使用过程中，可以提取为自定义属性
     */
    private int mHorizontalPadding;

    public ClipImageLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        mZoomImageView = new ClipZoomImageView(context);
        mClipImageView = new ClipImageBorderView(context);

        android.view.ViewGroup.LayoutParams lp = new LayoutParams(
                android.view.ViewGroup.LayoutParams.MATCH_PARENT,
                android.view.ViewGroup.LayoutParams.MATCH_PARENT);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ClipImageLayout);
        try {
            clipDrawableId = a.getResourceId(R.styleable.ClipImageLayout_clip_drawable, -1);
            mHorizontalPadding = a.getDimensionPixelOffset(R.styleable.ClipImageLayout_clip_padding, 50);
        } finally {
            a.recycle();
        }
        mZoomImageView.setImageResource(clipDrawableId);

        this.addView(mZoomImageView, lp);
        this.addView(mClipImageView, lp);

        mZoomImageView.setHorizontalPadding(mHorizontalPadding);
        mClipImageView.setHorizontalPadding(mHorizontalPadding);
    }

    /**
     * 对外公布设置边距的方法,单位为dp
     *
     * @param mHorizontalPadding
     */
    public void setHorizontalPadding(int mHorizontalPadding) {
        this.mHorizontalPadding = mHorizontalPadding;
    }

    public void setClipDrawableId(int clipDrawableId) {
        this.clipDrawableId = clipDrawableId;
    }

    public void setClipDrawable(Drawable drawable) {
        mZoomImageView.setImageDrawable(drawable);
    }

    public void setClipImagePath(String localPath) {
        Bitmap bitmap = BitmapUtil.getSmallBitmap(localPath, MAX_WIDTH, MAX_HEIGHT);
        if (bitmap != null) {
            mZoomImageView.setImageBitmap(bitmap);
        }
    }

    /**
     * 裁切图片
     *
     * @return
     */
    public Bitmap clip() {
        return mZoomImageView.clip();
    }

}
