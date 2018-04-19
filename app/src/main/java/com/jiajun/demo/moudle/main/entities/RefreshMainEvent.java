package com.jiajun.demo.moudle.main.entities;

/**
 * Created by dan on 2018/1/6/006.
 */

public class RefreshMainEvent {
    int index ;

    public RefreshMainEvent(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
