package com.fandean.zhihudaily.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import java.util.GregorianCalendar;

/**
 * Created by fan on 17-6-17.
 */

public class Utility {

    private static final String ANDROID_RESOURCE = "android.resource://";
    private static final String FOREWARD_SLASH = "/";
    //缓存过期时间间隔，毫秒值。 4小时 1000 * 60 * 4 * 60
    public static final int INTERVALS = 1000  * 60  * 3;


    /**
     * 从资源id转换成Uri，可供Glide等使用
     * @param context
     * @param resourceId
     * @return
     */
    public static Uri resourceIdToUri(Context context, int resourceId){
        return Uri.parse(ANDROID_RESOURCE + context.getPackageName() + FOREWARD_SLASH + resourceId);
    }


    /**
     * 判断传入的时间值对比当前时间是否已经过期
     * @param time 时间毫秒值
     * 最大期限，毫秒，24*60*60*1000一天的毫秒数
     * @return
     */
    public static Boolean isExpired(long time){
        GregorianCalendar now = new GregorianCalendar();
        Log.d("FanDean", "比较值为：" + time + "。当前值为：" + now.getTimeInMillis());
        long i = now.getTimeInMillis() - time;
        if (i >= INTERVALS){
            Log.d("FanDean","isExpired() 数据已经过期，时间间隔为: "
                    + i/1000/60 + " 分钟。预设值为：" + INTERVALS/1000/60 + "分钟。" );
            return true;
        } else {
            //没过期
            return false;
        }
    }

    //分享App，同时分析文字和图片
    public static void shareApp(Context context){
        String msg = "给大家介绍一个看新闻的APP，ZhihuDaily";
        Intent share = new Intent(android.content.Intent.ACTION_SEND);
//        Uri uri = Uri.parse("https://github.com/FanDean/ZhihuDaily/raw/master/pictures/ZhihuDaily.jpg");
//        Uri uri = resourceIdToUri(context,R.drawable.ic_zhi);
//        share.putExtra(Intent.EXTRA_STREAM,uri);
//        share.setType("image/*");
        share.setType("text/plain");
        //当用户选择短信时使用 sms_body 取得文字
        share.putExtra("sms_body",msg);
        share.putExtra(Intent.EXTRA_TEXT,msg);
        //自定义选择框的标题
        context.startActivity(Intent.createChooser(share, "邀请好友"));
    }

}
