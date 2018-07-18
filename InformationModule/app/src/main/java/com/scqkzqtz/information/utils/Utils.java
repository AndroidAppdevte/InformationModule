package com.scqkzqtz.information.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.databinding.BindingAdapter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by hghl on 2017/2/10.
 */

public class Utils {

    public static void showLongToast(Context context, String pMsg) {
        LongToast(context, pMsg);
    }

    private static void LongToast(Context context, String text) {
        if (lToast == null) {
            lToast = Toast.makeText(context.getApplicationContext(), text, Toast.LENGTH_SHORT);
            lToast.setGravity(Gravity.CENTER, 0, 0);
        } else {
            lToast.setText(text);
            lToast.setDuration(Toast.LENGTH_LONG);
        }
        lToast.show();
    }

    public static void showShortToast(Context context, String pMsg) {
        shortToast(context, pMsg);
    }

    private static void shortToast(Context context, String text) {
        if (mToast == null) {
            mToast = Toast.makeText(context.getApplicationContext(), text, Toast.LENGTH_SHORT);
            mToast.setGravity(Gravity.CENTER, 0, 0);
        } else {
            mToast.setText(text);
            mToast.setDuration(Toast.LENGTH_SHORT);
        }
        mToast.show();
    }

    /**
     * 将长时间格式时间转换为    分时秒字符串 yyyy-MM-dd HH:mm:ss
     *
     * @param dateDate
     * @param i
     * @return
     */
    public static String ConverToString(Date dateDate, int i) {
        String dateString = "";
        String strsub = "";
        if (dateDate != null) {
            switch (i) {
                case 1:
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                    strsub = formatter.format(dateDate);
                    break;
                case 2:
                    SimpleDateFormat time = new SimpleDateFormat("HH:mm");
                    strsub = time.format(dateDate);
                    break;
                case 3:
                    SimpleDateFormat time3 = new SimpleDateFormat("yyyy.MM.dd");
                    strsub = time3.format(dateDate);
                    break;
                case 4:
                    SimpleDateFormat time4 = new SimpleDateFormat("yyyy-MM-dd");
                    strsub = time4.format(dateDate);
                    break;
                case 5:
                    SimpleDateFormat time5 = new SimpleDateFormat("yyyy.MM.dd HH:mm");
                    strsub = time5.format(dateDate);
                    break;
                case 6:
                    SimpleDateFormat time6 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    strsub = time6.format(dateDate);
                    break;
                case 7:
                    SimpleDateFormat time7 = new SimpleDateFormat("yyyy年MM月dd日");
                    strsub = time7.format(dateDate);
                    break;
                case 8:
                    SimpleDateFormat tim8 = new SimpleDateFormat("MM-dd");
                    strsub = tim8.format(dateDate);
                    break;
                case 9:
                    SimpleDateFormat time9 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                    strsub = time9.format(dateDate);
                    break;
                case 10:
                    SimpleDateFormat time10 = new SimpleDateFormat("yyyy/MM/dd");
                    strsub = time10.format(dateDate);
                    break;
                case 11:
                    SimpleDateFormat time11 = new SimpleDateFormat("yyyy");
                    strsub = time11.format(dateDate);
                    break;
                case 12:
                    SimpleDateFormat time12 = new SimpleDateFormat("MM月dd日");
                    strsub = time12.format(dateDate);
                    break;
                case 13:
                    SimpleDateFormat time13 = new SimpleDateFormat("yyyyMMdd");
                    strsub = time13.format(dateDate);
                    break;
                case 14:
                    SimpleDateFormat time14 = new SimpleDateFormat("yyyy/MM/dd HH:mm");
                    strsub = time14.format(dateDate);
                    break;
                case 15:
                    SimpleDateFormat time15 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    strsub = time15.format(dateDate).substring(0, 16);
                    break;
                case 16:
                    SimpleDateFormat time16 = new SimpleDateFormat("dd日");
                    strsub = time16.format(dateDate);
                    break;
                case 17:
                    SimpleDateFormat time17 = new SimpleDateFormat("HH:mm:ss");
                    strsub = time17.format(dateDate);
                    break;
            }
        } else {
            strsub = "";
        }

        return strsub;
    }

