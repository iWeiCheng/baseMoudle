package com.jiajun.demo.moudle.account;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jiajun.demo.R;
import com.jiajun.demo.base.BaseActivity;
import com.jiajun.demo.model.BaseBean;
import com.jiajun.demo.network.BaseObserver;
import com.jiajun.demo.network.Network;
import com.jiajun.demo.util.CommonUtil;
import com.jiajun.demo.util.RandomNum;
import com.jiajun.demo.util.SignatureUtil;
import com.jiajun.demo.util.ToastUtil;
import com.jiajun.demo.views.ClearEditText;
import com.jiajun.demo.views.PassWordEditText;
import com.orhanobut.logger.Logger;

import java.util.HashMap;
import java.util.Map;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * Created by hgj on 2016/7/22 0022.
 * 忘记密码
 */
public class ForgetPasswordActivity extends BaseActivity implements View.OnClickListener {


    private TextView titleCenterTv;
    private TextView titleLeftIv;
    private ClearEditText phoneEt;
    private EditText codeEt;
    private TextView codeBtn;
    private Button submitBtn;
    private PassWordEditText password1Edt;
    private PassWordEditText password2Edt;

    private String phone;
    private String type;
    private String password1;
    private String password2;
    private String mobielVerify; // 验证码
    private TimeCount time;
    private Subscription mSubscription;


    private BaseObserver<Object> observer = new BaseObserver<Object>() {

        @Override
        public void onSuccess(Object bean) {
            dismissDialog();
            Logger.e("success:" + bean.toString());
        }

        @Override
        public void onError(int code, String message, BaseBean baseBean) {
            dismissDialog();
            Logger.e("error:" + message);
        }

        @Override
        public void networkError(Throwable e) {
            dismissDialog();
            Logger.e("service_error");
            ToastUtil.showToast(getContext(),"网络错误");

        }
    };

    private BaseObserver<Object> observer_submit = new BaseObserver<Object>() {

        @Override
        public void onSuccess(Object bean) {
            dismissDialog();
            Logger.e("success:" + bean.toString());
            ToastUtil.showToast(getContext(),"设置成功");
            finish();
        }

        @Override
        public void onError(int code, String message, BaseBean baseBean) {
            dismissDialog();
            Logger.e("error:" + message);
            ToastUtil.showToast(getContext(),message);

        }

        @Override
        public void networkError(Throwable e) {
            dismissDialog();
            Logger.e("service_error");
        }
    };

    @Override
    protected void loadLayout() {
        setContentView(R.layout.account_fogetpassword_activity);
    }

    @Override
    public void initPresenter() {
        unsubscribe();
    }

    private void unsubscribe() {
        if (mSubscription != null && !mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
    }

    @Override
    protected void initialize(Bundle savedInstanceState) {
        super.initialize(savedInstanceState);
        titleCenterTv = (TextView) findViewById(R.id.titleCeneter_textView);
        titleLeftIv = (TextView) findViewById(R.id.titleLeft_textView);
        phoneEt = (ClearEditText) findViewById(R.id.forget_password_phoneNum);
        codeEt = (EditText) findViewById(R.id.verification_code);
        codeBtn = (TextView) findViewById(R.id.get_forget_password_Code);
        submitBtn = (Button) findViewById(R.id.forgetPwd_submit);
        password1Edt = (PassWordEditText) findViewById(R.id.password1);
        password2Edt = (PassWordEditText) findViewById(R.id.password2);
//        String digists = "abcdefghigklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ~!@#$%^&*().;'+-=<>[],?0123456789";
//        password1Edt.setKeyListener(DigitsKeyListener.getInstance(digists));
//        password2Edt.setKeyListener(DigitsKeyListener.getInstance(digists));

    }

    @Override
    protected void setListener(Bundle savedInstanceState) {
        super.setListener(savedInstanceState);
        titleLeftIv.setOnClickListener(this);
        codeBtn.setOnClickListener(this);
        submitBtn.setOnClickListener(this);
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        super.processLogic(savedInstanceState);
        titleLeftIv.setVisibility(View.VISIBLE);
        titleCenterTv.setText(R.string.findPassword);
        time = new TimeCount(60000, 1000);
    }

    @Override
    public void onClick(View v) {
        if (v == titleLeftIv) {
            finish();
        } else if (v == submitBtn) {
            phone = phoneEt.getText().toString();
            mobielVerify = codeEt.getText().toString();
            password1 = password1Edt.getText().toString();
            password2 = password2Edt.getText().toString();
            if (!password1.equals(password2)) {
                ToastUtil.showToast(this, "2次输入的密码不一致");
                return;
            }
            submit();
        } else if (v == codeBtn) {
            phone = phoneEt.getText().toString();
            if(CommonUtil.isPhoneNumber(phone)) {
                type = "get";
                getCode();
            }else{
                ToastUtil.showToast(this,"请输入正确的手机号");
            }
        }
    }

    private void submit() {
        showProgressDialog();
        Map<String, String> params = new HashMap<>();
        params.put("method", "setPassword");
        params.put("checkCode", mobielVerify);
        params.put("mobile", phone);
        params.put("pwd", password1);
        String str_random = RandomNum.getrandom();
        params.put("random", str_random);
        String str_signature = SignatureUtil.getSignature(params);
        params.put("signature", str_signature);
        mSubscription = Network.resetPwd().resetPwd(Network.WORK_URL,params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer_submit);
    }


    // 获取验证码
    private void getCode() {
        Map<String, String> map = new HashMap<>();
        map.put("method", "getVerificationCode");
        map.put("mobile", phone);
        String str_random = RandomNum.getrandom();
        map.put("random", str_random);
        String str_signature = SignatureUtil.getSignature(map);
        map.put("signature", str_signature);
        mSubscription = Network.getCode().getCode(Network.WORK_URL,map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
        time.start();
    }


    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);// 参数依次为总时长,和计时的时间间隔
        }

        @Override
        public void onFinish() {// 计时完毕时触发
            codeBtn.setText("获取验证码");
            codeBtn.setTextColor(getResources().getColor(R.color.white));
//            codeBtn.setBackgroundResource(R.drawable.shape_login);
            codeBtn.setClickable(true);
        }

        @Override
        public void onTick(long millisUntilFinished) {// 计时过程显示
            codeBtn.setClickable(false);
            codeBtn.setTextColor(getResources().getColor(R.color.text_login_gray));
            codeBtn.setText("(" + millisUntilFinished / 1000 + ")重新获取");
        }
    }
}
