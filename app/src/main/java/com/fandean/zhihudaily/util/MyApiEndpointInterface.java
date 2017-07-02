package com.fandean.zhihudaily.util;

import com.fandean.zhihudaily.bean.DoubanMovie;
import com.fandean.zhihudaily.bean.DoubanMovieInTheaters;
import com.fandean.zhihudaily.bean.ZhihuNews;
import com.fandean.zhihudaily.bean.ZhihuStory;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

/**
 * REST API形式的接口
 * Call<T> get();必须是这种形式,这是2.0之后的新形式
 * 这些函数会通过get方法区请求服务器对应路径的资源，并把结果封装为对应的返回类型(Java对象)。
 *
 *  @GET("before/{date}") 请求的URL可以根据函数参数动态更新。一个可替换的区块为用 {  和  } 包围的字符串，
 *  而函数参数必需用  @Path 注解表明，并且注解的参数为同样的字符串
 *
 *  在URL路径中也可以指定URL参数： @GET("/users/list?sort=desc")
 *
 *  返回值：
 *  如果要直接获取HTTP返回的对象，使用Response对象(OkHttp3中的)。 这里使用的是OkHttp3中的ResponseBody
 *  可以设置转换器
 * Created by fan on 17-6-18.
 */

public interface MyApiEndpointInterface {
    //ResponseBody是Okhttp3 中的

    /*
     * 知乎日报
     * baseUrl: https://news-at.zhihu.com/api/4/news/
     * 60 × 4 = 4分钟
     */
    @Headers("Cache-Control: public, max-age=240")
    @GET("latest")
    Call<ZhihuNews> getLatestZhihuNews();

    //86400表示一天
    @Headers("Cache-Control: public, max-age=86400")
    @GET("{id}")
    Call<ZhihuStory> getZhihuStory(@Path("id") int storyId);

    //1小时
    @Headers("Cache-Control: public, max-age=3600")
    @GET("before/{date}")
    Call<ZhihuNews> getBeforeZhihuNews(@Path("date") String date);

    //豆瓣
    //baseUrl: https://api.douban.com/v2/movie/
    //通过firefox获取响应数据，查看响应头
    //https://api.douban.com/v2/movie/in_theaters?city=北京&start=0&count=100
    //多个查询参数使用@QueryMap更加灵活，也可使用单个@Query
    @Headers("Cache-Control: public, max-age=3600")
    @GET("in_theaters")
    Call<DoubanMovieInTheaters> getDoubanMovieInTheaters(@QueryMap Map<String,String> options);


    // DOUBAN_MOVIE = "http://api.douban.com/v2/movie/subject/"
    @Headers("Cache-Control: public, max-age=600")
    @GET("subject/{id}")
    Call<DoubanMovie> getDoubanMovie(@Path("id") int movieId);
}
