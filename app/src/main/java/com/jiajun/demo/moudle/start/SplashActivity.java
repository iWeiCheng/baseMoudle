package com.jiajun.demo.moudle.start;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.flaviofaria.kenburnsview.KenBurnsView;
import com.jiajun.demo.R;
import com.jiajun.demo.base.BaseActivity;
import com.jiajun.demo.config.Const;
import com.jiajun.demo.model.BaseBean;
import com.jiajun.demo.moudle.account.LoginActivity;
import com.jiajun.demo.moudle.account.entities.LoginBean;
import com.jiajun.demo.moudle.main.MainsActivity;
import com.jiajun.demo.moudle.start.entities.VersionBean;
import com.jiajun.demo.network.BaseObserver;
import com.jiajun.demo.network.Network;
import com.jiajun.demo.update.AppUtils;
import com.jiajun.demo.util.RandomNum;
import com.jiajun.demo.util.SPUtils;
import com.jiajun.demo.util.SignatureUtil;
import com.jiajun.demo.util.ToastUtil;
import com.jiajun.demo.views.CustomAlertDialog;
import com.orhanobut.logger.Logger;
import com.qiyukf.unicorn.api.Unicorn;
import com.qiyukf.unicorn.api.YSFUserInfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

@RuntimePermissions
public class SplashActivity extends BaseActivity {

    private KenBurnsView mKenBurns;
    private ImageView mLogo;
    private TextView welcomeText;
    private String versionName, versionUrl;
    private int versionCode;
    private Subscription mSubscription;
    private boolean isFirstOpen = true;
    private BaseObserver<LoginBean> observer = new BaseObserver<LoginBean>() {

        @Override
        public void onSuccess(LoginBean bean) {
            dismissDialog();
            YSFUserInfo userInfo = new YSFUserInfo();
            userInfo.userId = bean.getUserId();
            userInfo.data = userInfoData(bean.getUserName() + "-" + bean.getCompanyName(), bean.getMobile()).toJSONString();
            Unicorn.setUserInfo(userInfo);
            preferences.edit().putString("user_id", bean.getUserId()).apply();
            Intent intent = new Intent(getContext(), MainsActivity.class);
            getContext().startActivity(intent);
            finish();
        }

        @Override
        public void onError(int code, String message, BaseBean baseBean) {
            dismissDialog();
            ToastUtil.showToast(getContext(), message);
            startLoginActivity();
        }

        @Override
        public void networkError(Throwable e) {
            dismissDialog();
            Logger.e(e.getMessage());
            ToastUtil.showToast(getContext(), getString(R.string.empty_network_error));
            startLoginActivity();
        }
    };
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Intent intent = new Intent(getContext(), LoginActivity.class);
            intent.putExtra("isFirst", "false");
            startActivity(intent);
            finish();
        }
    };

    private BaseObserver<VersionBean> observer_version = new BaseObserver<VersionBean>() {

        @Override
        public void onSuccess(final VersionBean bean) {
            dismissDialog();
            versionUrl = bean.getVersionUrl();
            if (!bean.getVersionNumber().equals(versionName) && bean.getUpdateType() != null && bean.getUpdateType().equals("1")) {//强制更新
                CustomAlertDialog dialog = new CustomAlertDialog(SplashActivity.this, R.layout.dialog_version) {
                    @Override
                    public void onClickCancel() {
                        super.onClickCancel();
                        finish();
                    }

                    @Override
                    public void onClickSubmit() {
                        // super.onClickSubmit();
                        Uri uri = Uri.parse(bean.getVersionUrl());
                        Intent it = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(it);
                    }

                };
                dialog.setDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        finish();
                    }
                });
            } else {
//                if (!bean.getVersionNumber().equals(versionName)) {//版本不一致
//                    CustomAlertDialog dialog = new CustomAlertDialog(SplashActivity.this, R.layout.dialog_version) {
//                        @Override
//                        public void onClickCancel() {
//                            super.onClickCancel();
//                            autoLogin();
//                        }
//
//                        @Override
//                        public void dismiss() {
//                            if (!isExites()) {
//                                if (!bean.getVersionNumber().equals(versionName)) {
//                                    preferences.edit().putString("localVersionCode", versionName).apply();
//                                } else {
//                                    autoLogin();
//                                }
//                            }
//                            super.dismiss();
//                        }
//
//                        @Override
//                        public void onClickSubmit() {
//                            // super.onClickSubmit();
//                            Uri uri = Uri.parse(bean.getVersionUrl());
//                            Intent it = new Intent(Intent.ACTION_VIEW, uri);
//                            startActivity(it);
//                        }
//                    };
//                } else {
                    new Handler().postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            autoLogin();
                        }
                    }, 1000);
                    SPUtils.put(SplashActivity.this, Const.FIRST_OPEN, false);
