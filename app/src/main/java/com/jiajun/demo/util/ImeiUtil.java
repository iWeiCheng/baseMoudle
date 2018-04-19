package com.jiajun.demo.util;

import android.content.Context;
import android.telephony.TelephonyManager;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author L
 */
public class ImeiUtil {

    private static ImeiUtil instance;

    public static ImeiUtil getInstance() {
        if (instance == null)
            instance = new ImeiUtil();
        return instance;
    }

    private ImeiUtil() {
    }

    private String getMtkImei(Context context) {
        TelephonyManager telepManager;
        telepManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        try {
            Class<?> c = Class.forName("com.android.internal.telephony.Phone");
            Method m1 = TelephonyManager.class.getDeclaredMethod("getDeviceIdGemini", int.class);

            Field fields1 = c.getField("GEMINI_SIM_1");
            if (fields1 != null) {
                fields1.setAccessible(true);
                int simId_1 = (Integer) fields1.get(null);
                String imei_1 = (String) m1.invoke(telepManager, simId_1);
                if (verifyImei(imei_1)) {
                    return imei_1;
                }
            }

            Field fields2 = c.getField("GEMINI_SIM_2");
            if (fields2 != null) {
                fields2.setAccessible(true);
                int simId_2 = (Integer) fields2.get(null);
                String imei_2 = (String) m1.invoke(telepManager, simId_2);
                if (verifyImei(imei_2)) {
                    return imei_2;
                }
            }

        } catch (Exception e) {
            return null;
        }

        return null;
    }

    private String getSpreadImei(Context context) {

        try {
            Class<?> c = Class.forName("com.android.internal.telephony.PhoneFactory");
            Method m = c.getMethod("getServiceName", String.class, int.class);
            String spreadTmService = (String) m.invoke(c, Context.TELEPHONY_SERVICE, 1);
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            TelephonyManager tm1 = (TelephonyManager) context.getSystemService(spreadTmService);

            String imei_1 = tm.getDeviceId();
            String imei_2 = tm1.getDeviceId();
            if (verifyImei(imei_1)) {
                return imei_1;
            }
            if (verifyImei(imei_2)) {
                return imei_2;
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }

    private String getQualcommImei(Context context) {
        Context mContext = context;
        try {

            Class<?> cx = Class.forName("android.telephony.MSimTelephonyManager");
            Object obj = mContext.getSystemService("phone_msim");
            int simId_1 = 0;
            int simId_2 = 1;

            Method md = cx.getMethod("getDeviceId", int.class);

            String imei_1 = (String) md.invoke(obj, simId_1);
            String imei_2 = (String) md.invoke(obj, simId_2);
            if (verifyImei(imei_1)) {
                return imei_1;
            }
            if (verifyImei(imei_2)) {
                return imei_2;
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }

    public boolean verifyImei(String imei) {
        if (!imei.isEmpty()) {
            if (getimei15(imei.substring(0, imei.length() - 1)).equals(imei.substring(imei.length() - 1))) {
                return true;
            }
        }
        return false;
    }

    private static String getimei15(String imei) {
        if (imei.length() == 14) {
            try {
                char[] imeiChar = imei.toCharArray();
                int resultInt = 0;
                for (int i = 0; i < imeiChar.length; i++) {
                    int a = Integer.parseInt(String.valueOf(imeiChar[i]));
                    i++;
                    final int temp = Integer.parseInt(String.valueOf(imeiChar[i])) * 2;
                    final int b = temp < 10 ? temp : temp - 9;
                    resultInt += a + b;
                }
                resultInt %= 10;
                resultInt = resultInt == 0 ? 0 : 10 - resultInt;
                return resultInt + "";
            } catch (Exception e) {
                e.printStackTrace();
                return "";
            }
        } else {
            return "";
        }
    }

    public String getImei(Context context) {
        try {
            String getQualcommImei = getQualcommImei(context);
            String getMtkImei = getMtkImei(context);
            String getSpreadImei = getSpreadImei(context);
            if (getQualcommImei != null) {
                return getQualcommImei;

            } else if (getMtkImei != null) {
                return getMtkImei;

            } else if (getSpreadImei != null) {
                return getSpreadImei;

            } else {
                TelephonyManager telepManager;
                telepManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                String imei = telepManager.getDeviceId();
                return imei;
            }
        } catch (Exception e) {

        }
        return "";
    }
}
