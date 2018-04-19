package com.jiajun.demo.util;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.util.Base64;
import android.util.TypedValue;
import android.widget.Toast;


import com.jiajun.demo.R;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by cai.jia on 2016/7/21 0021.
 */

public class CommonUtil {

    /**
     * 是否是手机号码
     *
     * @return
     */
    public static boolean isPhoneNumber(String phone) {
        if (!TextUtils.isEmpty(phone)) {
            Pattern pattern = Pattern.compile("^1[345678]\\d{9}$");
            Matcher matcher = pattern.matcher(phone);
            return matcher.find();
        }
        return false;
    }
    /**
     * 判断邮箱是否合法
     *
     * @param email
     * @return
     */
    public static boolean isEmail(String email) {
        if (null == email || "".equals(email)) return false;
        //Pattern p = Pattern.compile("\\w+@(\\w+.)+[a-z]{2,3}"); //简单匹配
        Pattern p = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");//复杂匹配
        Matcher m = p.matcher(email);
        return m.matches();
    }
    /**
     * 是否是jsonObject
     * @param json
     * @return
     * @throws Exception
     */
    public static boolean isJsonObject(String json) throws Exception {
        if (TextUtils.isEmpty(json)) {
            return false;
        }
        Object o = new JSONTokener(json).nextValue();
        return o instanceof JSONObject;
    }

    /**
     * 是否是jsonArray
     * @param json
     * @return
     * @throws Exception
     */
    public static boolean isJsonArray(String json) throws Exception {
        if (TextUtils.isEmpty(json)) {
            return false;
        }
        Object o = new JSONTokener(json).nextValue();
        return o instanceof JSONArray;
    }


    /**
     * 过滤特殊字符
     * @param value
     * @return
     */
    private   String specialCharFilter(String value){
        String	reg="[^a-zA-Z0-9\u4E00-\u9FA5_]";
        String str;
        str = value.replaceAll(reg,"");
        return str;
    }
    /**
     * 获取程序版本名称
     *
     * @throws PackageManager.NameNotFoundException
     *
     */
    public static String getAppVesionName(Context context)
            throws PackageManager.NameNotFoundException {
        return context.getPackageManager().getPackageInfo(
                context.getPackageName(), Context.MODE_PRIVATE).versionName;
    }

