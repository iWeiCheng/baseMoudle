package com.jiajun.demo.views;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.animation.Animation;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;

import com.jiajun.demo.R;


public class PassWordEditText extends EditText implements
        OnFocusChangeListener, TextWatcher { 
	/**
	 * 删除按钮的引用
	 */
    private Drawable mvisiableDrawable; 
    /**
     * 控件是否有焦点
     */
    private boolean hasFoucs;
    
    /**
     * 是否显示明文密码
     * 
     */
    private boolean isHidden=true;
 
    public PassWordEditText(Context context) { 
    	this(context, null); 
    } 
 
    public PassWordEditText(Context context, AttributeSet attrs) { 
    	//这里构造方法也很重要，不加这个很多属性不能再XML里面定义
    	this(context, attrs, android.R.attr.editTextStyle); 
    } 
    
    public PassWordEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }
    
    
    private void init() { 
    	//获取EditText的DrawableRight,假如没有设置我们就使用默认的图片
    	mvisiableDrawable = getCompoundDrawables()[2]; 
        if (mvisiableDrawable == null) { 
        	mvisiableDrawable = getResources().getDrawable(R.drawable.account_password_gone_iv); //应该设置显示和隐藏 的图标，待美工给图标
        } 
        mvisiableDrawable.setBounds(0, 0, mvisiableDrawable.getIntrinsicWidth(), mvisiableDrawable.getIntrinsicHeight()); 
        //默认设置隐藏图标
        setClearIconVisible(false); 
        //设置焦点改变的监听
        setOnFocusChangeListener(this); 
        //设置输入框里面内容发生改变的监听
        addTextChangedListener(this); 
    } 
    private void init2(int drawable) { 
    	//获取EditText的DrawableRight,假如没有设置我们就使用默认的图片
    	mvisiableDrawable = getCompoundDrawables()[2]; 
    	mvisiableDrawable = getResources().getDrawable(drawable); //应该设置显示和隐藏 的图标，待美工给图标
    	mvisiableDrawable.setBounds(0, 0, mvisiableDrawable.getIntrinsicWidth(), mvisiableDrawable.getIntrinsicHeight()); 
    	//默认设置隐藏图标
    	setClearIconVisible(true); 
    	//设置焦点改变的监听
    	setOnFocusChangeListener(this); 
    	//设置输入框里面内容发生改变的监听
    	addTextChangedListener(this); 
    } 
 
 
    
    
    /**
     * 因为我们不能直接给EditText设置点击事件，所以我们用记住我们按下的位置来模拟点击事件
     * 当我们按下的位置 在  EditText的宽度 - 图标到控件右边的间距 - 图标的宽度  和
     * EditText的宽度 - 图标到控件右边的间距之间我们就算点击了图标，竖直方向就没有考虑
     */
	@Override 
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_UP) {
			if (getCompoundDrawables()[2] != null) {

				boolean touchable = event.getX() > (getWidth() - getTotalPaddingRight())
						&& (event.getX() < ((getWidth() - getPaddingRight())));
				
				if (touchable) {
					 if (isHidden) {
		                 //设置EditText文本为可见的
						 init2(R.drawable.account_password_visible_iv);
						 this.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
		             } else {
		            	 init2(R.drawable.account_password_gone_iv);
		            	 this.setTransformationMethod(PasswordTransformationMethod.getInstance());
		             }
		             isHidden = !isHidden;
		             this.postInvalidate();
		             //切换后将EditText光标置于末尾
		             CharSequence charSequence = this.getText();
		             if (charSequence instanceof Spannable) {
		                 Spannable spanText = (Spannable) charSequence;
		                 Selection.setSelection(spanText, charSequence.length());
		             }
				}
			}
		}

		return super.onTouchEvent(event);
	}
 
    private void setBackgroundResource(Drawable drawable) {
		// TODO Auto-generated method stub
		
	}

	/**
     * 当ClearEditText焦点发生变化的时候，判断里面字符串长度设置清除图标的显示与隐藏
     */
    @Override 
    public void onFocusChange(View v, boolean hasFocus) { 
    	this.hasFoucs = hasFocus;
        if (hasFocus) { 
            setClearIconVisible(getText().length() > 0); 
        } else { 
            setClearIconVisible(false); 
        } 
    } 
 
 
    /**
     * 设置清除图标的显示与隐藏，调用setCompoundDrawables为EditText绘制上去
     * @param visible
     */
    protected void setClearIconVisible(boolean visible) { 
        Drawable right = visible ? mvisiableDrawable : null; 
        setCompoundDrawables(getCompoundDrawables()[0], getCompoundDrawables()[1], right, getCompoundDrawables()[3]); 
    } 
    
    
    /**
     * 当输入框里面内容发生变化的时候回调的方法
     */
    @Override 
    public void onTextChanged(CharSequence s, int start, int count, 
            int after) { 
            	if(hasFoucs){
            		setClearIconVisible(s.length() > 0);
            	}
    } 
 
    @Override 
    public void beforeTextChanged(CharSequence s, int start, int count, 
            int after) { 
         
    } 
 
    @Override 
    public void afterTextChanged(Editable s) { 
         
    } 
    
   
    /**
     * 设置晃动动画
     */
    public void setShakeAnimation(){
    	this.setAnimation(shakeAnimation(5));
    }
    
    
    /**
     * 晃动动画
     * @param counts 1秒钟晃动多少下
     * @return
     */
    public static Animation shakeAnimation(int counts){
    	Animation translateAnimation = new TranslateAnimation(0, 10, 0, 0);
    	translateAnimation.setInterpolator(new CycleInterpolator(counts));
    	translateAnimation.setDuration(1000);
    	return translateAnimation;
    }
 
 
}
