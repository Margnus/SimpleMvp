package com.lxf.mvvm.bean;

import android.databinding.BaseObservable;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.List;

/**
 * 描述：
 *
 * @author lixiaofan
 * @date 2017/11/6 14:46
 */

public class DbMovieBean extends BaseObservable implements Serializable {

    /**
     * count : 1
     * start : 0
     * total : 250
     * subjects : [{"rating":{"max":10,"average":9.6,"stars":"50","min":0},"genres":["犯罪","剧情"],"title":"肖申克的救赎","casts":[{"alt":"https://movie.douban.com/celebrity/1054521/","avatars":{"small":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p17525.webp","large":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p17525.webp","medium":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p17525.webp"},"name":"蒂姆·罗宾斯","id":"1054521"},{"alt":"https://movie.douban.com/celebrity/1054534/","avatars":{"small":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p34642.webp","large":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p34642.webp","medium":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p34642.webp"},"name":"摩根·弗里曼","id":"1054534"},{"alt":"https://movie.douban.com/celebrity/1041179/","avatars":{"small":"https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p5837.webp","large":"https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p5837.webp","medium":"https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p5837.webp"},"name":"鲍勃·冈顿","id":"1041179"}],"collect_count":1148426,"original_title":"The Shawshank Redemption","subtype":"movie","directors":[{"alt":"https://movie.douban.com/celebrity/1047973/","avatars":{"small":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p230.webp","large":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p230.webp","medium":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p230.webp"},"name":"弗兰克·德拉邦特","id":"1047973"}],"year":"1994","images":{"small":"https://img3.doubanio.com/view/photo/s_ratio_poster/public/p480747492.webp","large":"https://img3.doubanio.com/view/photo/s_ratio_poster/public/p480747492.webp","medium":"https://img3.doubanio.com/view/photo/s_ratio_poster/public/p480747492.webp"},"alt":"https://movie.douban.com/subject/1292052/","id":"1292052"}]
     * title : 豆瓣电影Top250
     */

    @JSONField(name = "count")
    private int count;
    @JSONField(name = "start")
    private int start;
    @JSONField(name = "total")
    private int total;
    @JSONField(name = "title")
    private String title;
    @JSONField(name = "subjects")
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

    public static class SubjectsBean extends BaseObservable implements Serializable{
        /**
         * rating : {"max":10,"average":9.6,"stars":"50","min":0}
         * genres : ["犯罪","剧情"]
         * title : 肖申克的救赎
         * casts : [{"alt":"https://movie.douban.com/celebrity/1054521/","avatars":{"small":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p17525.webp","large":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p17525.webp","medium":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p17525.webp"},"name":"蒂姆·罗宾斯","id":"1054521"},{"alt":"https://movie.douban.com/celebrity/1054534/","avatars":{"small":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p34642.webp","large":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p34642.webp","medium":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p34642.webp"},"name":"摩根·弗里曼","id":"1054534"},{"alt":"https://movie.douban.com/celebrity/1041179/","avatars":{"small":"https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p5837.webp","large":"https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p5837.webp","medium":"https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p5837.webp"},"name":"鲍勃·冈顿","id":"1041179"}]
         * collect_count : 1148426
         * original_title : The Shawshank Redemption
         * subtype : movie
         * directors : [{"alt":"https://movie.douban.com/celebrity/1047973/","avatars":{"small":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p230.webp","large":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p230.webp","medium":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p230.webp"},"name":"弗兰克·德拉邦特","id":"1047973"}]
         * year : 1994
         * images : {"small":"https://img3.doubanio.com/view/photo/s_ratio_poster/public/p480747492.webp","large":"https://img3.doubanio.com/view/photo/s_ratio_poster/public/p480747492.webp","medium":"https://img3.doubanio.com/view/photo/s_ratio_poster/public/p480747492.webp"}
         * alt : https://movie.douban.com/subject/1292052/
         * id : 1292052
         */

        @JSONField(name = "rating")
        private RatingBean rating;
        @JSONField(name = "title")
        private String title;
        @JSONField(name = "collect_count")
        private int collectCount;
        @JSONField(name = "original_title")
        private String originalTitle;
        @JSONField(name = "subtype")
        private String subtype;
        @JSONField(name = "year")
        private String year;
        @JSONField(name = "images")
        private ImagesBean images;
        @JSONField(name = "alt")
        private String alt;
        @JSONField(name = "id")
        private String id;
        @JSONField(name = "genres")
        private List<String> genres;
        @JSONField(name = "casts")
        private List<CastsBean> casts;
        @JSONField(name = "directors")
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

        public int getCollectCount() {
            return collectCount;
        }

        public void setCollectCount(int collectCount) {
            this.collectCount = collectCount;
        }

        public String getOriginalTitle() {
            return originalTitle;
        }

        public void setOriginalTitle(String originalTitle) {
            this.originalTitle = originalTitle;
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

        public ImagesBean getImages() {
            return images;
        }

        public void setImages(ImagesBean images) {
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

        public static class RatingBean extends BaseObservable implements Serializable{
            /**
             * max : 10
             * average : 9.6
             * stars : 50
             * min : 0
             */

            @JSONField(name = "max")
            private int max;
            @JSONField(name = "average")
            private double average;
            @JSONField(name = "stars")
            private String stars;
            @JSONField(name = "min")
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

        public static class ImagesBean extends BaseObservable implements Serializable{
            /**
             * small : https://img3.doubanio.com/view/photo/s_ratio_poster/public/p480747492.webp
             * large : https://img3.doubanio.com/view/photo/s_ratio_poster/public/p480747492.webp
             * medium : https://img3.doubanio.com/view/photo/s_ratio_poster/public/p480747492.webp
             */

            @JSONField(name = "small")
            private String small;
            @JSONField(name = "large")
            private String large;
            @JSONField(name = "medium")
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

        public static class CastsBean extends BaseObservable implements Serializable{
            /**
             * alt : https://movie.douban.com/celebrity/1054521/
             * avatars : {"small":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p17525.webp","large":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p17525.webp","medium":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p17525.webp"}
             * name : 蒂姆·罗宾斯
             * id : 1054521
             */

            @JSONField(name = "alt")
            private String alt;
            @JSONField(name = "avatars")
            private ImagesBean avatars;
            @JSONField(name = "name")
            private String name;
            @JSONField(name = "id")
            private String id;

            public String getAlt() {
                return alt;
            }

            public void setAlt(String alt) {
                this.alt = alt;
            }

            public ImagesBean getAvatars() {
                return avatars;
            }

            public void setAvatars(ImagesBean avatars) {
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
        }

        public static class DirectorsBean extends BaseObservable implements Serializable{
            /**
             * alt : https://movie.douban.com/celebrity/1047973/
             * avatars : {"small":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p230.webp","large":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p230.webp","medium":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p230.webp"}
             * name : 弗兰克·德拉邦特
             * id : 1047973
             */

            @JSONField(name = "alt")
            private String alt;
            @JSONField(name = "avatars")
            private ImagesBean avatars;
            @JSONField(name = "name")
            private String name;
            @JSONField(name = "id")
            private String id;

            public String getAlt() {
                return alt;
            }

            public void setAlt(String alt) {
                this.alt = alt;
            }

            public ImagesBean getAvatars() {
                return avatars;
            }

            public void setAvatars(ImagesBean avatars) {
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
        }
    }
}
