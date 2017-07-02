package com.fandean.zhihudaily.util;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by fan on 17-6-23.
 */

public class HttpUtil {
    public static final String LOG_TAG = "知乎Daily:Okhttp3 log ->";
    public static final String DOUBSN_BASE_URL = "https://api.douban.com/v2/movie/";
    public static final String ZHIHU_BASE_URL = "https://news-at.zhihu.com/api/4/news/";
    public static final String OKHTTP_CACHE_DIR_NAME = "zhihudailyokhttp3cache";



    //另请求头部的 max-age 在 MyApiEndpointInterface 中手动添加
    public static MyApiEndpointInterface getRetrofitClient(final Context context, final String baseUrl){
        Retrofit mRetrofit;
        OkHttpClient mOkHttpClient;
        //1. 设置缓存路径。或者使用 getCacheDir()
//        File httpCacheDirectory = new File(context.getExternalCacheDir(), OKHTTP_CACHE_DIR_NAME);
//        File httpCacheDirectory = new File(context.getCacheDir(), OKHTTP_CACHE_DIR_NAME);
        File httpCacheDirectory = new File(context.getCacheDir(), OKHTTP_CACHE_DIR_NAME);

        //设置缓存 10M，如果超过此大小，OkHttp3将会使用相应算法进行清除
        Cache cache = new Cache(httpCacheDirectory, 10 * 1024 * 1024);

        //以匿名内部类的形式，实现过滤器，并在该过滤器中同时进行重写请求和重写响应
        Interceptor REWRITE_CACHE_CONTROL_INTERCEPTOR = new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                //以下是重写请求的操作
                Request originalRequest = chain.request();
                NetworkState networkState = new NetworkState(context);
                if (!networkState.isNetWorkConnected()){
                    //没有连网
                    originalRequest = originalRequest.newBuilder()
                            .cacheControl(CacheControl.FORCE_CACHE)
                            .build();
                }

                //以下是重写响应的操作
                okhttp3.Response originalResponse = chain.proceed(originalRequest);
                if (networkState.isNetWorkConnected()){
                    //有网的时候读接口上的@Headers里的配置，你可以在这里进行统一的设置
                    String cacheControl = originalRequest.cacheControl().toString();
                    return originalResponse.newBuilder()
                            .header("Cache-Control",cacheControl)
                            //清除响应体对Cache有影响的信息
                            .removeHeader("Pragma")
                            .build();
                } else {
                    //无网络时
                    return originalResponse.newBuilder()
                            //一周时间
                            .header("Cache-Control","public,only-if-cached,max-stale=604800")
                            .removeHeader("Pragma")
                            .build();

                }
            }
        };


        //实现一个日志过滤器
        Interceptor loggingInterceptor = new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                long t1 = System.nanoTime();
                Log.i(LOG_TAG,String.format("Sending request %s on %s%n%s", request.url(),  chain.connection(), request.headers()));
                okhttp3.Response response = chain.proceed(request);
                long t2 = System.nanoTime();
                Log.i(LOG_TAG,String.format("Received response for %s in %.1fms%n%s", response.request().url(), (t2 - t1) / 1e6d, response.headers()));
                return response;
            }
        };

        //需要导入 compile 'com.squareup.okhttp3:logging-interceptor:3.8.0'
//        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
//        logging.setLevel(HttpLoggingInterceptor.Level.BODY);


        mOkHttpClient = new OkHttpClient.Builder()
                //连接超时设置
                .connectTimeout(10, TimeUnit.SECONDS)
                // 读取超时时间设置
                .readTimeout(10, TimeUnit.SECONDS)
                //失败重试
                .retryOnConnectionFailure(true)
                //设置缓存。也可以在后面使用mOkHttpClient.setCache(cache)的方法。
                .cache(cache)
                //设置拦截器。同时设置两个（是否需要设置两个还未知）另拦截器是按顺序执行的
                .addInterceptor(loggingInterceptor)
                .addInterceptor(REWRITE_CACHE_CONTROL_INTERCEPTOR)
                .addNetworkInterceptor(REWRITE_CACHE_CONTROL_INTERCEPTOR)
                //可以选择设置日志拦截器
                .build();




        mRetrofit = new Retrofit.Builder() //新建一个构建器来设置参数
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(mOkHttpClient)
                .build(); //构建器的此方法生成Retrofit对象

        return mRetrofit.create(MyApiEndpointInterface.class);
    }
}
