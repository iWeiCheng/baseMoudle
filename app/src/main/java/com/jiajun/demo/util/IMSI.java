package com.jiajun.demo.util;

import android.content.Context;
import android.telephony.TelephonyManager;

/**
 * IMSI
 * Created by dan on 2016/5/19.
 */
public class IMSI {
    public static String getImsi(Context context) {
        try {
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            String _imsi = tm.getSubscriberId();
            if (_imsi != null && !_imsi.equals("")) {
                return _imsi;
            }
        } catch (Exception e) {
            return "";
        }
        return "";
    }
}
