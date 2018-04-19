package com.jiajun.demo.views;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.text.SpannableStringBuilder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.TextView;

import com.jiajun.demo.R;
import com.jiajun.demo.util.DeviceUtil;


public class CustomAlertDialog {
	Context context;
	android.app.AlertDialog ad;
	private int contentViewId;
	private TextView cancel, submit, msg_tv,msg_title;
	private View lineView;
	private ConfirmListener confirmListener ;
	private ListView listView;
	/**
	 * 自定义dialog布局
	 * 
	 * @param context
	 * @param contentViewId
	 */
	public CustomAlertDialog(Context context, int contentViewId) {
		// TODO Auto-generated constructor stub
		this.contentViewId = contentViewId;
		this.context = context;
		ad = new android.app.AlertDialog.Builder(context).create();
		ad.setCanceledOnTouchOutside(false);
		ad.show();
		// 关键在下面的两行,使用window.setContentView,替换整个对话框窗口的布局
		Window window = ad.getWindow();
		WindowManager.LayoutParams p = window.getAttributes();
		if(p.height > DeviceUtil.getScreenHeight(context) * 0.85f) {
			p.height = (int) (DeviceUtil.getScreenHeight(context) * 0.85f);
			window.setAttributes(p);
		}
		window.setContentView(contentViewId);
		cancel = (TextView) window.findViewById(R.id.dialog_cancel);
		submit = (TextView) window.findViewById(R.id.dialog_submit);
		msg_tv = (TextView) window.findViewById(R.id.dialog_msg);
		msg_title = (TextView) window.findViewById(R.id.dialog_title);
		lineView = window.findViewById(R.id.dialog_line);
		if (cancel != null) {
			cancel.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					onClickCancel();
				}
			});
		}
		if (submit != null) {
			submit.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					onClickSubmit();
				}
			});
		}
		initCustom(window,contentViewId);
	}


	public void setTextColor(){
		msg_tv.setTextColor(Color.parseColor("#333333"));
	}

	public void setTextPadding(){
		msg_tv.setPadding(DeviceUtil.dp2px(context,15),DeviceUtil.dp2px(context,8),DeviceUtil.dp2px(context,15),DeviceUtil.dp2px(context,15));
	}
	public void initCustom(Window window,int contentViewId2) {

	}

	/**
	 * 点击取消调用此方法
	 */
	public void onClickCancel() {
		dismiss();
	}

	/**
	 * 点击确定调用此方法
	 */
	public void onClickSubmit() {
		if(confirmListener != null ){
			confirmListener.onConfirm();
		}
		dismiss();
	}

	/**
	 * 获取dialog中的布局
	 * 
	 * @return
	 */
	public View getContentView() {
		return LayoutInflater.from(context).inflate(contentViewId, null);
	}

	public void dismiss() {
		ad.dismiss();
	}

	public void setMsg(String msg) {
		msg_tv.setText(msg);
	}

	public void setSpanMsg(SpannableStringBuilder span) {
		msg_tv.setText(span);
	}

	public void setPositiveMsg(String msg) {
		submit.setVisibility(View.VISIBLE);
		if (lineView != null) {
			lineView.setVisibility(View.VISIBLE);
		}
		submit.setText(msg);
	}

	public void setNegetiveMsg(String msg) {
		cancel.setText(msg);
	}

	public void setDialogTitle(String title){
		if(msg_title != null ){
			msg_title.setText(title);
		}
	}

	public void setDismissListener( DialogInterface.OnDismissListener listener){
		ad.setOnDismissListener(listener);
	}

	public interface ConfirmListener {
		public  void  onConfirm();
	};

	public void setConfirmListener(ConfirmListener listener ){
		this.confirmListener = listener ;
	}

}
