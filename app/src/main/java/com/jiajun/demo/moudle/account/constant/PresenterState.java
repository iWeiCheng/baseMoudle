package com.jiajun.demo.moudle.account.constant;

/**
 * P层的一些检查状态
 * Created by cai.jia on 2016/7/21 0021.
 */

public interface PresenterState {

    int SUCCESS = 0x0;

    /**
     * 登录的一些检查状态
     */
    interface LoginState {

        //用户名为空
        int USERNAME_EMPTY = 0x1;

        //密码未空
        int PASSWORD_EMPTY = 0x2;

        //手机号码格式不对
        int USERNAME_ERROR = 0x3;
    }

    /**
     * 登出检查状态
     */
    interface LogoutState {

        //token为空
        int TOKEN_EMPTY = 0x1;
    }

    interface RegisterState {

        //用户名为空
        int USERNAME_EMPTY = 0x1;

        //用户名不符合
        int USERNAME_ERROR = 0x2;

        //密码为空
        int PASSWORD_EMPTY = 0x3;

        //昵称为空
        int NICKNAME_EMPTY = 0x4;

        //验证码为空
        int VALIDATE_CODE_EMPTY = 0x5;
    }

    interface RegisterMobileVerify {

        //手机号码为空
        int MOBILE_EMPTY = 0x1;

        //手机号码不正确
        int MOBILE_ERROR = 0x2;

    }

    interface ForgetMobileVerify {

        //手机号码为空
        int MOBILE_EMPTY = 0x1;

        //手机号码不正确
        int MOBILE_ERROR = 0x2;
    }

    interface CheckVerify {

        //用户名为空
        int USERNAME_EMPTY = 0x1;

        //手机号码不正确
        int USERNAME_ERROR = 0x2;

        //验证码为空
        int VALIDATE_CODE_EMPTY = 0x3;

    }

    interface CheckPhoneState {

        //手机号码为空
        int MOBILE_EMPTY = 0x1;

        //手机号码不正确
        int MOBILE_ERROR = 0x2;
    }

    interface SendMobileState {

        //手机号码为空
        int MOBILE_EMPTY = 0x1;

        //手机号码不正确
        int MOBILE_ERROR = 0x2;
    }

    interface CheckPasswordState{

        int PASSWORD_EMPTY = 0x1 ;

        int PASSWORD_ERROR = 0x2 ;
    }

}
