package com.jiajun.demo.util;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;

import java.util.List;

/**
 * 管理Fragment的显示和隐藏
 * Created by cai.jia on 2015/7/29.
 */
public class FragmentService {

    private FragmentManager manager;

    private int mEnter, mExit;

    public FragmentService(FragmentManager manager) {
        this.manager = manager;
    }

    public FragmentService setCustomAnimations(int enter, int exit) {
        this.mEnter = enter;
        this.mExit = exit;
        return this;
    }

    public void addOrShow(int containerViewId, Fragment f, boolean addToBackStack, String tag) {
        if (manager == null || f == null) {
            return;
        }
        hideAll();
        if (f.isAdded()) {
            show(f);
        } else {
            add(containerViewId, f, addToBackStack, tag);
        }
    }

    private void add(int containerViewId, Fragment f, boolean addToBackStack, String tag) {
        if (f != null) {
            FragmentTransaction transaction = getFragmentTransaction();
            String uniqueTag = uniqueTag(containerViewId, f, tag);
            if (addToBackStack) {
                transaction.addToBackStack(tag);
            }
            transaction.add(containerViewId, f, uniqueTag).commit();
        }
    }

    private FragmentTransaction getFragmentTransaction() {
        return manager.beginTransaction().setCustomAnimations(mEnter, mExit);
    }

    public void remove(Fragment f) {
        if (f != null) {
            FragmentTransaction transaction = getFragmentTransaction();
            transaction.remove(f).commit();
        }
    }

    public void show(Fragment f) {
        if (f != null) {
            FragmentTransaction transaction = getFragmentTransaction();
            transaction.show(f).commit();
        }
    }

    public void hide(Fragment f) {
        if (f != null) {
            FragmentTransaction transaction = getFragmentTransaction();
            transaction.hide(f).commit();
        }
    }

    public void hideAll() {
        List<Fragment> fragments = manager.getFragments();
        if (fragments != null && !fragments.isEmpty()) {
            for (Fragment f : fragments) {
                hide(f);
            }
        }
    }

    /**
     * 每个容器里面对应的唯一tag
     *
     * @param containerViewId 容器id
     * @param f               页面
     * @param tag             传进来的tag
     * @return
     */
    private String uniqueTag(int containerViewId, Fragment f, String tag) {
        return containerViewId + f.getClass().getName() + (TextUtils.isEmpty(tag) ? "" : tag);
    }
}
