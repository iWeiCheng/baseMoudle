package com.jiajun.demo.moudle.account;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jiajun.demo.R;
import com.jiajun.demo.base.BaseActivity;
import com.jiajun.demo.model.BaseBean;
import com.jiajun.demo.moudle.account.entities.OrgInfo;
import com.jiajun.demo.network.BaseObserver;
import com.jiajun.demo.network.Network;
import com.jiajun.demo.util.CommonUtil;
import com.jiajun.demo.util.RandomNum;
import com.jiajun.demo.util.SharedPreferenceUtil;
import com.jiajun.demo.util.SignatureUtil;
import com.jiajun.demo.util.ToastUtil;
import com.jiajun.demo.views.ClearEditText;
import com.jiajun.demo.views.PassWordEditText;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * Created by djj on 2016/10/26 0021.
 * 注册
 */
public class RegisterActivity extends BaseActivity implements View.OnClickListener, SelectFragment.OnClickSubmitListener {


    @BindView(R.id.titleLeft_textView)
    TextView back;
    @BindView(R.id.titleCeneter_textView)
    TextView title;

    ClearEditText phoneNum;
    @BindView(R.id.verification_code)
    ClearEditText verificationCode;
    @BindView(R.id.get_forget_password_Code)
    TextView getVerificationCode;


    private SharedPreferences preferences;
    private TimeCount time;
    private String phone;
    private String password1, password2;
    private String isRecommend = "";
    private String groudId = "", companyId = "";
    private SelectFragment selectFragment_group;
    private SelectFragment selectFragment_company;
    private ArrayList<OrgInfo.OrgInfoBean> grouds;
    private int index = -2;//选择集团的位置
    private Subscription mSubscription;

    private BaseObserver<OrgInfo> observer = new BaseObserver<OrgInfo>() {

        @Override
        public void onSuccess(OrgInfo bean) {
            dismissDialog();
            grouds = (ArrayList<OrgInfo.OrgInfoBean>) bean.getOrgInfo();
            if(grouds.size()==0){
                ToastUtil.showToast(getContext(),"无相应公司可选");
            }
            if (selectFragment_group == null) {
                selectFragment_group = SelectFragment.getInstance(grouds, -1);
                selectFragment_group.setClickItemListener(RegisterActivity.this);
            }
            selectFragment_group.show(getSupportFragmentManager(), "");
        }

        @Override
        public void onError(int code, String message, BaseBean baseBean) {
            dismissDialog();
            Logger.e("error:" + message);
            ToastUtil.showToast(RegisterActivity.this,message);
        }

        @Override
        public void networkError(Throwable e) {
            dismissDialog();
            Logger.e("service_error");
            ToastUtil.showToast(RegisterActivity.this,"网络错误");
        }
    };


    private BaseObserver<Object> observer_code = new BaseObserver<Object>() {

        @Override
        public void onSuccess(Object bean) {
            dismissDialog();
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
            ToastUtil.showToast(RegisterActivity.this,"网络错误");
        }
    };


    private BaseObserver<Object> observer_register = new BaseObserver<Object>() {

        @Override
        public void onSuccess(Object bean) {
            dismissDialog();
            ToastUtil.showToast(getContext(), "注册成功！请登录");
            finish();
        }

        @Override
        public void onError(int code, String message, BaseBean baseBean) {
            dismissDialog();
            Logger.e("error:" + message);
            ToastUtil.showToast(RegisterActivity.this,message);
        }

        @Override
        public void networkError(Throwable e) {
            dismissDialog();
            Logger.e("service_error");
            ToastUtil.showToast(RegisterActivity.this,"网络错误");
        }
    };


