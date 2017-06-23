package com.fandean.zhihudaily.bean;

import java.util.List;

/**
 * Created by fan on 17-6-17.
 */

public class ZhihuNews {

    /**
     * date : 20170617
     * stories : []
     * top_stories : [{}]
     */

    //日期
    private String date;
    //当日新闻
    private List<StoriesBean> stories;
    //热门新闻
    private List<TopStoriesBean> top_stories;



    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<StoriesBean> getStories() {
        return stories;
    }

    public void setStories(List<StoriesBean> stories) {
        this.stories = stories;
    }

    public List<TopStoriesBean> getTop_stories() {
        return top_stories;
    }

    public void setTop_stories(List<TopStoriesBean> top_stories) {
        this.top_stories = top_stories;
    }

    public static class StoriesBean {
        /**
         * images : ["https:\\/\\/pic4.zhimg.com\\/v2-5d0f262b77032fbcba5c5927731ba2e7.jpg"]
         * type : 0
         * id : 9474154
         * ga_prefix : 061708
         * title : 走进这个满是魔幻气息的岛屿，就像活在了史诗级大片里
         */

        private int type;
        //新闻内容id
        private int id;
        //供 Google Analytics 使用
        private String ga_prefix;
        //新闻标题
        private String title;
        //新闻图片地址
        private List<String> images;

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getGa_prefix() {
            return ga_prefix;
        }

        public void setGa_prefix(String ga_prefix) {
            this.ga_prefix = ga_prefix;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public List<String> getImages() {
            return images;
        }

        public void setImages(List<String> images) {
            this.images = images;
        }
    }

    public static class TopStoriesBean {
        /**
         * image : https:\/\/pic2.zhimg.com\/v2-1384cae182fc2b7b1c6e2923254f5ac5.jpg
         * type : 0
         * id : 9479653
         * ga_prefix : 061707
         * title :
         */

        private String image;
        private int type;
        private int id;
        private String ga_prefix;
        private String title;

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getGa_prefix() {
            return ga_prefix;
        }

        public void setGa_prefix(String ga_prefix) {
            this.ga_prefix = ga_prefix;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
