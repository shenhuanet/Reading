package com.shenhua.reading.utils;

/**
 * Created by shenhua on 2016/3/29.
 */
public class MyStringUtils {

    public static final String DB_NAME = "History.db";
    public static final String TABLE_NAME = "history";
    public static final int DB_VERSION = 1;
    public static final int TYPE_CSDN = 1;
    public static final int TYPE_SENG = 2;
    public static final int TYPE_JCODE = 3;
    public static final int TYPE_TUIKU = 4;
    public static final int TYPE_HONGHEI = 5;
    public static final int TYPE_KAIYUAN = 6;
    public static final int TYPE_KAIFAZHE = 7;
    public static final int TYPE_KAN_DAIMA = 8;
    public static final int TYPE_KAN_ZUJIAN = 9;
    public static final int TYPE_OPEN = 10;

    public static final String[] tab_name_array = {"首页", "CSDN", "SegmentFault", "Jcode", "推酷", "红黑联盟", "开源中国", "开发者头条", "看源代码", "看源组件", "深度开源"};
    public static final String URL_KANYUAN_DAIMA = "http://www.see-source.com/android/list.html";//安卓代码分享
    public static final String URL_KANYUAN_ZUJIAN = "http://www.see-source.com/androidwidget/list.html";//安卓组件中心
    public static final String URL_KAIFAZHE = "http://m.toutiao.io/";//开发者头条
    public static final String URL_KAIYUAN = "http://www.oschina.net/blog/";//开源中国博客
    public static final String URL_HONGHEI = "http://www.2cto.com/kf/yidong/Android/news/";//红黑联盟
    public static final String URL_TUIKU = "http://www.tuicool.com/ah";//推酷
    public static final String URL_SEGF = "http://segmentfault.com/t/android/blogs";//segmentfault
    public static final String URL_CSDN_YD = "http://blog.csdn.net/mobile/newest.html";//CSDN移动
    public static final String URL_CSDN_YD_M = "http://m.blog.csdn.net/article/details?id=";
    //    public static final String URL_CSDN_ANDROID = "http://blog.csdn.net/tag/details.html?tag=android";//CSDN android
    public static final String URL_JCODE = "http://www.jcodecraeer.com/plus/list.php?tid=16";//泡在网上的日子
    public static final String URL_OPEN = "http://www.open-open.com";//深度开源

    public static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.110 Safari/537.36";
    public static String[] RandomColors = {"#F44336", "#E91E63", "#9C27B0", "#2196F3", "#03A9F4", "#00BCD4", "#009688", "#4CAF50", "#8BC34A", "#CDDC39", "#FFEB3B", "#FFC107", "#FF9800", "#FF5722", "#795548", "#9E9E9E", "#607D8B"};

    public static String toUtf8String(String s) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c >= 0 && c <= 255) {
                stringBuffer.append(c);
            } else {
                byte[] b;
                try {
                    b = String.valueOf(c).getBytes("utf-8");
                } catch (Exception ex) {
                    System.out.println(ex);
                    b = new byte[0];
                }
                for (int j = 0; j < b.length; j++) {
                    int k = b[j];
                    if (k < 0)
                        k += 256;
                    stringBuffer.append("%" + Integer.toHexString(k).toUpperCase());
                }
            }
        }
        String result = stringBuffer.toString();
        result = result.replaceAll(" +", "+");
        return result;
    }

}