//                }
            }
            Logger.e(bean.toString());
        }

        @Override
        public void onError(int code, String message, BaseBean baseBean) {
            dismissDialog();
            Logger.e("error:" + message);
            handler.sendEmptyMessageDelayed(1, 1500);
        }

        @Override
        public void networkError(Throwable e) {
            Logger.e(e.getMessage());
            handler.sendEmptyMessageDelayed(1, 1500);
        }
    };

    private void autoLogin() {
        if (preferences.getBoolean("isRemember", false)) {
            showProgressDialog();
            String phone = preferences.getString("phone", "");
            String password = preferences.getString("pwd", "");
            String str_random = RandomNum.getrandom();
            Map<String, String> map = new HashMap<>();
            map.put("method", "login");
            map.put("username", phone);
            map.put("password", password);
            map.put("versionName", getResources().getString(R.string.versionName));
            map.put("random", str_random);
            String str_signature = SignatureUtil.getSignature(map);
            map.put("signature", str_signature);
            mSubscription = Network.login().login(Network.WORK_URL, map)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(observer);
        } else {
            handler.sendEmptyMessageDelayed(1, 1500);
        }
    }


    @Override
    protected void loadLayout() {
        Window window = getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION,
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        // 加上这句设置为全屏 不加则只隐藏title
        setContentView(R.layout.activity_splash);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    @Override
    public void initPresenter() {

    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        super.processLogic(savedInstanceState);
        versionCode = AppUtils.getVersionCode(this);
        versionName = AppUtils.getVersionName(this);
        unsubscribe();
        // 判断是否是第一次开启应用
        isFirstOpen = (boolean) SPUtils.get(this, Const.FIRST_OPEN, false);
        Logger.e("isFirstOpen:" + isFirstOpen);
        SplashActivityPermissionsDispatcher.startWelcomeGuideActivityWithCheck(SplashActivity.this);
//        if (isFirstOpen) {
//            SplashActivityPermissionsDispatcher.startWelcomeGuideActivityWithCheck(this);
//        } else {
//            initContentView();
//            new Handler().postDelayed(new Runnable() {
//
//                @Override
//                public void run() {
//                    startLoginActivity();
//                }
//            }, 1000);
////        initContentView();
//            SPUtils.put(this, Const.FIRST_OPEN, false);
//        }
    }

    @Override
    protected int getTranslucentColor() {
        return R.color.transparent;
    }

    private void unsubscribe() {
        if (mSubscription != null && !mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
    }

    private void getVersion() {
        showProgressDialog();
        String str_random = RandomNum.getrandom();
        Map<String, String> map = new HashMap<>();
        map.put("method", "getVersion");
        map.put("type", "1");
        map.put("versionName", getResources().getString(R.string.versionName));
        map.put("random", str_random);
        String str_signature = SignatureUtil.getSignature(map);
        map.put("signature", str_signature);

        mSubscription = Network.getVersionApi().getVersion(Network.WORK_URL, map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer_version);

    }

    //=======================动态权限的申请===========================================================<
    @NeedsPermission({
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE})
    public void startWelcomeGuideActivity() {
        getVersion();
    }

    /**
     * 为什么要获取这个权限给用户的说明
     *
     * @param request
     */
    @OnShowRationale({
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void showRationaleForCamera(final PermissionRequest request) {
        new AlertDialog.Builder(this)
                .setMessage("有部分权限需要你的授权")
//                .setPositiveButton(R.string.imtrue, (dialog, button) -> request.proceed())
//                .setNegativeButton(R.string.cancel, (dialog, button) -> request.cancel())
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        request.proceed();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        request.cancel();
                    }
                })
                .show()
        ;
    }

    /**
     * 如果用户不授予权限调用的方法
     */
    @OnPermissionDenied({
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void showDeniedForCamera() {
        showPermissionDenied();
    }

    public void showPermissionDenied() {
        new AlertDialog.Builder(this)
                .setTitle("权限说明")
                .setCancelable(false)
                .setMessage("本应用需要部分必要的权限，如果不授予可能会影响正常使用！")
                .setNegativeButton("退出应用", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setPositiveButton("赋予权限", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SplashActivityPermissionsDispatcher.startWelcomeGuideActivityWithCheck(SplashActivity.this);
                    }
                })
                .create().show();
    }

    /**
     * 如果用户选择了让设备“不再询问”，而调用的方法
     */
    @OnNeverAskAgain({
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void showNeverAskForCamera() {
        Toast.makeText(this, "不再询问授权权限！", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        SplashActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

//=======================动态权限的申请===========================================================>


//    public void initContentView() {
//        setContentView(R.layout.activity_splash);
//        //设置状态栏透明
//        StateBarTranslucentUtils.setStateBarTranslucent(this);
//        mKenBurns = (KenBurnsView) findViewById(R.id.ken_burns_images);
//        mLogo = ((ImageView) findViewById(R.id.logo_splash));
//        welcomeText = ((TextView) findViewById(R.id.welcome_text));
//
//        Glide.with(this)
//                .load(R.drawable.welcometoqbox)
//                .into(mKenBurns);
//
////        animation2();
////        animation3();
////        saveTestData();
//    }

//    private void saveTestData() {
//        TestDao testDao = new TestDao(getApplicationContext());
//        testDao.deleteAllTestEntity();
//        String[] strs = getResources().getStringArray(R.array.arrays_address);
//        for (int i = 0; i < 10; i++) {
//            testDao.insertTestEntities(new TestEntity(i, strs[i]));
//        }
//        List<TestEntity> testEntities = testDao.queryTestEntityList();
//        Logger.e(testEntities.toString());
//    }


    Animation anim;

    private void animation2() {
        mLogo.setAlpha(1.0F);
        anim = AnimationUtils.loadAnimation(this, R.anim.translate_top_to_center);
        mLogo.startAnimation(anim);
    }

    ObjectAnimator alphaAnimation;

    private void animation3() {
        alphaAnimation = ObjectAnimator.ofFloat(welcomeText, "alpha", 0.0F, 1.0F);
        alphaAnimation.setStartDelay(1700);
        alphaAnimation.setDuration(500);
        alphaAnimation.start();
    }

    public void startLoginActivity() {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    @Override
    protected void onPause() {
        super.onPause();
        if (mKenBurns != null) {
            mKenBurns.pause();
        }
        if (alphaAnimation != null) {
            alphaAnimation.cancel();
        }
        if (anim != null) {
            anim.cancel();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mKenBurns != null) {
            mKenBurns.resume();
        }
    }

    private JSONArray userInfoData(String name, String mobile) {
        JSONArray array = new JSONArray();
        array.add(userInfoDataItem("real_name", name, false, -1, null, null)); // name
        array.add(userInfoDataItem("mobile_phone", mobile, false, -1, null, null)); // mobile
        return array;
    }

    private JSONObject userInfoDataItem(String key, Object value, boolean hidden, int index, String label, String href) {
        JSONObject item = new JSONObject();
        try {
            item.put("key", key);
            item.put("value", value);
            if (hidden) {
                item.put("hidden", true);
            }
            if (index >= 0) {
                item.put("index", index);
            }
            if (!TextUtils.isEmpty(label)) {
                item.put("label", label);
            }
            if (!TextUtils.isEmpty(href)) {
                item.put("href", href);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return item;
    }

    private boolean isExites() {
        if (!isTaskRoot()) {
            finish();
            return true;
        }
        return false;
    }
}