    /**
     * 时间戳转时间
     *
     * @param time
     * @return
     */
    public static String ConverToString(long time, String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        String dateStr = "";
        dateStr = format.format(time);
        return dateStr;
    }

    private static final SharedPreferences getSharedPreference(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    /**
     * 获取SharedPreference 值
     *
     * @param context
     * @param key
     * @return
     */
    public static final String getValue(Context context, String key) {
        return getSharedPreference(context).getString(key, "");
    }

    public static final Boolean getBooleanValue(Context context, String key) {
        return getSharedPreference(context).getBoolean(key, false);
    }

    public static final void putBooleanValue(Context context, String key,
                                             boolean bl) {
        SharedPreferences.Editor edit = getSharedPreference(context).edit();
        edit.putBoolean(key, bl);
        edit.commit();
    }

    public static final int getIntValue(Context context, String key) {
        return getSharedPreference(context).getInt(key, 0);
    }

    public static final long getLongValue(Context context, String key,
                                          long default_data) {
        return getSharedPreference(context).getLong(key, default_data);
    }

    public static final boolean putLongValue(Context context, String key,
                                             Long value) {
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putLong(key, value);
        return editor.commit();
    }

    public static final Boolean hasValue(Context context, String key) {
        return getSharedPreference(context).contains(key);
    }

    /**
     * 设置SharedPreference 值
     *
     * @param context
     * @param key
     * @param value
     */
    public static final boolean putValue(Context context, String key,
                                         String value) {
        value = value == null ? "" : value;
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString(key, value);
        boolean result = editor.commit();
        if (!result) {
            return false;
        }
        return true;
    }

    /**
     * 设置SharedPreference 值
     *
     * @param context
     * @param key
     * @param value
     */
    public static final boolean putIntValue(Context context, String key,
                                            int value) {
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putInt(key, value);
        boolean result = editor.commit();
        if (!result) {
            return false;
        }
        return true;
    }


    /**
     * 获取当前小时
     */
    public static String getHourTime() {
        String Time;
        Calendar c = Calendar.getInstance();
//        取得系统日期:year = c.get(Calendar.YEAR)
//        month = c.grt(Calendar.MONTH)
//        day = c.get(Calendar.DAY_OF_MONTH)
        int mm = c.get(Calendar.MINUTE);
        if (mm > 9) {
            Time = String.valueOf(c.get(Calendar.HOUR_OF_DAY)) + " : " + String.valueOf(mm);
        } else {
            Time = String.valueOf(c.get(Calendar.HOUR_OF_DAY)) + " : " + "0" + String.valueOf(mm);
        }

        return Time;
    }

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     * DisplayMetrics类中属性scaledDensity）
     */
    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;

        return (int) (pxValue / fontScale + 0.5f);

    }


    /**
     * 将sp值转换为px值，保证文字大小不变
     * （DisplayMetrics类中属性scaledDensity）
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * 时间戳转化为字符串
     *
     * @param date
     * @return
     */
    public static String getTime(long date) {
        long minute = (System.currentTimeMillis() - date) / (1000 * 60);
        if (minute < 1) {
            return "刚刚";
        } else if (1 <= minute && minute < 60) {
            return minute + "分钟前";
        }

        long hour = (System.currentTimeMillis() - date) / (1000 * 60 * 60);
        if (1 <= hour && hour < 24) {
            return hour + "小时前";
        }

        long day = (System.currentTimeMillis() - date) / (1000 * 60 * 60 * 24);
        if (1 <= day && day < 30) {
            return day + "天前";
        } else {
            long month = day / 30;
            return month + "月前";
        }

    }

