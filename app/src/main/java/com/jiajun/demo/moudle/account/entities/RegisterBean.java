package com.jiajun.demo.moudle.account.entities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 注册返回结果
 * Created by cai.jia on 2016/7/21 0021.
 */

public class RegisterBean implements Parcelable {


    /**
     * userId : 32
     * nickname : 小写
     * token : 2ec412a5f9811d559000cef0d94c0ce9
     */

    private int userId;
    private String nickname;
    private String token;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.userId);
        dest.writeString(this.nickname);
        dest.writeString(this.token);
    }

    public RegisterBean() {
    }

    protected RegisterBean(Parcel in) {
        this.userId = in.readInt();
        this.nickname = in.readString();
        this.token = in.readString();
    }

    public static final Creator<RegisterBean> CREATOR = new Creator<RegisterBean>() {
        @Override
        public RegisterBean createFromParcel(Parcel source) {
            return new RegisterBean(source);
        }

        @Override
        public RegisterBean[] newArray(int size) {
            return new RegisterBean[size];
        }
    };
}
