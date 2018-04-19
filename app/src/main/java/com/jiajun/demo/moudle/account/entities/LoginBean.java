package com.jiajun.demo.moudle.account.entities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 登录返回结果
 * Created by cai.jia on 2016/7/21 0021.
 */

public class LoginBean implements Parcelable {


    private String userId;
    private String mobile;
    private String userName;
    private String companyName;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.userId);
        dest.writeString(this.mobile);
        dest.writeString(this.userName);
        dest.writeString(this.companyName);
    }

    public LoginBean() {
    }

    protected LoginBean(Parcel in) {
        this.userId = in.readString();
        this.mobile = in.readString();
        this.userName = in.readString();
        this.companyName = in.readString();
    }

    public static final Creator<LoginBean> CREATOR = new Creator<LoginBean>() {
        @Override
        public LoginBean createFromParcel(Parcel source) {
            return new LoginBean(source);
        }

        @Override
        public LoginBean[] newArray(int size) {
            return new LoginBean[size];
        }
    };
}