    public static String getReduceTime(long time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long times = time - 8 * 60 * 60 * 1000;
        String d = format.format(times);
        Date date = null;
        try {
            date = format.parse(d);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String dateString = "";
        SimpleDateFormat formatter = null;
        formatter = new SimpleDateFormat("HH:mm:ss");
        dateString = formatter.format(date);
        return dateString;
    }

    /*
     * 防止连续点击
     */
    public synchronized static boolean isMainFastClick() {
        long time = System.currentTimeMillis();
        if (time - lastClickTime < 1000) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

    /**
     * 隐藏软键盘
     *
     * @param context
     * @param editText
     */
    public static final void hideSoftInput(Context context, EditText editText) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0); //强制隐藏键盘
    }

    /**
     * 距离今天多久
     *
     * @param date
     * @return
     */
    public static String fromToDate(Date date) {
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);

            long time = date.getTime() / 1000;
            long now = new Date().getTime() / 1000;
            long ago = now - time;
            if (ago <= 60) {
                return "刚刚";
            } else if (ago <= ONE_HOUR)
                return ago / ONE_MINUTE + "分钟前";
            else if (ago <= ONE_DAY)
//            return ago / ONE_HOUR + "小时" + (ago % ONE_HOUR / ONE_MINUTE)
//                    + "分钟前";
                return ago / ONE_HOUR + "小时前";
            else {
                return ConverToString(date, 4);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }


    private static final long ONE_MINUTE = 60;
    private static final long ONE_HOUR = 3600;
    private static final long ONE_DAY = 86400;
    private static final long ONE_MONTH = 2592000;
    private static final long ONE_YEAR = 31104000;

    public static Calendar calendar = Calendar.getInstance();
    private static Toast mToast;
    private static Toast lToast;

    private static long lastClickTime;
    private static Handler myHandler = new Handler();


