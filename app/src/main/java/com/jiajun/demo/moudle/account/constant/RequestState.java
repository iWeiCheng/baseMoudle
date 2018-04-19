package com.jiajun.demo.moudle.account.constant;

/**
 * Created by Administrator on 2016/7/22 0022.
 * request的Id
 */
public interface RequestState {

    //登录
    int Account_Login = 0x1;

    //注册
    int Account_Register = 0x2;

    //
    int CHECK_PHONE = 0x3;
    int SEND_CODE = 0x4;

    // 忘记密码
    int Account_ForgetPassword = 0x5;

    // 重设密码
    int Account_SetPassword = 0x6;
}
