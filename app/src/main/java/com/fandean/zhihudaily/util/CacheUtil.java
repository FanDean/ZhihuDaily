package com.fandean.zhihudaily.util;

import android.content.Context;
import android.os.Environment;

import com.bumptech.glide.Glide;

import java.io.File;

import static com.fandean.zhihudaily.util.HttpUtil.OKHTTP_CACHE_DIR_NAME;

/**
 *   应用内数据的所有路径：
 * /data/data/com.xxx.xxx/cache - 应用内缓存（注：对应方法getCacheDir()）
 * /data/data/com.xxx.xxx/databases - 应用内数据库
 * /data/data/com.xxx.xxx/shared_prefs - 应用内配置文件
 * /data/data/com.xxx.xxx/files - 应用内文件（注：对应方法getFilesDir()，该方法没有参数)
 *
 * 获取外部Cache路径，通过getExternalCacheDir()获取；注意先判断是否存在外置SD卡
 * 获取外部file路径，通过getExternalFilesDir(String type)获取， type为特定类型，比如 Environment.DIRECTORY_MUSIC
 *
 * 清除缓存：由于不是非常了解各目录中的数据是否是可清除的（特别是databases目录），所以只清除已知应用的相关缓存。
 *
 * Glide自带清除缓存的功能,分别对应Glide.get(context).clearDiskCache();(清除磁盘缓存)与Glide.get(context).clearMemory();(清除内存缓存)
 * 两个方法.其中clearDiskCache()方法必须运行在子线程,clearMemory()方法必须运行在主线程,这是这两个方法所强制要求的,详见源码.
 *
 * WebView 控件,当加载html 页面时,会在/data/data/package_name目录下生成database与cache 两个文件夹。
 * 请求的url 记录是保存在WebViewCache.db,而url 的内容是保存在WebViewCache 文件夹下
 *
 * 清除Retrofit/OkHttp3缓存
 *
 *
 * Created by fan on 17-6-30.
 */

public class CacheUtil {

    /**
     * 判断外部SD卡是否存在
     * @param context
     * @return
     */
    public static boolean isExternalStorageExist(Context context){
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()){
            return true;
        }
        return false;
    }


    /**
     * 获取app缓存路径，如果外部SD卡路径不存在则返回内部缓存路径
     * 与 Environment.getExternalStorageDirectory().getPath() 作用相似，但返回的路径不同；这里是缓存路径
     * @param context
     * @return
     */
    public String getCachePath( Context context ){
        String cachePath ;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            //外部存储可用
            cachePath = context.getExternalCacheDir().getPath() ;
        }else {
            //外部存储不可用
            cachePath = context.getCacheDir().getPath() ;
        }
        return cachePath ;
    }


    /**
     * 删除Glide的磁盘缓存，此方法必须在后台线程中调用
     * @param context
     */
    public static void clearGlideCache(Context context){
        //删除Glide磁盘中的缓存，不删除内存中的缓存
        //Glide.get()源码中有一句 Context applicationContext = context.getApplicationContext();
        // 所以传入普通的Context即可

        //clearDiskCache()必须在后台线程中调用，见doc
        Glide.get(context).clearDiskCache();
    }

    /**
     * 删除WebView缓存的数据库文件
     * @param context
     */
    public static void clearWebWiewDBCache(Context context){
        //CacheManager.getCacheFileBaseDir(); 已经不能使用
        //WebView的缓存文件使用mWebview.clearCache(true);

        context.deleteDatabase("WebView.db");
        context.deleteDatabase("WebViewCache.db");
    }

    /**
     * 删除OkHttp3的缓存
     * @param context
     */
    public static void clearOkHttp3Cache(Context context){
        //路径
        File httpCacheDirectory = new File(context.getCacheDir(), OKHTTP_CACHE_DIR_NAME);
        deleteFolder(httpCacheDirectory);
    }


    /**
     * 此处计算的是整个cache目录的大小，而其他删除方法只是删除部分cache文件夹下的目录
     * @param context
     * @return
     */
    public static String getCacheSize(Context context){
        long size = 0;
        try {
            size += getFolderSize(context.getCacheDir());
            size += getFolderSize(context.getExternalCacheDir());
        } catch (Exception e){
            e.printStackTrace();
        }
        return formatFileSize(size);
    }


    /**
     * 获取指定文件夹内所有文件大小的和
     */
    public static long getFolderSize(File file) throws Exception {
        long size = 0;
        if (file!= null && file.isDirectory()) {
            try {
                for (File aFileList : file.listFiles()) {
                    if (aFileList.isDirectory()) {
                        //递归调用
                        size += getFolderSize(aFileList);
                    } else {
                        size += aFileList.length();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (file != null && file.isFile()){
            size = file.length();
        }
        return size;
    }


    /**
     * 转换文件大小
     * @param fileS
     * @return B/KB/MB/GB
     */
    public static String formatFileSize(long fileS) {
        //TODO 学习 DecimalFormat
        java.text.DecimalFormat df = new java.text.DecimalFormat("#.00");
        String fileSizeString = "";
        if (fileS < 1024) {
            fileSizeString = df.format((double) fileS) + "B";
        } else if (fileS < 1048576) {
            fileSizeString = df.format((double) fileS / 1024) + "KB";
        } else if (fileS < 1073741824) {
            fileSizeString = df.format((double) fileS / 1048576) + "MB";
        } else {
            fileSizeString = df.format((double) fileS / 1073741824) + "G";
        }
        return fileSizeString;
    }


    /**
     * 删除文件夹下的所有文件和目录；如果传入的是文件则删除该文件
     */
    public static void deleteFolder(File file){
        //该方法只能删除文件或空目录
        // file.delete()
        if (file!= null && file.isDirectory()){
            try {
                for (File child:file.listFiles()){
                    if (child.isDirectory()) {
                        //递归调用，退出条件为child不是目录
                        deleteFolder(child);
                    }
                    child.delete();
                }
            } catch(Exception e) {
                e.printStackTrace();
            }
        } else if (file != null && file.isFile()){
            file.delete();
        }
    }
}