    /*
    * 防止连续点击
    */
    public synchronized static boolean isFastClick() {
        long time = System.currentTimeMillis();
        if (time - lastClickTime < 500) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

    /**
     * 多种隐藏软件盘方法的其中一种
     *
     * @param token
     */
    public static void hideSoftInput(IBinder token, InputMethodManager im) {
        if (token != null) {
            im.hideSoftInputFromWindow(token,
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /*
     * 获取缓存大小
     */
    public static String getTotalCacheSize(Context context) throws Exception {
        long cacheSize = getFolderSize(context.getCacheDir());
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            cacheSize += getFolderSize(context.getExternalCacheDir());
        }
        return getFormatSize(cacheSize);
    }

    /*
     * 清理缓存
     */
    public static void clearAllCache(Context context) {
        deleteDir(context.getCacheDir());
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            deleteDir(context.getExternalCacheDir());
        }
    }

    private static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        return dir.delete();
    }

    // 获取文件
    // Context.getExternalFilesDir() --> SDCard/Android/data/你的应用的包名/files/
    // 目录，一般放一些长时间保存的数据
    // Context.getExternalCacheDir() -->
    // SDCard/Android/data/你的应用包名/cache/目录，一般存放临时缓存数据
    public static long getFolderSize(File file) throws Exception {
        long size = 0;
        try {
            File[] fileList = file.listFiles();
            for (int i = 0; i < fileList.length; i++) {
                // 如果下面还有文件
                if (fileList[i].isDirectory()) {
                    size = size + getFolderSize(fileList[i]);
                } else {
                    size = size + fileList[i].length();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }

    /**
     * 格式化单位
     *
     * @param size
     * @return
     */
    public static String getFormatSize(double size) {
        double kiloByte = size / 1024;
        if (kiloByte < 1) {
            // return size + "Byte";
            return "0M";
        }

        double megaByte = kiloByte / 1024;
        if (megaByte < 1) {
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "KB";
        }

        double gigaByte = megaByte / 1024;
        if (gigaByte < 1) {
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "MB";
        }

        double teraBytes = gigaByte / 1024;
        if (teraBytes < 1) {
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "GB";
        }
        BigDecimal result4 = new BigDecimal(teraBytes);
        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString()
                + "TB";
    }

    /**
     * 获取当前时间
     *
     * @param
     * @return
     */
    public static String getNowEDate() {
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd 05:00:00");
        String date = sDateFormat.format(new Date());
        return date;
    }

    /**
     * 验证手机号
     *
     * @param mobiles
     * @return
     */
    public static boolean isMobileNO(String mobiles) {
        Pattern p = Pattern
                .compile("^((13[0-9])|(15[^4,\\D])|(17[^4,\\D])|(18[0-9]))\\d{8}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    /**
     * 验证是否是数字
     *
     * @param str
     * @return
     */
    public static boolean isNumber(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher match = pattern.matcher(str);
        if (match.matches() == false) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 验证是否是字母
     */
    public static boolean isLeter(String s) {
        char c = s.charAt(0);
        int i = (int) c;
        if ((i >= 65 && i <= 90) || (i >= 97 && i <= 122)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断是否是特殊字符
     */
    public static boolean checkTS(String str) {
        String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Pattern mPattern = Pattern.compile(regEx);
        Matcher mMatcher_older = mPattern.matcher(str);
        if (!mMatcher_older.find()) {
            return true;
        }
        return false;
    }

    public static boolean IsChinese(String strName) {
        char[] ch = strName.toCharArray();
        for (int i = 0; i < ch.length; i++) {
            char c = ch[i];
            if (isChinese(c)) {
                return true;
            }
        }
        return false;
    }

    // 根据Unicode编码完美的判断中文汉字和符号
    private static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION) {
            return true;
        }
        return false;
    }

    /**
     * 判断是否为汉字
     */
    public static boolean isChinese(String str) {

        char[] chars = str.toCharArray();
        boolean isGB2312 = false;
        for (int i = 0; i < chars.length; i++) {
            byte[] bytes = ("" + chars[i]).getBytes();
            if (bytes.length == 2) {
                int[] ints = new int[2];
                ints[0] = bytes[0] & 0xff;
                ints[1] = bytes[1] & 0xff;

                if (ints[0] >= 0x81 && ints[0] <= 0xFE && ints[1] >= 0x40
                        && ints[1] <= 0xFE) {
                    isGB2312 = true;
                    break;
                }
            }
        }
        return isGB2312;
    }

    /**
     * date转String yyyy-MM-dd HH:mm:ss
     *
     * @param date
     * @return
     */
    public static String getStringDate(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(date);
        return dateString;
    }

    /**
     * date转String yyyy-MM-dd
     *
     * @param dateStr
     * @return
     */
    public static String get3Date(String dateStr, int statu) {
        Date date = getDateTime(dateStr);
        String dateString = "";
        SimpleDateFormat formatter = null;
        switch (statu) {
            case 1:
                formatter = new SimpleDateFormat("yyyy年MM月dd日");
                break;
            case 2:
                formatter = new SimpleDateFormat("HH:mm");
                break;
            case 3:
                formatter = new SimpleDateFormat("yyyy-MM-dd");
                break;
            case 4:
                formatter = new SimpleDateFormat("HH:mm:ss");
                break;
        }
        dateString = formatter.format(date);
        return dateString;
    }

    public static Date getDateTime(String dateStr) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = dateFormat.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static Date getDateDay(String dateStr) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        Date date = null;
        try {
            date = dateFormat.parse(dateStr);
            System.out.println(date.toLocaleString().split(" ")[0]);//切割掉不要的时分秒数据
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static long getDateDaySure2(long number) {
        String str = ("" + number).substring(0, 14);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = null;
        try {
            date = dateFormat.parse(str);
            System.out.println(date.toLocaleString().split(" ")[0]);//切割掉不要的时分秒数据
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date.getTime();
    }

    public static String getDateDays(String dateStr, int statu) {//20170628  000000
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = null;
        try {
            date = dateFormat.parse(dateStr);
            Log.e("VDSVNDSLVD", "======================" + date.getDay());
            System.out.println(date.toLocaleString().split(" ")[0]);//切割掉不要的时分秒数据
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String dateString = "";
        SimpleDateFormat formatter = null;

        switch (statu) {
            case 0:
                formatter = new SimpleDateFormat("yyyy-MM-dd");
                break;
            case 1:
                formatter = new SimpleDateFormat("HH:mm");
                break;
        }
        dateString = formatter.format(date);
        return dateString;
    }

    public static String getFormatTime(Date date, int statu) {
        if (date == null) {
            return "";
        }
        String dateString = "";
        SimpleDateFormat formatter = null;
        switch (statu) {
            case 1:
                formatter = new SimpleDateFormat("yyyy");
                dateString = formatter.format(date);
                break;
            case 2:
                formatter = new SimpleDateFormat("MM");
                dateString = formatter.format(date);
                break;
            case 3:
                formatter = new SimpleDateFormat("dd");
                dateString = formatter.format(date) + "";
                break;
            case 4:
                formatter = new SimpleDateFormat("yyyy-MM-dd");
                dateString = formatter.format(date);
                break;
            case 5:
                formatter = new SimpleDateFormat("yyyy年MM月dd日");
                dateString = formatter.format(date);
                break;
            case 6:
                formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                dateString = formatter.format(date);
                break;
            case 7:
                formatter = new SimpleDateFormat("HH:mm:ss");
                dateString = formatter.format(date);
                break;
            case 8:
                formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                dateString = formatter.format(date);
                break;
        }
//        dateString = formatter.format(date);
        return dateString;
    }


    public static String replareZero(String str) {
        if (str.equalsIgnoreCase("0") || str.contains("0.00")) {
            str = "--";
        }
        return str;

    }

    public static String replareZeroFloat(String str) {
        float dataFloat = Float.parseFloat(str);
        if (dataFloat == 0) {
            str = "--";
        }
        return str;

    }

    public static String dataShows(String newStr, String dataStr) {
        if (newStr.equalsIgnoreCase("--")) {
            dataStr = replareZero(dataStr);
        }
        return dataStr;
    }

    public static String showDay(Date createdAt, int number) {

        String times = "";
        try {
            SimpleDateFormat newSDF = null;
            switch (number) {
                case 1:
                    newSDF = new SimpleDateFormat("dd/MM/yyyy");
                    times = newSDF.format(createdAt);
                    break;
                case 2:
                    newSDF = new SimpleDateFormat("MM/dd/yyyy  HH:mm");
                    times = newSDF.format(createdAt);
                    break;
                case 3:
                    Calendar calendar = new GregorianCalendar();
                    calendar.setTime(createdAt);
                    calendar.add(calendar.DATE, -1);//把日期往后增加一天.整数往后推,负数往前移动
                    createdAt = calendar.getTime();   //这个时间就是日期往后推一天的结果
                    newSDF = new SimpleDateFormat("yyyyMMdd");
                    times = newSDF.format(createdAt);
                    break;
                case 4:
                    newSDF = new SimpleDateFormat("yyyyMMdd");
                    times = newSDF.format(createdAt);
                    break;
            }
        } catch (Exception e) {
        }
        return times;
    }

    /**
     * compute Sample Size
     *
     * @param options
     * @param minSideLength
     * @param maxNumOfPixels
     * @return
     */
    public static int computeSampleSize(BitmapFactory.Options options,
                                        int minSideLength, int maxNumOfPixels) {
        int initialSize = computeInitialSampleSize(options, minSideLength,
                maxNumOfPixels);

        int roundedSize;
        if (initialSize <= 8) {
            roundedSize = 1;
            while (roundedSize < initialSize) {
                roundedSize <<= 1;
            }
        } else {
            roundedSize = (initialSize + 7) / 8 * 8;
        }

        return roundedSize;
    }

    /**
     * compute Initial Sample Size
     *
     * @param options
     * @param minSideLength
     * @param maxNumOfPixels
     * @return
     */
    private static int computeInitialSampleSize(BitmapFactory.Options options,
                                                int minSideLength, int maxNumOfPixels) {
        double w = options.outWidth;
        double h = options.outHeight;

        // 上下限范围
        int lowerBound = (maxNumOfPixels == -1) ? 1 : (int) Math.ceil(Math
                .sqrt(w * h / maxNumOfPixels));
        int upperBound = (minSideLength == -1) ? 128 : (int) Math.min(
                Math.floor(w / minSideLength), Math.floor(h / minSideLength));

        if (upperBound < lowerBound) {
            // return the larger one when there is no overlapping zone.
            return lowerBound;
        }

        if ((maxNumOfPixels == -1) && (minSideLength == -1)) {
            return 1;
        } else if (minSideLength == -1) {
            return lowerBound;
        } else {
            return upperBound;
        }
    }

    /**
     * 对double保留两位小数.不足用0补齐
     *
     * @param value double数据.
     * @return 精度计算后的数据.
     */
    public static String DoubleRound(double value) {
        DecimalFormat df = new DecimalFormat("#,##0.00");
        return df.format(value);
    }

    /**
     * 对float保留两位小数.不足用0补齐
     *
     * @param value double数据.
     * @return 精度计算后的数据.
     */
    public static String FloatRound(float value) {
        DecimalFormat df = new DecimalFormat("#,##0.00");
        return df.format(value);
    }

    /**
     * 将double取余两位
     *
     * @param d
     * @param textView
     * @param ending   如需要加%
     */
    public static void setTextPrice(Double d, TextView textView, String ending) {
        textView.setText(DoubleRound(d) + ending);
        if (d < 0) {
            textView.setTextColor(Color.parseColor("#5DA81A"));
        } else if (d > 0) {
            textView.setTextColor(Color.parseColor("#e94f53"));
        } else {
            textView.setTextColor(Color.parseColor("#999999"));
        }
    }

    public static String listToString(List<String> list) {
        if (list == null) {
            return null;
        }
        StringBuilder result = new StringBuilder();
        boolean first = true;
        //第一个前面不拼接","
        for (String string : list) {
            if (first) {
                first = false;
            } else {
                result.append(",");
            }
            result.append(string);
        }
        return result.toString();
    }

    public static List<String> stringToList(String strs) {
        String str[] =
                strs.split(",");
        return Arrays.asList(str);
    }


    public static void saveBitmap(Context context, Bitmap bitmap, String bitName) throws IOException {
        File file = new File(getSystemFilePath(context, Environment.DIRECTORY_PICTURES) + "/" + bitName);
        File fileParent = file.getParentFile();
        if (!fileParent.exists()) {
            fileParent.mkdirs();
        }
        file.createNewFile();
        FileOutputStream out;
        try {
            out = new FileOutputStream(file);
            if (bitmap.compress(Bitmap.CompressFormat.PNG, 90, out)) {
                out.flush();
                out.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getSystemFilePath(Context context, String FilesDirType) {
        String cachePath;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            cachePath = context.getExternalFilesDir(FilesDirType).getAbsolutePath();
        } else {
            cachePath = context.getFilesDir().getAbsolutePath();
        }
        return cachePath;
    }

    /**
     * 简单验证输入的身份证号是否合法
     */
    public static boolean isLegalId(String id) {
        if (id.toUpperCase().matches("(^\\d{15}$)|(^\\d{17}([0-9]|X)$)")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 获得指定日期的前一天
     *
     * @param date
     * @return
     * @throws Exception
     */
    public static Date getSpecifiedDayBefore(Date date) {//可以用new Date().toLocalString()传递参数
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int day = c.get(Calendar.DATE);
        c.set(Calendar.DATE, day - 1);
        return c.getTime();
    }

    /**
     * 获得指定日期的前N天
     *
     * @param date
     * @return
     * @throws Exception
     */
    public static Date getSpecifiedDayBefore(Date date, int N) {//可以用new Date().toLocalString()传递参数
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int day = c.get(Calendar.DATE);
        c.set(Calendar.DATE, day - N);
        return c.getTime();
    }

    @BindingAdapter({"imageUrl"})
    public static void imageLoader(ImageView imageView, int url) {
        Glide.with(imageView.getContext())
                .load(url)
                .into(imageView);
    }

    /**
     * 关闭软键盘
     *
     * @param context
     */
    public static void hintKeyboard(Activity context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive() && context.getCurrentFocus() != null) {
            if (context.getCurrentFocus().getWindowToken() != null) {
                imm.hideSoftInputFromWindow(context.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

    public static void removeDuplicatedItem(){

    }
}
