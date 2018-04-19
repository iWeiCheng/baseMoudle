package com.jiajun.demo.moudle.main;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.jiajun.demo.R;
import com.jiajun.demo.app.BaseApplication;
import com.jiajun.demo.base.BaseActivity;
import com.jiajun.demo.config.Const;
import com.jiajun.demo.util.inputmethodmanager_leak.IMMLeaks;
import com.jiajun.demo.widget.TabBar_Mains;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;

/**
 * 主页
 */

public class MainsActivity extends BaseActivity {


//    @BindView(R.id.framelayout_mains)
//    FrameLayout framelayoutMains;
//    @BindView(R.id.recommend_mains)
//    TabBar_Mains recommendMains;
//    @BindView(R.id.cityfinder_mains)
//    TabBar_Mains cityfinderMains;
//    @BindView(R.id.findtravel_mains)
//    TabBar_Mains findtravelMains;
//    @BindView(R.id.me_mains)
//    TabBar_Mains meMains;





    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void loadLayout() {
        BaseApplication.setMainActivity(this);
        setContentView(R.layout.activity_mains);
        Window window = getWindow();
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //使得布局延伸到状态栏和导航栏区域
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);

//            //透明状态栏/导航栏
//            window.setStatusBarColor(Color.TRANSPARENT);
//            //这样的效果跟上述的主题设置效果类似
        }
    }

    @Override
    protected int getTranslucentColor() {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            return R.color.transparent;
        } else {
            return R.color.textGreen;
        }
    }

    @Override
    public void initialize(Bundle savedInstanceState) {

        //订阅事件
        EventBus.getDefault().register(this);
    }

    @Override
    public void initPresenter() {

    }

//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void ONLogoutEvent(LogoutBean logoutBean) {
//        finish();
//    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        for (Fragment fragment :
                getBaseFragmentManager().getFragments()) {
            getFragmentTransaction().remove(fragment);
        }
        super.onDestroy();
        BaseApplication.setMainActivity(null);
        IMMLeaks.fixFocusedViewLeak(getApplication());//解决 Android 输入法造成的内存泄漏问题。
    }


    /**
     * 监听用户按返回键
     *
     * @param keyCode
     * @param event
     * @return
     */
    private boolean mIsExit;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == android.view.KeyEvent.KEYCODE_BACK) {
            if (mIsExit) {
                this.finish();
            } else {
                Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
                mIsExit = true;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mIsExit = false;
                    }
                }, 2000);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    /**
     * 用于优雅的退出程序(当从其他地方退出应用时会先返回到此页面再执行退出)
     *
     * @param intent
     */
    @Override
    protected void onNewIntent(android.content.Intent intent) {
        super.onNewIntent(intent);
        if (intent != null) {
            boolean isExit = intent.getBooleanExtra(Const.TAG_EXIT, false);
            if (isExit) {
                finish();
            }
        }
    }
}
