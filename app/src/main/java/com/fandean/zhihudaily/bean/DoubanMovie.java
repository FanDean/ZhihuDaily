package com.fandean.zhihudaily.bean;

import java.util.List;

/**
 * http://api.douban.com/v2/movie/subject/11803087
 * 该api返回的json格式可能会变，（与之前的不一样）
 * Created by fan on 17-6-17.
 */

public class DoubanMovie {

    /**
     * rating : {"max":10,"average":7.5,"stars":"40","min":0}
     * reviews_count : 361
     * wish_count : 32474
     * douban_site : https://site.douban.com/foxmovies/
     * year : 2017
     * images : {"small":"http://img7.doubanio.com/view/movie_poster_cover/ipst/public/p2459944375.webp","large":"http://img7.doubanio.com/view/movie_poster_cover/lpst/public/p2459944375.webp","medium":"http://img7.doubanio.com/view/movie_poster_cover/spst/public/p2459944375.webp"}
     * alt : https://movie.douban.com/subject/11803087/
     * id : 11803087
     * mobile_url : https://movie.douban.com/subject/11803087/mobile
     * title : 异形：契约
     * do_count : null
     * share_url : http://m.douban.com/movie/subject/11803087
     * seasons_count : null
     * schedule_url : https://movie.douban.com/subject/11803087/cinema/
     * episodes_count : null
     * countries : ["美国"]
     * genres : ["科幻","惊悚","恐怖"]
     * collect_count : 23635
     * casts : [{"alt":"https://movie.douban.com/celebrity/1000010/","avatars":]
     * current_season : null
     * original_title : Alien: Covenant
     * summary : “科幻之父”雷德利-斯科特将为他所开创的《异形》系列带来新篇章。《异形：契约》的故事发生在《普罗米修斯》
     * 10年后，一群新的宇航员乘坐着“契约号”飞船前往遥远的星系寻找殖民地，他们来到一处看似天堂般的星球，
     * 实则是黑暗、危险的地狱，在那里他们见到了“普罗米修斯”号唯一的幸存者——由迈克尔·法斯宾德饰演的生化人大卫，
     * 一场毁灭性的巨大灾难即将到来。
     * subtype : movie
     * directors : [{"alt":"https://movie.douban.com/celebrity/1054416/","avatars":{"small"]
     * comments_count : 11150
     * ratings_count : 22053
     * aka : ["异形：圣约(港/台)","神奇异形在哪里(豆友译名)","已开：大勺(豆友译名)","异形：失乐园","普罗米修斯2",]
     */

    private DoubanMovieInTheaters.SubjectsBean.RatingBean rating;
    //影评（长影评）数量
    private int reviews_count;
    //多少人想看
    private int wish_count;
    private String douban_site;
    private String year;
    private DoubanMovieInTheaters.SubjectsBean.MoviePosterCoverBean images;
    private String alt;
    private String id;
    private String mobile_url;
    private String title;
    private Object do_count;
    private String share_url;
    private Object seasons_count;
    private String schedule_url;
    private Object episodes_count;
    //多少人看过
    private int collect_count;
    //null
    private Object current_season;
    //原电影标题
    private String original_title;
    //电影介绍
    private String summary;
    //豆瓣分类，比如这是movie
    private String subtype;
    //短评数量
    private int comments_count;
    //评分人数（实时更新）
    private int ratings_count;
    //电影-国家
    private java.util.List<String> countries;
    //电影分类
    private java.util.List<String> genres;
    //演员（多位）
    private java.util.List<DoubanMovieInTheaters.SubjectsBean.CastsBean> casts;
    //导演
    private java.util.List<DoubanMovieInTheaters.SubjectsBean.DirectorsBean> directors;
    //电影别名
    private java.util.List<String> aka;

    public DoubanMovieInTheaters.SubjectsBean.RatingBean getRating() {
        return rating;
    }

    public void setRating(DoubanMovieInTheaters.SubjectsBean.RatingBean rating) {
        this.rating = rating;
    }

    public int getReviews_count() {
        return reviews_count;
    }

    public void setReviews_count(int reviews_count) {
        this.reviews_count = reviews_count;
    }

    public int getWish_count() {
        return wish_count;
    }

    public void setWish_count(int wish_count) {
        this.wish_count = wish_count;
    }

    public String getDouban_site() {
        return douban_site;
    }

    public void setDouban_site(String douban_site) {
        this.douban_site = douban_site;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public DoubanMovieInTheaters.SubjectsBean.MoviePosterCoverBean getImages() {
        return images;
    }

    public void setImages(DoubanMovieInTheaters.SubjectsBean.MoviePosterCoverBean images) {
        this.images = images;
    }

    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMobile_url() {
        return mobile_url;
    }

    public void setMobile_url(String mobile_url) {
        this.mobile_url = mobile_url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Object getDo_count() {
        return do_count;
    }

    public void setDo_count(Object do_count) {
        this.do_count = do_count;
    }

    public String getShare_url() {
        return share_url;
    }

    public void setShare_url(String share_url) {
        this.share_url = share_url;
    }

    public Object getSeasons_count() {
        return seasons_count;
    }

    public void setSeasons_count(Object seasons_count) {
        this.seasons_count = seasons_count;
    }

    public String getSchedule_url() {
        return schedule_url;
    }

    public void setSchedule_url(String schedule_url) {
        this.schedule_url = schedule_url;
    }

    public Object getEpisodes_count() {
        return episodes_count;
    }

    public void setEpisodes_count(Object episodes_count) {
        this.episodes_count = episodes_count;
    }

    public int getCollect_count() {
        return collect_count;
    }

    public void setCollect_count(int collect_count) {
        this.collect_count = collect_count;
    }

    public Object getCurrent_season() {
        return current_season;
    }

    public void setCurrent_season(Object current_season) {
        this.current_season = current_season;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getSubtype() {
        return subtype;
    }

    public void setSubtype(String subtype) {
        this.subtype = subtype;
    }

    public int getComments_count() {
        return comments_count;
    }

    public void setComments_count(int comments_count) {
        this.comments_count = comments_count;
    }

    public int getRatings_count() {
        return ratings_count;
    }

    public void setRatings_count(int ratings_count) {
        this.ratings_count = ratings_count;
    }

    public List<String> getCountries() {
        return countries;
    }

    public void setCountries(List<String> countries) {
        this.countries = countries;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public List<DoubanMovieInTheaters.SubjectsBean.CastsBean> getCasts() {
        return casts;
    }

    public void setCasts(List<DoubanMovieInTheaters.SubjectsBean.CastsBean> casts) {
        this.casts = casts;
    }

    public List<DoubanMovieInTheaters.SubjectsBean.DirectorsBean> getDirectors() {
        return directors;
    }

    public void setDirectors(List<DoubanMovieInTheaters.SubjectsBean.DirectorsBean> directors) {
        this.directors = directors;
    }

    public List<String> getAka() {
        return aka;
    }

    public void setAka(List<String> aka) {
        this.aka = aka;
    }
}