    @Override
    protected void loadLayout() {
        Window window = getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION,
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        // 加上这句设置为全屏 不加则只隐藏title
//        setContentView(R.layout.activity);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    private void unsubscribe() {
        if (mSubscription != null && !mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
    }

    @Override
    public void initPresenter() {
        unsubscribe();
    }

    @Override
    protected int getTranslucentColor() {
        return R.color.transparent;
    }

    @Override
    protected void initialize(Bundle savedInstanceState) {
        super.initialize(savedInstanceState);
        preferences = SharedPreferenceUtil.getSharedPreferences(this);
        title.setText(getString(R.string.register));
        back.setVisibility(View.VISIBLE);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
//        String digists = "0123456789abcdefghigklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ~!@#$%^&*().;'+-=<>[],?";
//        pwd.setKeyListener(DigitsKeyListener.getInstance(digists));
//        confirmPwd.setKeyListener(DigitsKeyListener.getInstance(digists));
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        time = new TimeCount(60000, 1000);// 构造CountDownTimer对象

    }

    private void getOrgInfo() {
        showProgressDialog();
        String str_random = RandomNum.getrandom();
        Map<String, String> map = new HashMap<>();
        map.put("method", "getOrgInfo");
        map.put("versionName", getResources().getString(R.string.versionName));
        map.put("random", str_random);
        String str_signature = SignatureUtil.getSignature(map);
        map.put("signature", str_signature);
        mSubscription = Network.getOrgInfoApi().getOrgInfo(Network.WORK_URL,map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    @Override
    protected void setListener(Bundle savedInstanceState) {
        super.setListener(savedInstanceState);
        back.setOnClickListener(this);
//        register.setOnClickListener(this);
        getVerificationCode.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        phone = phoneNum.getText().toString();
//        if (v == register) {
//            // 注册操作
//            if(name.getText().toString().length()==0){
//                ToastUtil.showToast(this, "请输入姓名");
//                return;
//            }
//            if(phoneNum.getText().toString().length()==0){
//                ToastUtil.showToast(this, "请输入手机号");
//                return;
//            }
//            if(verificationCode.getText().toString().length()==0){
//                ToastUtil.showToast(this, "请输入验证码");
//                return;
//            }
//            if(pwd.getText().toString().length()==0){
//                ToastUtil.showToast(this, "请输入密码");
//                return;
//            }
//            password1 = pwd.getText().toString();
//            password2 = confirmPwd.getText().toString();
//            if (!password1.equals(password2)) {
//                ToastUtil.showToast(this, "2次输入的密码不一致");
//                return;
//            }
//            if (isRecommend.equals("")) {
//                ToastUtil.showToast(this, "请选择是否有推荐人");
//                return;
//            }
//            register();
//        } else if (v == getVerificationCode) {
//            if(CommonUtil.isPhoneNumber(phone)) {
//                getCode();
//            }else{
//                ToastUtil.showToast(this,"请输入正确的手机号");
//                return;
//            }
//        } else if (v == back) {
//            finish();
//        }
    }

    private void register() {
        showProgressDialog();
        Map<String, String> map = new HashMap<>();
        map.put("method", "register");
//        map.put("name", name.getText().toString());
        map.put("mobile", phone);
        map.put("pwd", password1);
        map.put("isRecommend", isRecommend);
        map.put("checkCode", verificationCode.getText().toString());
        String str_random = RandomNum.getrandom();
        map.put("random", str_random);
        String str_signature = SignatureUtil.getSignature(map);
        map.put("signature", str_signature);
        map.put("groudId", groudId);
        map.put("companyId", companyId);
//        map.put("recommendMobile", recommendNum.getText().toString());
        mSubscription = Network.RegisterApi().register(Network.WORK_URL,map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer_register);
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
                .subscribe(observer_code);
        time.start();
    }

    @Override
    public void onClickItem(int index, int position) {
        if (index == -1) {
            if (this.index == position) {
                return;
            }
            this.index = position;
            groudId = grouds.get(position).getGroudId();
            selectFragment_group.dismiss();
            companyId = "";
        } else {
            companyId = grouds.get(index).getSubOrg().get(position).getCompanyId();
            selectFragment_company.dismiss();
        }
    }


    /**
     * 倒计时类~
     *
     * @author Administrator
     */
    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);// 参数依次为总时长,和计时的时间间隔
        }

        @Override
        public void onFinish() {// 计时完毕时触发
            getVerificationCode.setText("获取验证码");
            getVerificationCode.setTextColor(getResources().getColor(R.color.white));
            getVerificationCode.setClickable(true);
        }

        @Override
        public void onTick(long millisUntilFinished) {// 计时过程显示
            getVerificationCode.setClickable(false);
            getVerificationCode.setTextColor(getResources().getColor(R.color.text_login_gray));
            getVerificationCode.setText(String.format("(%d)重新获取", millisUntilFinished / 1000));
        }
    }
}
