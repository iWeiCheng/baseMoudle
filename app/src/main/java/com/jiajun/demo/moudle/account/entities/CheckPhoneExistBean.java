package com.jiajun.demo.moudle.account.entities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 检查手机号是否存在
 * Created by cai.jia on 2016/7/25 0025.
 */

public class CheckPhoneExistBean implements Parcelable {

    private int isExist ;

    public int getIsExist() {
        return isExist;
    }

    public void setIsExist(int isExist) {
        this.isExist = isExist;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.isExist);
    }

    public CheckPhoneExistBean() {
    }

    protected CheckPhoneExistBean(Parcel in) {
        this.isExist = in.readInt();
    }

    public static final Creator<CheckPhoneExistBean> CREATOR = new Creator<CheckPhoneExistBean>() {
        @Override
        public CheckPhoneExistBean createFromParcel(Parcel source) {
            return new CheckPhoneExistBean(source);
        }

        @Override
        public CheckPhoneExistBean[] newArray(int size) {
            return new CheckPhoneExistBean[size];
        }
    };
}
