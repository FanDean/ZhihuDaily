package com.fandean.zhihudaily.util;

import android.content.Context;
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

}
