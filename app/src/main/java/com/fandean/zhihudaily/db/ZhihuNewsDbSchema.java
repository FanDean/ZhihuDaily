package com.fandean.zhihudaily.db;

/**
 * 除非使用ORM，否则保存实时数据到数据库真是灾难，只保存收藏数据好了。
 * Created by fan on 17-6-18.
 */

public class ZhihuNewsDbSchema {

    //TODO 使用主外键关联 ZhihuNewses 和 ZhihuStory
    //使用该类(静态不可继承的内部类)来描述数据库表 ZhihuNewses
    //直接保存stories列表，外加
//    public static final class ZhihuNewses{
//        public static final String NAME = "zhihuNewses";
//
//        public static final class Cols{
//            //自动增长的主键id
////            public static final String ID = "id";
//            //该条数据的创建时间
//            public static final String CREATETIME = "create_time";
//            //新闻日期
//            public static final String NEWSDATE = "news_date";
//            //Json
//            public static final String JSON = "json";
//
//            /*
//            //新闻日期
//            public static final String NEWSDATE = "news_date";
//            public static final String STORYID = "story_id";
//            public static final String TITEL = "title";
//            //只保存一条图片地址
//            public static final String IMAGEURL = "image_url";
//            */
//        }
//    }
//
//    //数据表2：
//    public static final class ZhihuStories{
//        public static final String NAME = "zhihuStory";
//
//        public static final class Cols{
//            //外键
////            public static final String ID = "id";
//            //该条数据的创建时间
//            public static final String CREATETIME = "create_time";
//            //新闻日期
//            public static final String NEWSDATE = "news_date";
//            //Json
//            public static final String JSON = "json";
//
///*            //新闻日期
//            public static final String NEWSDATE = "news_date";
//            public static final String BODY = "body";
//            public static final String TITEL = "title";
//            public static final String IMAGEURL = "image_url";
//            public static final String SHARE_URL = "share_url";
//            public static final String STORYID = "story_id";
//            public static final String JS = "js";
//            public static final String CSS = "css";*/
//        }
//    }

    //数据表3： 保存收藏的数据(包括知乎和豆瓣)
    public static final class Favorites {
        public static final String NAME = "Favorites";

        public static final class Cols{
            //主键，自动增长，另可用于对收藏排序
            public static final String ID = "id";
            //知乎或豆瓣对应的id值
            public static final String CONTENT_ID = "content_id";
            //int类型 0代表知乎，1代表豆瓣
            public static final String TYPE = "type";
            //详细信息的url，用于跳转到详情页面
            public static final String URL = "url";
            //以下为显示信息
            //标题
            public static final String TITEL = "title";
            //只保存一条图片地址
            public static final String IMAGEURL = "image_url";
        }
    }
}
