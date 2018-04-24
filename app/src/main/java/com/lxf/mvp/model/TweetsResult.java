package com.lxf.mvp.model;

import java.util.List;

/**
 * Created by lixiaofan on 2017/7/24.
 */

public class TweetsResult extends BaseResult<List<TweetsResult.DataBean>>{

    public static class DataBean {
        /**
         * id : 186951
         * owner_id : 295802
         * owner : {"job":1,"sex":0,"location":"北京 海淀区","company":"金石嘉诺（北京）科技有限公司","slogan":"Try anything once!","avatar":"https://dn-coding-net-production-static.qbox.me/1130c094-1659-4183-946c-c76c5ed5ef4a.jpg","lavatar":"https://dn-coding-net-production-static.qbox.me/1130c094-1659-4183-946c-c76c5ed5ef4a.jpg","created_at":1477670557000,"last_logined_at":1500858385000,"last_activity_at":1500859333748,"global_key":"liuxiaojia","name":"liuxiaojia","name_pinyin":"","path":"/u/liuxiaojia","status":0,"id":0,"vip":3,"follows_count":0,"fans_count":0,"followed":false,"follow":false}
         * created_at : 1500859333000
         * sort_time : 1500859333000
         * likes : 6
         * rewards : 0
         * comments : 1
         * comment_list : [{"id":435745,"tweet_id":186951,"owner_id":51423,"owner":{"job":1,"sex":0,"location":"","company":"","slogan":"Do what i love !","avatar":"https://dn-coding-net-production-static.qbox.me/63dd74d9-0f1f-44f5-bf79-a990fb812b62.jpg?imageMogr2/auto-orient/format/jpeg/crop/!600x600a0a19","lavatar":"https://dn-coding-net-production-static.qbox.me/63dd74d9-0f1f-44f5-bf79-a990fb812b62.jpg?imageMogr2/auto-orient/format/jpeg/crop/!600x600a0a19","created_at":1417870385000,"last_logined_at":1500860307000,"last_activity_at":1500860499280,"global_key":"ghostsf","name":"ghostsf","name_pinyin":"","path":"/u/ghostsf","status":0,"id":0,"vip":4,"follows_count":0,"fans_count":0,"followed":false,"follow":false},"created_at":1500860366000,"content":"where ?"}]
         * device : iPhone 7
         * location :
         * coord :
         * address :
         * content : <p>好久没冒个泡了</p>
         <p><a href="https://dn-coding-net-production-pp.qbox.me/2d70caa4-7786-4ff1-8081-4e4010b3b489.jpg" target="_blank" class="bubble-markdown-image-link" rel="nofollow noopener noreferrer"><img src="https://dn-coding-net-production-pp.qbox.me/2d70caa4-7786-4ff1-8081-4e4010b3b489.jpg" alt="" class="bubble-markdown-image"></a></p>
         * activity_id : 0
         * liked : false
         * like_users : [{"path":"/u/xiaoyu97","global_key":"xiaoyu97","name":"xiaoyu97","avatar":"/static/fruit_avatar/Fruit-13.png","follow":0,"followed":0},{"path":"/u/tankxu","global_key":"tankxu","name":"tankxu","avatar":"https://dn-coding-net-production-static.qbox.me/ca2898bb-c955-4bc8-8ce8-b20a6b75af4a.png?imageMogr2/auto-orient/format/png/crop/!177x177a0a0","follow":0,"followed":0},{"path":"/u/ghostsf","global_key":"ghostsf","name":"ghostsf","avatar":"https://dn-coding-net-production-static.qbox.me/63dd74d9-0f1f-44f5-bf79-a990fb812b62.jpg?imageMogr2/auto-orient/format/jpeg/crop/!600x600a0a19","follow":0,"followed":0},{"path":"/u/changhongyuan","global_key":"changhongyuan","name":"changhongyuan","avatar":"https://dn-coding-net-production-static.qbox.me/121dcbf5-89bf-41e8-8ae1-1da0e170820f.jpg?imageMogr2/auto-orient/format/jpeg/crop/!638x638a0a0","follow":0,"followed":0},{"path":"/u/dont","global_key":"dont","name":"dont","avatar":"https://dn-coding-net-production-static.qbox.me/96763ead-6a29-40ba-815d-294616f4ec5d.png?imageMogr2/auto-orient/format/png/crop/!200x200a0a0","follow":0,"followed":0},{"path":"/u/Licoy","global_key":"Licoy","name":"憧憬Licoy","avatar":"https://dn-coding-net-production-static.qbox.me/b8527596-da3d-4662-acac-aa23dd1dacbe.jpg","follow":0,"followed":0}]
         * reward_users : []
         * rewarded : false
         */

