package com.jiajun.demo.moudle.account.entities;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by danjj on 2016/12/6.
 */
public class OrganizationInfoBean implements Parcelable {

    private List<OrgInfoBean> orgInfo;

    public List<OrgInfoBean> getOrgInfo() {
        return orgInfo;
    }

    public void setOrgInfo(List<OrgInfoBean> orgInfo) {
        this.orgInfo = orgInfo;
    }

    public static class OrgInfoBean implements Parcelable {
        /**
         * groudId : 1031
         * name : 东莞鼎泰保险有限公司
         * subOrg : [{"companyId":"1032","name":"南城营业部"},{"companyId":"1033","name":"东城营业部"},{"companyId":"1034","name":"横沥营业部"},{"companyId":"1035","name":"企石营业部"},{"companyId":"1036","name":"常平营业部"},{"companyId":"1037","name":"桥头营业部"},{"companyId":"1038","name":"东坑营业部"},{"companyId":"1039","name":"大朗营业部"},{"companyId":"1040","name":"道滘营业部"},{"companyId":"1041","name":"长安营业部"},{"companyId":"1042","name":"麻涌营业部"},{"companyId":"1043","name":"厚街营业部"},{"companyId":"1044","name":"寮步一部营业部"},{"companyId":"1045","name":"寮步二部营业部"},{"companyId":"1046","name":"邹海团队"},{"companyId":"1047","name":"孙艳团队"}]
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
             * companyId : 1032
             * name : 南城营业部
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

            public static final Creator<SubOrgBean> CREATOR = new Creator<SubOrgBean>() {
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
            dest.writeList(this.subOrg);
        }

        public OrgInfoBean() {
        }

        protected OrgInfoBean(Parcel in) {
            this.groudId = in.readString();
            this.name = in.readString();
            this.subOrg = new ArrayList<SubOrgBean>();
            in.readList(this.subOrg, SubOrgBean.class.getClassLoader());
        }

        public static final Creator<OrgInfoBean> CREATOR = new Creator<OrgInfoBean>() {
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

    public OrganizationInfoBean() {
    }

    protected OrganizationInfoBean(Parcel in) {
        this.orgInfo = in.createTypedArrayList(OrgInfoBean.CREATOR);
    }

    public static final Creator<OrganizationInfoBean> CREATOR = new Creator<OrganizationInfoBean>() {
        @Override
        public OrganizationInfoBean createFromParcel(Parcel source) {
            return new OrganizationInfoBean(source);
        }

        @Override
        public OrganizationInfoBean[] newArray(int size) {
            return new OrganizationInfoBean[size];
        }
    };
}
