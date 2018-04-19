package com.jiajun.demo.moudle.account.entities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 登录返回结果
 * Created by cai.jia on 2016/7/21 0021.
 */

public class VerificationCodeBean implements Parcelable {

    private String checkCode;

    public String getCheckCode() {
        return checkCode;
    }

    public void setCheckCode(String checkCode) {
        this.checkCode = checkCode;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.checkCode);
    }

    public VerificationCodeBean() {
    }

    protected VerificationCodeBean(Parcel in) {
        this.checkCode = in.readString();
    }

    public static final Creator<VerificationCodeBean> CREATOR = new Creator<VerificationCodeBean>() {
        @Override
        public VerificationCodeBean createFromParcel(Parcel source) {
            return new VerificationCodeBean(source);
        }

        @Override
        public VerificationCodeBean[] newArray(int size) {
            return new VerificationCodeBean[size];
        }
    };
}
