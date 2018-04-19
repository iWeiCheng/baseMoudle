package com.jiajun.demo.network;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import java.io.IOException;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.internal.Util;
import okhttp3.internal.http.HttpMethod;

/**
 * 网络拦截器,设置公共参数
 * Created by cai.jia on 2017/4/14 0014
 */

public class NetworkInterceptor implements Interceptor {

    private Map<String, Object> params;
    private static final String ADD_COMMON_PARAMS = "isAddCommonParams";

    public NetworkInterceptor() {

    }

    public NetworkInterceptor(Map<String, Object> params) {
        this.params = params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request request = chain.request();
        String isAddCommonParams = request.header(ADD_COMMON_PARAMS);
        if ("false".equals(isAddCommonParams)) {//设置头部过滤  上传图片时候不过滤会导致500
            request = request.newBuilder().removeHeader(ADD_COMMON_PARAMS).build();
            return chain.proceed(request);
        }
        String method = request.method();
        if (TextUtils.isEmpty(method)) {
            return chain.proceed(request);
        }

        if (!HttpMethod.requiresRequestBody(method)) {
            request = request.newBuilder().url(createNewHttpUrl(request)).build();

        } else {
            request = request.newBuilder().method(method, createNewRequestBody(request)).build();
        }

        return chain.proceed(request);
    }

    private HttpUrl createNewHttpUrl(Request request) {
        if (params == null) {
            return request.url();
        }

        HttpUrl.Builder builder = request.url().newBuilder();
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            builder.addQueryParameter(entry.getKey(), String.valueOf(entry.getValue()));
        }
        return builder.build();
    }

    private RequestBody createNewRequestBody(Request request) {
        if (params == null) {
            return request.body();
        }
        RequestBody body = request.body();
        if (body == null) {
            return Util.EMPTY_REQUEST;
        }

        if (body instanceof FormBody) {
            FormBody.Builder builder = new FormBody.Builder();
            FormBody formBody = (FormBody) body;
            int size = formBody.size();
            for (int i = 0; i < size; i++) {
                String name = formBody.encodedName(i);
                String value = formBody.encodedValue(i);
                builder.addEncoded(name, value);
            }

            for (Map.Entry<String, Object> entry : params.entrySet()) {
                builder.add(entry.getKey(), String.valueOf(entry.getValue()));
            }
            return builder.build();

        } else if (body instanceof MultipartBody) {
            MultipartBody.Builder builder = new MultipartBody.Builder();
            builder.setType(MultipartBody.FORM);
            MultipartBody multipartBody = (MultipartBody) body;
            int size = multipartBody.size();
            for (int i = 0; i < size; i++) {
                MultipartBody.Part part = multipartBody.part(i);
                builder.addPart(part);
            }

            for (Map.Entry<String, Object> entry : params.entrySet()) {
                builder.addFormDataPart(entry.getKey(), String.valueOf(entry.getValue()));
            }
            return builder.build();
        }

        return Util.EMPTY_REQUEST;
    }
}
