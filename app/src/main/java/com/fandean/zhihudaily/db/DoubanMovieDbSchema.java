package com.fandean.zhihudaily.db;

/**
 * 废弃
 * TODO: 豆瓣未做缓存
 * Created by fan on 17-6-18.
 */

public class DoubanMovieDbSchema {
    //表1：
    public static final class DoubanMovieInTheaters{
        public static final String NAME = "doubanMovieInTheaters";

        public static final class Cols{
            public static final String ID = "id";
            //该条数据的创建时间
            public static final String CREATETIME = "create_time";
            //新闻日期
//            public static final String NEWSDATE = "news_date";
            public static final String JSON = "json";
        }
    }

    //表2：
    public static final class DoubanMovies{
        public static final String NAME = "doubanMovies";

        public static final class Cols{
            public static final String ID = "id";
            public static final String JSON = "json";
        }
    }


}