    /**
     * 得到ActionBar的高度
     * @param context
     * @return
     */
    public static int getActionBarSize(Context context) {
        // Calculate ActionBar height
        float dimension = context.getResources().getDimension(R.dimen.action_bar_size);
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, dimension, context.getResources().getDisplayMetrics());
    }

    /**
     * 获取状态栏的高度
     *
     * @param context
     * @return
     */
    public static int getStatusBarHeight(Context context) {
        int statusBarHeight = 0;
        try {
            Class<?> c = Class.forName("com.android.internal.R$dimen");
            Object o = c.newInstance();
            Field field = c.getField("status_bar_height");
            int x = (Integer) field.get(o);
            statusBarHeight = context.getResources().getDimensionPixelSize(x);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusBarHeight;
    }

    public static int getTopMargin(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            return CommonUtil.getActionBarSize(context) + CommonUtil.getStatusBarHeight(context);
        } else {
            return CommonUtil.getActionBarSize(context);
        }
    }

    public static void showToast(Context context, String msg) {
        if (!TextUtils.isEmpty(msg)) {
            Toast toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
            toast.show();
        }
    }



        public static int getScreenWidth(Context context) {
            return context.getResources().getDisplayMetrics().widthPixels;
        }

        public static int getScreenHeight(Context context) {
            return context.getResources().getDisplayMetrics().heightPixels;
        }

        public static int px2dip(Context context, float pxValue) {
            final float scale = context.getResources().getDisplayMetrics().density;
            return (int) (pxValue / scale + 0.5f);
        }

        public static int dip2px(Context context, float dipValue) {
            final float scale = context.getResources().getDisplayMetrics().density;
            return (int) (dipValue * scale);
        }



        public static void showLongToast(Context context, String msg) {
            if (!TextUtils.isEmpty(msg)) {
                Toast toast = Toast.makeText(context, msg, Toast.LENGTH_LONG);
                toast.show();
            }
        }

        public static Toast createToast(Context context, String msg) {
            if (!TextUtils.isEmpty(msg)) {
                Toast toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
                toast.show();
                return toast;
            }
            return null;
        }



        public static Bitmap revitionImageSize(String path) throws IOException {
            BufferedInputStream in = new BufferedInputStream(new FileInputStream(
                    new File(path)));
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(in, null, options);
            in.close();
            int i = 0;
            Bitmap bitmap = null;
            while (true) {
                if ((options.outWidth >> i <= 480) && (options.outHeight >> i <= 800)) {
                    in = new BufferedInputStream(
                            new FileInputStream(new File(path)));
                    options.inSampleSize = (int) Math.pow(2.0D, i);
                    options.inJustDecodeBounds = false;
                    bitmap = BitmapFactory.decodeStream(in, null, options);
                    break;
                }
                i += 1;
            }
            return bitmap;
        }

        /**
         *
         * 将图片转为String
         *
         */
        public static String Image2String(Bitmap bm) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.JPEG, 40, stream);
            byte[] b = stream.toByteArray();
            // 将图片流以字符串形式存储下来
            // String tp = new String(Base64Coder.encodeLines(b));
            return "";
        }

        public static String Image2Base64(Bitmap bitmap,int percent) {
            String result = null;
            ByteArrayOutputStream baos = null;
            try {
                if (bitmap != null) {
                    baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, percent, baos);
                    baos.flush();
                    baos.close();
                    byte[] bitmapBytes = baos.toByteArray();
                    result = Base64.encodeToString(bitmapBytes, Base64.DEFAULT);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (baos != null) {
                        baos.flush();
                        baos.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return result;
        }

        public static String Image2Base64(Bitmap bitmap) {
            return Image2Base64(bitmap, 100);
        }

        public static void call(Context context, String mobile) {
            if (!TextUtils.isEmpty(mobile)) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + mobile));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                context.startActivity(intent);
            } else {
                CommonUtil.showToast(context, "没有电话号码");
            }
        }

        public static boolean isNum(String str) {
            if (TextUtils.isEmpty(str) || str.trim().equals("-") || str.trim().equals("+")) {
                return false;
            }
            return str.matches("^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$");
        }

        public static boolean isPosNum(String string) {
            return string.matches("^(([1-9]\\d*)|(([1-9][0-9]*\\.[0-9]{1,2})|([0]\\.[0-9]{1,2})))$");
        }

        public static boolean isPositiveIntNumber(String status) {
            if (TextUtils.isEmpty(status)) {
                return false;
            }
            Pattern pattern = Pattern.compile("^[0-9]\\d*$");
            Matcher matcher = pattern.matcher(status);
            return matcher.matches();
        }



        public static String addStrFormat(String format,String ...numStr) {
            Double totalPrice = 0d;
            for (String num : numStr) {
                if (CommonUtil.isNum(num)) {
                    totalPrice += Double.parseDouble(num);
                }
            }
            return String.format(format, totalPrice);
        }

        public static String addStr(String ...numStr) {
            Double totalPrice = 0d;
            for (String num : numStr) {
                if (CommonUtil.isNum(num)) {
                    totalPrice += Double.parseDouble(num);
                }
            }
            return String.format("%.2f", totalPrice);
        }

        public static boolean equals(String num1, int num) {
            return isNum(num1) && Double.parseDouble(num1) == num;
        }


        public static String formatTime(long time, String pattern) {
            SimpleDateFormat dateFormat = new SimpleDateFormat(pattern, Locale.CHINESE);
            return dateFormat.format(new Date(time));
        }

        public static String formatTime(long time) {
            return formatTime(time, "yyyy-MM-dd HH:mm:ss");
        }

//        public static void showCustomToast(Context context, String message) {
//            View view = LayoutInflater.from(context)
//                    .inflate(R.layout.view_toast_layout,new LinearLayout(context),false);
//            TextView text = (TextView) view.findViewById(R.id.text);
//            text.setText(message);
//            Toast toast = new Toast(context);
//            toast.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.TOP, 0, CommonUtil.dip2px(context,60));
//            toast.setDuration(Toast.LENGTH_SHORT);
//            ViewGroup.LayoutParams p = text.getLayoutParams();
//            p.width = CommonUtil.getScreenWidth(context) - CommonUtil.dip2px(context, 36);
//            text.setLayoutParams(p);
//            toast.setView(view);
//            toast.show();
//        }

        public static String getDevicesVersion(){
            String model = Build.MODEL;
            return model;
        }

        /**
         * 生成唯一码
         * @return
         */
        public static String uuid() {
            return UUID.randomUUID().toString().replace("-", "").toUpperCase();
        }

        public static void installApk(Context context, String path) {
            if (TextUtils.isEmpty(path)) {
                return;
            }
            Intent intent = new Intent(Intent.ACTION_VIEW);
            File file = new File(path);
            if (file.exists()) {
                intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        }

    }