        private int id;
        private int owner_id;
        private OwnerBean owner;
        private long created_at;
        private long sort_time;
        private int likes;
        private int rewards;
        private int comments;
        private String device;
        private String location;
        private String coord;
        private String address;
        private String content;
        private int activity_id;
        private boolean liked;
        private boolean rewarded;
        private List<CommentListBean> comment_list;
        private List<LikeUsersBean> like_users;
        private List<?> reward_users;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getOwner_id() {
            return owner_id;
        }

        public void setOwner_id(int owner_id) {
            this.owner_id = owner_id;
        }

        public OwnerBean getOwner() {
            return owner;
        }

        public void setOwner(OwnerBean owner) {
            this.owner = owner;
        }

        public long getCreated_at() {
            return created_at;
        }

        public void setCreated_at(long created_at) {
            this.created_at = created_at;
        }

        public long getSort_time() {
            return sort_time;
        }

        public void setSort_time(long sort_time) {
            this.sort_time = sort_time;
        }

        public int getLikes() {
            return likes;
        }

        public void setLikes(int likes) {
            this.likes = likes;
        }

        public int getRewards() {
            return rewards;
        }

        public void setRewards(int rewards) {
            this.rewards = rewards;
        }

        public int getComments() {
            return comments;
        }

        public void setComments(int comments) {
            this.comments = comments;
        }

        public String getDevice() {
            return device;
        }

