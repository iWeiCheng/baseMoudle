package com.jiajun.demo.moudle.account.entities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by danjj on 2016/12/9.
 */
public class VersionBean implements Parcelable {
    String versionNumber;
    String versionUrl;

    public String getVersionNumber() {
        return versionNumber;
    }

    public void setVersionNumber(String versionNumber) {
        this.versionNumber = versionNumber;
    }

    public String getVersionUrl() {
        return versionUrl;
    }

    public void setVersionUrl(String versionUrl) {
        this.versionUrl = versionUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.versionNumber);
        dest.writeString(this.versionUrl);
    }

    public VersionBean() {
    }

    protected VersionBean(Parcel in) {
        this.versionNumber = in.readString();
        this.versionUrl = in.readString();
    }

    public static final Creator<VersionBean> CREATOR = new Creator<VersionBean>() {
        @Override
        public VersionBean createFromParcel(Parcel source) {
            return new VersionBean(source);
        }

        @Override
        public VersionBean[] newArray(int size) {
            return new VersionBean[size];
        }
    };
}
