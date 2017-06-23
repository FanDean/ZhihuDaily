package com.fandean.zhihudaily.bean;

import java.util.List;

/**
 * //默认只显示在北京上映的前20条（start=0   count=20)
 * https://api.douban.com/v2/movie/in_theaters
 *
 * https://api.douban.com/v2/movie/in_theaters?apikey=0b2bdeda43b5688921839c8ecb20399b&city=北京&start=0&count=100&client=&udid=
 * 还需构造url，添加查询参数
 *
 * api可能会变，在代码中进行检测
 * Created by fan on 17-6-17.
 */

public class DoubanMovieInTheaters {

    /**
     * count : 20
     * start : 0
     * total : 43
     * subjects : [{"rating":{"max":10,"average":7.5,"stars":"40","min":0},"genres":["科幻","惊悚",]
     * title : 正在上映的电影-北京
     */

    //本次请求的电影数量
    private int count;
    //起始位置
    private int start;
    //正在上映的电影总数（某地区、比如北京）
    private int total;
    //页面标题： 正在上映的电影-北京
    private String title;
    //电影信息
    private List<SubjectsBean> subjects;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<SubjectsBean> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<SubjectsBean> subjects) {
        this.subjects = subjects;
    }

    public static class SubjectsBean {
        /**
         * rating : {"max":10,"average":7.5,"stars":"40","min":0}
         * genres : ["科幻","惊悚","恐怖"]
         * title : 异形：契约
         * casts : [{"alt":"https://movie.douban.com/celebrity/1000010/","avatars":]
         * collect_count : 23341
         * original_title : Alien: Covenant
         * subtype : movie
         * directors : [{"alt":"https://movie.douban.com/celebrity/1054416/","avatars":]
         * year : 2017
         * images : {"large":"https://img3.doubanio.com/view/movie_poster_cover/lpst/public/p2459944375.webp",}
         * alt : https://movie.douban.com/subject/11803087/
         * id : 11803087
         */

        private RatingBean rating;
        //电影名称
        private String title;
        //多少人看过
        private int collect_count;
        //电影原名
        private String original_title;
        private String subtype;
        private String year;
        private MoviePosterCoverBean images;
        private String alt;
        private String id;
        private List<String> genres;
        private List<CastsBean> casts;
        private List<DirectorsBean> directors;

        public RatingBean getRating() {
            return rating;
        }

        public void setRating(RatingBean rating) {
            this.rating = rating;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getCollect_count() {
            return collect_count;
        }

        public void setCollect_count(int collect_count) {
            this.collect_count = collect_count;
        }

        public String getOriginal_title() {
            return original_title;
        }

        public void setOriginal_title(String original_title) {
            this.original_title = original_title;
        }

        public String getSubtype() {
            return subtype;
        }

        public void setSubtype(String subtype) {
            this.subtype = subtype;
        }

        public String getYear() {
            return year;
        }

        public void setYear(String year) {
            this.year = year;
        }

        public MoviePosterCoverBean getImages() {
            return images;
        }

        public void setImages(MoviePosterCoverBean images) {
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

        public List<String> getGenres() {
            return genres;
        }

        public void setGenres(List<String> genres) {
            this.genres = genres;
        }

        public List<CastsBean> getCasts() {
            return casts;
        }

        public void setCasts(List<CastsBean> casts) {
            this.casts = casts;
        }

        public List<DirectorsBean> getDirectors() {
            return directors;
        }

        public void setDirectors(List<DirectorsBean> directors) {
            this.directors = directors;
        }

        /**
         * 电影评分
         */
        public static class RatingBean {
            /**
             * max : 10
             * average : 7.5
             * stars : 40
             * min : 0
             */
            //最高分
            private int max;
            //该电影平均分
            private double average;
//            private float average;

            private String stars;
            private int min;

            public int getMax() {
                return max;
            }

            public void setMax(int max) {
                this.max = max;
            }

            public double getAverage() {
                return average;
            }

            public void setAverage(double average) {
                this.average = average;
            }


            public String getStars() {
                return stars;
            }

            public void setStars(String stars) {
                this.stars = stars;
            }

            public int getMin() {
                return min;
            }

            public void setMin(int min) {
                this.min = min;
            }
        }

        /**
         * 电影海报封面
         */
        public static class MoviePosterCoverBean {
            /**
             * small : https://img3.doubanio.com/view/movie_poster_cover/ipst/public/p2459944375.webp
             * large : https://img3.doubanio.com/view/movie_poster_cover/lpst/public/p2459944375.webp
             * medium : https://img3.doubanio.com/view/movie_poster_cover/spst/public/p2459944375.webp
             */

            private String small;
            private String large;
            private String medium;

            public String getSmall() {
                return small;
            }

            public void setSmall(String small) {
                this.small = small;
            }

            public String getLarge() {
                return large;
            }

            public void setLarge(String large) {
                this.large = large;
            }

            public String getMedium() {
                return medium;
            }

            public void setMedium(String medium) {
                this.medium = medium;
            }
        }

        /**
         * 演员
         */
        public static class CastsBean {
            /**
             * alt : https://movie.douban.com/celebrity/1000010/
             * avatars : {"small":"https://img3.doubanio.com/img/celebrity/small/32214.jpg",}
             * name : 迈克尔·法斯宾德
             * id : 1000010
             */
            //该演员的网页链接
            private String alt;
            //演员头像照片
            private CastsAvatarsBean avatars;
            //演员名字
            private String name;
            //演员ID
            private String id;

            public String getAlt() {
                return alt;
            }

            public void setAlt(String alt) {
                this.alt = alt;
            }

            public CastsAvatarsBean getAvatars() {
                return avatars;
            }

            public void setAvatars(CastsAvatarsBean avatars) {
                this.avatars = avatars;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            /**
             * 演员头像
             */
            public static class CastsAvatarsBean {
                /**
                 * small : https://img3.doubanio.com/img/celebrity/small/32214.jpg
                 * large : https://img3.doubanio.com/img/celebrity/large/32214.jpg
                 * medium : https://img3.doubanio.com/img/celebrity/medium/32214.jpg
                 */

                private String small;
                private String large;
                private String medium;

                public String getSmall() {
                    return small;
                }

                public void setSmall(String small) {
                    this.small = small;
                }

                public String getLarge() {
                    return large;
                }

                public void setLarge(String large) {
                    this.large = large;
                }

                public String getMedium() {
                    return medium;
                }

                public void setMedium(String medium) {
                    this.medium = medium;
                }
            }
        }

        /**
         * 导演信息
         */
        public static class DirectorsBean {
            /**
             * alt : https://movie.douban.com/celebrity/1054416/
             * avatars : {"small":"https://img1.doubanio.com/img/celebrity/small/588.jpg",}
             * name : 雷德利·斯科特
             * id : 1054416
             */

            private String alt;
            private DirectorsAvatarsBean avatars;
            private String name;
            private String id;

            public String getAlt() {
                return alt;
            }

            public void setAlt(String alt) {
                this.alt = alt;
            }

            public DirectorsAvatarsBean getAvatars() {
                return avatars;
            }

            public void setAvatars(DirectorsAvatarsBean avatars) {
                this.avatars = avatars;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            /**
             * 导演头像
             */
            public static class DirectorsAvatarsBean {
                /**
                 * small : https://img1.doubanio.com/img/celebrity/small/588.jpg
                 * large : https://img1.doubanio.com/img/celebrity/large/588.jpg
                 * medium : https://img1.doubanio.com/img/celebrity/medium/588.jpg
                 */

                private String small;
                private String large;
                private String medium;

                public String getSmall() {
                    return small;
                }

                public void setSmall(String small) {
                    this.small = small;
                }

                public String getLarge() {
                    return large;
                }

                public void setLarge(String large) {
                    this.large = large;
                }

                public String getMedium() {
                    return medium;
                }

                public void setMedium(String medium) {
                    this.medium = medium;
                }
            }
        }
    }
}