        public void setDevice(String device) {
            this.device = device;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getCoord() {
            return coord;
        }

        public void setCoord(String coord) {
            this.coord = coord;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getActivity_id() {
            return activity_id;
        }

        public void setActivity_id(int activity_id) {
            this.activity_id = activity_id;
        }

        public boolean isLiked() {
            return liked;
        }

        public void setLiked(boolean liked) {
            this.liked = liked;
        }

        public boolean isRewarded() {
            return rewarded;
        }

        public void setRewarded(boolean rewarded) {
            this.rewarded = rewarded;
        }

        public List<CommentListBean> getComment_list() {
            return comment_list;
        }

        public void setComment_list(List<CommentListBean> comment_list) {
            this.comment_list = comment_list;
        }

        public List<LikeUsersBean> getLike_users() {
            return like_users;
        }

        public void setLike_users(List<LikeUsersBean> like_users) {
            this.like_users = like_users;
        }

        public List<?> getReward_users() {
            return reward_users;
        }

        public void setReward_users(List<?> reward_users) {
            this.reward_users = reward_users;
        }

        public static class OwnerBean {
            /**
             * job : 1
             * sex : 0
             * location : 北京 海淀区
             * company : 金石嘉诺（北京）科技有限公司
             * slogan : Try anything once!
             * avatar : https://dn-coding-net-production-static.qbox.me/1130c094-1659-4183-946c-c76c5ed5ef4a.jpg
             * lavatar : https://dn-coding-net-production-static.qbox.me/1130c094-1659-4183-946c-c76c5ed5ef4a.jpg
             * created_at : 1477670557000
             * last_logined_at : 1500858385000
             * last_activity_at : 1500859333748
             * global_key : liuxiaojia
             * name : liuxiaojia
             * name_pinyin :
             * path : /u/liuxiaojia
             * status : 0
             * id : 0
             * vip : 3
             * follows_count : 0
             * fans_count : 0
             * followed : false
             * follow : false
             */

            private int job;
            private int sex;
            private String location;
            private String company;
            private String slogan;
            private String avatar;
            private String lavatar;
            private long created_at;
            private long last_logined_at;
            private long last_activity_at;
            private String global_key;
            private String name;
            private String name_pinyin;
            private String path;
            private int status;
            private int id;
            private int vip;
            private int follows_count;
            private int fans_count;
            private boolean followed;
            private boolean follow;

            public int getJob() {
                return job;
            }

            public void setJob(int job) {
                this.job = job;
            }

            public int getSex() {
                return sex;
            }

            public void setSex(int sex) {
                this.sex = sex;
            }

            public String getLocation() {
                return location;
            }

            public void setLocation(String location) {
                this.location = location;
            }

            public String getCompany() {
                return company;
            }

            public void setCompany(String company) {
                this.company = company;
            }

            public String getSlogan() {
                return slogan;
            }

            public void setSlogan(String slogan) {
                this.slogan = slogan;
            }

            public String getAvatar() {
                return avatar;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }

            public String getLavatar() {
                return lavatar;
            }

            public void setLavatar(String lavatar) {
                this.lavatar = lavatar;
            }

            public long getCreated_at() {
                return created_at;
            }

            public void setCreated_at(long created_at) {
                this.created_at = created_at;
            }

            public long getLast_logined_at() {
                return last_logined_at;
            }

            public void setLast_logined_at(long last_logined_at) {
                this.last_logined_at = last_logined_at;
            }

            public long getLast_activity_at() {
                return last_activity_at;
            }

            public void setLast_activity_at(long last_activity_at) {
                this.last_activity_at = last_activity_at;
            }

            public String getGlobal_key() {
                return global_key;
            }

            public void setGlobal_key(String global_key) {
                this.global_key = global_key;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getName_pinyin() {
                return name_pinyin;
            }

            public void setName_pinyin(String name_pinyin) {
                this.name_pinyin = name_pinyin;
            }

            public String getPath() {
                return path;
            }

            public void setPath(String path) {
                this.path = path;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getVip() {
                return vip;
            }

            public void setVip(int vip) {
                this.vip = vip;
            }

            public int getFollows_count() {
                return follows_count;
            }

            public void setFollows_count(int follows_count) {
                this.follows_count = follows_count;
            }

            public int getFans_count() {
                return fans_count;
            }

            public void setFans_count(int fans_count) {
                this.fans_count = fans_count;
            }

            public boolean isFollowed() {
                return followed;
            }

            public void setFollowed(boolean followed) {
                this.followed = followed;
            }

            public boolean isFollow() {
                return follow;
            }

            public void setFollow(boolean follow) {
                this.follow = follow;
            }
        }

        public static class CommentListBean {
            /**
             * id : 435745
             * tweet_id : 186951
             * owner_id : 51423
             * owner : {"job":1,"sex":0,"location":"","company":"","slogan":"Do what i love !","avatar":"https://dn-coding-net-production-static.qbox.me/63dd74d9-0f1f-44f5-bf79-a990fb812b62.jpg?imageMogr2/auto-orient/format/jpeg/crop/!600x600a0a19","lavatar":"https://dn-coding-net-production-static.qbox.me/63dd74d9-0f1f-44f5-bf79-a990fb812b62.jpg?imageMogr2/auto-orient/format/jpeg/crop/!600x600a0a19","created_at":1417870385000,"last_logined_at":1500860307000,"last_activity_at":1500860499280,"global_key":"ghostsf","name":"ghostsf","name_pinyin":"","path":"/u/ghostsf","status":0,"id":0,"vip":4,"follows_count":0,"fans_count":0,"followed":false,"follow":false}
             * created_at : 1500860366000
             * content : where ?
             */

            private int id;
            private int tweet_id;
            private int owner_id;
            private OwnerBeanX owner;
            private long created_at;
            private String content;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getTweet_id() {
                return tweet_id;
            }

            public void setTweet_id(int tweet_id) {
                this.tweet_id = tweet_id;
            }

            public int getOwner_id() {
                return owner_id;
            }

            public void setOwner_id(int owner_id) {
                this.owner_id = owner_id;
            }

            public OwnerBeanX getOwner() {
                return owner;
            }

            public void setOwner(OwnerBeanX owner) {
                this.owner = owner;
            }

            public long getCreated_at() {
                return created_at;
            }

            public void setCreated_at(long created_at) {
                this.created_at = created_at;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public static class OwnerBeanX {
                /**
                 * job : 1
                 * sex : 0
                 * location :
                 * company :
                 * slogan : Do what i love !
                 * avatar : https://dn-coding-net-production-static.qbox.me/63dd74d9-0f1f-44f5-bf79-a990fb812b62.jpg?imageMogr2/auto-orient/format/jpeg/crop/!600x600a0a19
                 * lavatar : https://dn-coding-net-production-static.qbox.me/63dd74d9-0f1f-44f5-bf79-a990fb812b62.jpg?imageMogr2/auto-orient/format/jpeg/crop/!600x600a0a19
                 * created_at : 1417870385000
                 * last_logined_at : 1500860307000
                 * last_activity_at : 1500860499280
                 * global_key : ghostsf
                 * name : ghostsf
                 * name_pinyin :
                 * path : /u/ghostsf
                 * status : 0
                 * id : 0
                 * vip : 4
                 * follows_count : 0
                 * fans_count : 0
                 * followed : false
                 * follow : false
                 */

                private int job;
                private int sex;
                private String location;
                private String company;
                private String slogan;
                private String avatar;
                private String lavatar;
                private long created_at;
                private long last_logined_at;
                private long last_activity_at;
                private String global_key;
                private String name;
                private String name_pinyin;
                private String path;
                private int status;
                private int id;
                private int vip;
                private int follows_count;
                private int fans_count;
                private boolean followed;
                private boolean follow;

                public int getJob() {
                    return job;
                }

                public void setJob(int job) {
                    this.job = job;
                }

                public int getSex() {
                    return sex;
                }

                public void setSex(int sex) {
                    this.sex = sex;
                }

                public String getLocation() {
                    return location;
                }

                public void setLocation(String location) {
                    this.location = location;
                }

                public String getCompany() {
                    return company;
                }

                public void setCompany(String company) {
                    this.company = company;
                }

                public String getSlogan() {
                    return slogan;
                }

                public void setSlogan(String slogan) {
                    this.slogan = slogan;
                }

                public String getAvatar() {
                    return avatar;
                }

                public void setAvatar(String avatar) {
                    this.avatar = avatar;
                }

                public String getLavatar() {
                    return lavatar;
                }

                public void setLavatar(String lavatar) {
                    this.lavatar = lavatar;
                }

                public long getCreated_at() {
                    return created_at;
                }

                public void setCreated_at(long created_at) {
                    this.created_at = created_at;
                }

                public long getLast_logined_at() {
                    return last_logined_at;
                }

                public void setLast_logined_at(long last_logined_at) {
                    this.last_logined_at = last_logined_at;
                }

                public long getLast_activity_at() {
                    return last_activity_at;
                }

                public void setLast_activity_at(long last_activity_at) {
                    this.last_activity_at = last_activity_at;
                }

                public String getGlobal_key() {
                    return global_key;
                }

                public void setGlobal_key(String global_key) {
                    this.global_key = global_key;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getName_pinyin() {
                    return name_pinyin;
                }

                public void setName_pinyin(String name_pinyin) {
                    this.name_pinyin = name_pinyin;
                }

                public String getPath() {
                    return path;
                }

                public void setPath(String path) {
                    this.path = path;
                }

                public int getStatus() {
                    return status;
                }

                public void setStatus(int status) {
                    this.status = status;
                }

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public int getVip() {
                    return vip;
                }

                public void setVip(int vip) {
                    this.vip = vip;
                }

                public int getFollows_count() {
                    return follows_count;
                }

                public void setFollows_count(int follows_count) {
                    this.follows_count = follows_count;
                }

                public int getFans_count() {
                    return fans_count;
                }

                public void setFans_count(int fans_count) {
                    this.fans_count = fans_count;
                }

                public boolean isFollowed() {
                    return followed;
                }

                public void setFollowed(boolean followed) {
                    this.followed = followed;
                }

                public boolean isFollow() {
                    return follow;
                }

                public void setFollow(boolean follow) {
                    this.follow = follow;
                }
            }
        }

        public static class LikeUsersBean {
            /**
             * path : /u/xiaoyu97
             * global_key : xiaoyu97
             * name : xiaoyu97
             * avatar : /static/fruit_avatar/Fruit-13.png
             * follow : 0
             * followed : 0
             */

            private String path;
            private String global_key;
            private String name;
            private String avatar;
            private int follow;
            private int followed;

            public String getPath() {
                return path;
            }

            public void setPath(String path) {
                this.path = path;
            }

            public String getGlobal_key() {
                return global_key;
            }

            public void setGlobal_key(String global_key) {
                this.global_key = global_key;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getAvatar() {
                return avatar;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }

            public int getFollow() {
                return follow;
            }

            public void setFollow(int follow) {
                this.follow = follow;
            }

            public int getFollowed() {
                return followed;
            }

            public void setFollowed(int followed) {
                this.followed = followed;
            }
        }
    }
}
