package com.jiajun.demo.moudle.start;




public interface SplashInteractor {

    public interface OnEnterIntoFinishListener{
        void isFirstOpen();

        void isNotFirstOpen();

        void showContentView();

    }

    void enterInto(boolean isFirstOpen, OnEnterIntoFinishListener listener);
}
