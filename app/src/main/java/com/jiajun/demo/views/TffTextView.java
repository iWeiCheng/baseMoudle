package com.jiajun.demo.views;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/** 
 * 修改字体 
 * 
 */  
public class TffTextView extends TextView
{  
  
    public TffTextView(Context context)
    {  
        super(context);  
    }  
  
    public TffTextView(Context context, AttributeSet attrs)
    {  
        super(context, attrs);  
        changeTypeFace(context, attrs);  
    }  
  
    /** 
     * 改变字体类型 
     * @param context 
     * @param attrs 
     */  
    private void changeTypeFace(Context context, AttributeSet attrs)
    {  
        if (attrs != null)  
        {  
            //TypedArray a = context.obtainStyledAttributes(attrs,  
            //R.styleable.TextView_Typefaces);  
            //            tf = a.getInt(R.styleable.TextView_Typefaces_tf, tf);  
            Typeface mtf = Typeface.createFromAsset(context.getAssets(),
                    "impact.ttf");
            super.setTypeface(mtf);  
        }  
    }  
} 