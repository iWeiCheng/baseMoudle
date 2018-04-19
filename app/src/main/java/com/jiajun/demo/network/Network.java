package com.jiajun.demo.network;

import android.content.Context;
import android.util.Log;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.franmontiel.persistentcookiejar.ClearableCookieJar;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.jiajun.demo.network.api.GetCodeApi;
import com.jiajun.demo.network.api.GetOrgInfoApi;
import com.jiajun.demo.network.api.GetVersionApi;
import com.jiajun.demo.network.api.LoginApi;
import com.jiajun.demo.network.api.RegisterApi;
import com.jiajun.demo.network.api.ResetPwdApi;
import com.jiajun.demo.util.IMSI;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.https.HttpsUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.OkHttpClient;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * api
 */


public class Network {


    private static GetVersionApi getVersionApi;
    private static GetOrgInfoApi getOrgInfoApi;
    private static RegisterApi registerApi;
    private static LoginApi loginApi;
    private static GetCodeApi getCodeApi;
    private static ResetPwdApi resetPwdApi;
//    private static GetGuideImagesApi guideImagesApi;
//    private static HomePageApi homePageApi;
//    private static PrivilegeApi privilegeApi;
//    private static CustomerApi customerApi;
//    private static UserInfoApi userinfoApi;
//    private static GetPersonalApi personalApi;
//    private static UploadHeadImgApi uploadHeadImgApi;


    private static OkHttpClient okHttpClient;
    private static Retrofit retrofit;
    private static Converter.Factory gsonConverterFactory = GsonConverterFactory.create();
    private static CallAdapter.Factory rxJavaCallAdapterFactory = RxJavaCallAdapterFactory.create();

    public static String KEY = "20171025#$@";
    public static String URL = "marke_Interfaces.jsp";
//        public static String SERVICE = "http://admintest.implus100.com";
//    public static String SERVICE = "http://test.implus100.com";
//    public static String SERVICE = "http://192.168.31.239:8080";
//    public static String SERVICE = "http://120.31.52.67:8080";
//    public static String SERVICE = "http://prepare.implus100.com";
    public static String SERVICE = "http://www.implus100.com";
//    public static String SERVICE = "http://49.4.14.102";
    public static String BASE_URL = SERVICE + "/agent/interface/";
//    public static String WORK_URL = SERVICE + "/agent/interface/marke_Interfaces.jsp";
    public static String WORK_URL = SERVICE + "/agent/interface/interfaces_marke.jsp";
//    public static String WORK_URL = SERVICE + "/agent/interface/marke_Interfaces.jsp";
//    public static String WORK_URL = SERVICE + "/agent/interface/interface_ax.jsp";

    public static SetCookieCache cookieCache = new  SetCookieCache();

    public Network() {

    }

