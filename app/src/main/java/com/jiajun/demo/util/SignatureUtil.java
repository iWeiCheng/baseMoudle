package com.jiajun.demo.util;

import android.util.Log;

import com.jiajun.demo.network.Network;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;


/**
 * Created by dan on 2017/11/20/020.
 */

public class SignatureUtil {

    /**
     * 生成签名, 先按KEY排序, 再生成签名
     *
     * @param maps
     * @return
     */
    public static String getSignature(Map<String, String> maps) {
//		secret_key = ;
        String args = "";
        List<Map.Entry<String, String>> keyList = new ArrayList<Map.Entry<String, String>>(maps.entrySet());
        // 然后通过比较器来实现排序
        Collections.sort(keyList, new Comparator<Map.Entry<String, String>>() {
            // 升序排序
            public int compare(Map.Entry<String, String> o1, Map.Entry<String, String> o2) {
                return o1.getKey().compareTo(o2.getKey());
            }
        });
        for (Map.Entry<String, String> entry : keyList) {
            if (!entry.getKey().equals("signature")) {
                args +=entry;
//                args += entry.getValue();
            }
        }
        args += Network.KEY;
        String new_signature = MD5.getMD5(args);
        Log.i("传入key排序：", keyList.toString());
        Log.i("传入签名参数：", args);
        Log.i("生成签名：", new_signature);
        return new_signature.toUpperCase();
    }
}
