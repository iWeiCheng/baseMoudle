package com.jiajun.demo.moudle.start.welcome;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.jiajun.demo.R;
import com.jiajun.demo.config.Const;
import com.jiajun.demo.util.SPUtils;
import com.jiajun.demo.util.StateBarTranslucentUtils;

/**
 * 欢迎页面
 * Created by dan on 2017/8/16.
 */

public class WelcomeActivity extends AppCompatActivity implements View.OnClickListener {


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 隐藏标题栏
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_welcome);
        //设置状态栏透明
        StateBarTranslucentUtils.setStateBarTranslucent(this);

        findViewById(R.id.bRetry).setOnClickListener(this);
        findViewById(R.id.root).setOnClickListener(this);

        if (savedInstanceState == null) {
//            replaceTutorialFragment();
        }

//        saveCategoryToDB();
//        saveFunctionToDB();
//        saveTestData();
    }

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

    /**
     * 当第一次打开App时，将功能类别存储到本地数据库
     */
//    private void saveFunctionToDB() {
//        Function function = null;
//        try {
//            function = (new Gson()).fromJson(StreamUtils.get(getApplicationContext(), R.raw.function), Function.class);
//        } catch (Exception e) {
//            Logger.e("读取raw中的function.json文件异常：" + e.getMessage());
//        }
//        if (function != null && function.getFunction() != null && function.getFunction().size() != 0) {
//            List<FunctionBean> functionBeanList = function.getFunction();
//            FunctionDao functionDao = new FunctionDao(getApplicationContext());
//            functionDao.insertFunctionBeanList(functionBeanList);
//        }


//    }

    /**
     * 第一次打开App时，将news的所有类别保存到本地数据库
     */
//    private void saveCategoryToDB() {
////        DBManager dbManager = DBManager.getInstance(this);
//        CategoryDao categoryDao = new CategoryDao(getApplicationContext());
//        categoryDao.deleteAllCategory();
//        categoryDao.insertCategoryList(new CategoryManager(this).getAllCategory());
//    }

    public static void start(Context context) {
        SPUtils.put(context, Const.FIRST_OPEN, true);
        context.startActivity(new Intent(context, WelcomeActivity.class));
    }

//    public void replaceTutorialFragment() {
//        getSupportFragmentManager().beginTransaction()
//                .replace(R.id.container_welcome, new CustomTutorialSupportFragment())
//                .commit();
//    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.root:
//                Intent intent = new Intent(this, PersonalActivity.class);
//                startActivity(intent);
                break;
            case R.id.bRetry:
//                replaceTutorialFragment();
                break;
        }
    }
}