    public static void init(Context context) {
        Map<String, Object> map = new HashMap<>();
        map.put("type", "1");
        map.put("imsi", IMSI.getImsi(context));
        NetworkInterceptor interceptor = new NetworkInterceptor(map);
        HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory(null, null, null);
//        CookieJarImpl cookieJar = new CookieJarImpl(new PersistentCookieStore(context));
        ClearableCookieJar cookieJar =
                new PersistentCookieJar(cookieCache, new SharedPrefsCookiePersistor(context));
        okHttpClient = new OkHttpClient.Builder()
                .addNetworkInterceptor(interceptor)
                .addNetworkInterceptor(new StethoInterceptor())
                .hostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String hostname, SSLSession session) {
                        return true;
                    }
                })
                .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .cookieJar(cookieJar)
                .build();
        OkHttpUtils.initClient(okHttpClient);
        retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(BASE_URL)
                .addConverterFactory(gsonConverterFactory)
                .addCallAdapterFactory(rxJavaCallAdapterFactory)
                .build();
    }


    /**
     * 获取版本号
     *
     * @return
     */
    public static GetVersionApi getVersionApi() {
        if (getVersionApi == null) {
            getVersionApi = retrofit.create(GetVersionApi.class);
        }
        Log.e("oooooo", "getVersionApi");
        return getVersionApi;
    }

    /**
     * 注册
     *
     * @return
     */
    public static RegisterApi RegisterApi() {
        if (registerApi == null) {
            registerApi = retrofit.create(RegisterApi.class);
        }
        Log.e("oooooo", "register");
        return registerApi;
    }

    /**
     * 获取集团公司
     *
     * @return
     */
    public static GetOrgInfoApi getOrgInfoApi() {
        if (getOrgInfoApi == null) {
            getOrgInfoApi = retrofit.create(GetOrgInfoApi.class);
        }
        Log.e("oooooo", "getOrgInfoApi");
        return getOrgInfoApi;
    }

    /**
     * 登录
     *
     * @return
     */
    public static LoginApi login() {
        if (loginApi == null) {
            loginApi = retrofit.create(LoginApi.class);
        }
        Log.e("oooooo", "login");
        return loginApi;
    }

    /**
     * 获取验证码
     *
     * @return
     */
    public static GetCodeApi getCode() {
        if (getCodeApi == null) {
            getCodeApi = retrofit.create(GetCodeApi.class);
        }
        Log.e("oooooo", "getcode");
        return getCodeApi;
    }

    /**
     * 重置密码
     *
     * @return
     */
    public static ResetPwdApi resetPwd() {
        if (resetPwdApi == null) {
            resetPwdApi = retrofit.create(ResetPwdApi.class);
        }
        Log.e("oooooo", "resetpwd");
        return resetPwdApi;
    }

//    /**
//     * 获取首次加载广告图片
//     *
//     * @return
//     */
//    public static GetGuideImagesApi getGuideImages() {
//        if (guideImagesApi == null) {
//            guideImagesApi = retrofit.create(GetGuideImagesApi.class);
//        }
//        Log.e("oooooo", "getGuideImages");
//        return guideImagesApi;
//    }
//
//    /**
//     * 获取首页信息
//     *
//     * @return
//     */
//    public static HomePageApi getHomePage() {
//        if (homePageApi == null) {
//            homePageApi = retrofit.create(HomePageApi.class);
//        }
//        Log.e("oooooo", "getHomePage");
//        return homePageApi;
//    }
//
//
//    /**
//     * 获取vip特权信息
//     *
//     * @return
//     */
//    public static PrivilegeApi getVipPrivilegeApi() {
//        if (privilegeApi == null) {
//            privilegeApi = retrofit.create(PrivilegeApi.class);
//        }
//        Log.e("oooooo", "getVipPrivilegeApi");
//        return privilegeApi;
//    }
//
//    /**
//     * 获取客户列表
//     *
//     * @return
//     */
//    public static CustomerApi getCustomerApi() {
//        if (customerApi == null) {
//            customerApi = retrofit.create(CustomerApi.class);
//        }
//        Log.e("oooooo", "getCustomerApi");
//        return customerApi;
//    }
//
//    /**
//     * 获取我的信息
//     *
//     * @return
//     */
//    public static UserInfoApi getUserInfoApi() {
//        if (userinfoApi == null) {
//            userinfoApi = retrofit.create(UserInfoApi.class);
//        }
//        Log.e("oooooo", "getUserInfoApi");
//        return userinfoApi;
//    }
//
//    /**
//     * 获取个人中心信息
//     *
//     * @return
//     */
//    public static GetPersonalApi getPersonalApi() {
//        if (personalApi == null) {
//            personalApi = retrofit.create(GetPersonalApi.class);
//        }
//        Log.e("oooooo", "GetPersonalApi");
//        return personalApi;
//    }
//
//    /**
//     * 上传头像
//     *
//     * @return
//     */
//    public static UploadHeadImgApi GetUploadHeadImgApi() {
//        if (uploadHeadImgApi == null) {
//            uploadHeadImgApi = retrofit.create(UploadHeadImgApi.class);
//        }
//        Log.e("oooooo", "uploadHeadImgApi");
//        return uploadHeadImgApi;
//    }

}
