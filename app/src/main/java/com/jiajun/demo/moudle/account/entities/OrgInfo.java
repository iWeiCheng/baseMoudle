package com.jiajun.demo.moudle.account.entities;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by dan on 2017/11/20/020.
 */

public class OrgInfo implements Parcelable {


    private List<OrgInfoBean> orgInfo;

    public List<OrgInfoBean> getOrgInfo() {
        return orgInfo;
    }

    public void setOrgInfo(List<OrgInfoBean> orgInfo) {
        this.orgInfo = orgInfo;
    }

    public static class OrgInfoBean implements Parcelable {
        /**
         * groudId : 714
         * name :
         * subOrg : [{"companyId":"716","name":""},{"companyId":"968","name":""},{"companyId":"970","name":""},{"companyId":"972","name":""},{"companyId":"794","name":""},{"companyId":"974","name":""},{"companyId":"976","name":""},{"companyId":"978","name":""},{"companyId":"980","name":""},{"companyId":"982","name":""}]
         */

        private String groudId;
        private String name;
        private List<SubOrgBean> subOrg;

        public String getGroudId() {
            return groudId;
        }

        public void setGroudId(String groudId) {
            this.groudId = groudId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<SubOrgBean> getSubOrg() {
            return subOrg;
        }

        public void setSubOrg(List<SubOrgBean> subOrg) {
            this.subOrg = subOrg;
        }

        public static class SubOrgBean implements Parcelable {
            /**
             * companyId : 716
             * name :
             */

            private String companyId;
            private String name;

            public String getCompanyId() {
                return companyId;
            }

            public void setCompanyId(String companyId) {
                this.companyId = companyId;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.companyId);
                dest.writeString(this.name);
            }

            public SubOrgBean() {
            }

            protected SubOrgBean(Parcel in) {
                this.companyId = in.readString();
                this.name = in.readString();
            }

            public static final Parcelable.Creator<SubOrgBean> CREATOR = new Parcelable.Creator<SubOrgBean>() {
                @Override
                public SubOrgBean createFromParcel(Parcel source) {
                    return new SubOrgBean(source);
                }

                @Override
                public SubOrgBean[] newArray(int size) {
                    return new SubOrgBean[size];
                }
            };
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.groudId);
            dest.writeString(this.name);
            dest.writeTypedList(this.subOrg);
        }

        public OrgInfoBean() {
        }

        protected OrgInfoBean(Parcel in) {
            this.groudId = in.readString();
            this.name = in.readString();
            this.subOrg = in.createTypedArrayList(SubOrgBean.CREATOR);
        }

        public static final Parcelable.Creator<OrgInfoBean> CREATOR = new Parcelable.Creator<OrgInfoBean>() {
            @Override
            public OrgInfoBean createFromParcel(Parcel source) {
                return new OrgInfoBean(source);
            }

            @Override
            public OrgInfoBean[] newArray(int size) {
                return new OrgInfoBean[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.orgInfo);
    }

    public OrgInfo() {
    }

    protected OrgInfo(Parcel in) {
        this.orgInfo = in.createTypedArrayList(OrgInfoBean.CREATOR);
    }

    public static final Parcelable.Creator<OrgInfo> CREATOR = new Parcelable.Creator<OrgInfo>() {
        @Override
        public OrgInfo createFromParcel(Parcel source) {
            return new OrgInfo(source);
        }

        @Override
        public OrgInfo[] newArray(int size) {
            return new OrgInfo[size];
        }
    };
}
