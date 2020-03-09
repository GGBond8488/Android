package cn.johnyu.day02.entity;

import java.util.List;

public class FaceGroup {

    /**
     * result : {"face_num":2,"face_list":[{"face_token":"a7a53e29cf6556b8e7227fd66538cb7d","location":{"top":136.99,"left":73.57,"rotation":9,"width":62,"height":55},"user_list":[{"score":95.148643493652,"group_id":"Xos","user_id":"Liuyz","user_info":""}]},{"face_token":"4283fb09231d73786d27387224b0be67","location":{"top":153.41,"left":411.54,"rotation":-9,"width":58,"height":51},"user_list":[{"score":90.677284240723,"group_id":"Xos","user_id":"ChenYW","user_info":""}]}]}
     * log_id : 9925754555999
     * error_msg : SUCCESS
     * cached : 0
     * error_code : 0
     * timestamp : 1573733971
     */

    private ResultBean result;
    private long log_id;
    private String error_msg;
    private int cached;
    private int error_code;
    private int timestamp;

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public long getLog_id() {
        return log_id;
    }

    public void setLog_id(long log_id) {
        this.log_id = log_id;
    }

    public String getError_msg() {
        return error_msg;
    }

    public void setError_msg(String error_msg) {
        this.error_msg = error_msg;
    }

    public int getCached() {
        return cached;
    }

    public void setCached(int cached) {
        this.cached = cached;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }

    public static class ResultBean {
        /**
         * face_num : 2
         * face_list : [{"face_token":"a7a53e29cf6556b8e7227fd66538cb7d","location":{"top":136.99,"left":73.57,"rotation":9,"width":62,"height":55},"user_list":[{"score":95.148643493652,"group_id":"Xos","user_id":"Liuyz","user_info":""}]},{"face_token":"4283fb09231d73786d27387224b0be67","location":{"top":153.41,"left":411.54,"rotation":-9,"width":58,"height":51},"user_list":[{"score":90.677284240723,"group_id":"Xos","user_id":"ChenYW","user_info":""}]}]
         */

        private int face_num;
        private List<FaceListBean> face_list;

        public int getFace_num() {
            return face_num;
        }

        public void setFace_num(int face_num) {
            this.face_num = face_num;
        }

        public List<FaceListBean> getFace_list() {
            return face_list;
        }

        public void setFace_list(List<FaceListBean> face_list) {
            this.face_list = face_list;
        }

        public static class FaceListBean {
            /**
             * face_token : a7a53e29cf6556b8e7227fd66538cb7d
             * location : {"top":136.99,"left":73.57,"rotation":9,"width":62,"height":55}
             * user_list : [{"score":95.148643493652,"group_id":"Xos","user_id":"Liuyz","user_info":""}]
             */

            private String face_token;
            private LocationBean location;
            private List<UserListBean> user_list;

            public String getFace_token() {
                return face_token;
            }

            public void setFace_token(String face_token) {
                this.face_token = face_token;
            }

            public LocationBean getLocation() {
                return location;
            }

            public void setLocation(LocationBean location) {
                this.location = location;
            }

            public List<UserListBean> getUser_list() {
                return user_list;
            }

            public void setUser_list(List<UserListBean> user_list) {
                this.user_list = user_list;
            }

            public static class LocationBean {
                /**
                 * top : 136.99
                 * left : 73.57
                 * rotation : 9
                 * width : 62
                 * height : 55
                 */

                private double top;
                private double left;
                private int rotation;
                private int width;
                private int height;

                public double getTop() {
                    return top;
                }

                public void setTop(double top) {
                    this.top = top;
                }

                public double getLeft() {
                    return left;
                }

                public void setLeft(double left) {
                    this.left = left;
                }

                public int getRotation() {
                    return rotation;
                }

                public void setRotation(int rotation) {
                    this.rotation = rotation;
                }

                public int getWidth() {
                    return width;
                }

                public void setWidth(int width) {
                    this.width = width;
                }

                public int getHeight() {
                    return height;
                }

                public void setHeight(int height) {
                    this.height = height;
                }
            }

            public static class UserListBean {
                /**
                 * score : 95.148643493652
                 * group_id : Xos
                 * user_id : Liuyz
                 * user_info :
                 */

                private double score;
                private String group_id;
                private String user_id;
                private String user_info;

                public double getScore() {
                    return score;
                }

                public void setScore(double score) {
                    this.score = score;
                }

                public String getGroup_id() {
                    return group_id;
                }

                public void setGroup_id(String group_id) {
                    this.group_id = group_id;
                }

                public String getUser_id() {
                    return user_id;
                }

                public void setUser_id(String user_id) {
                    this.user_id = user_id;
                }

                public String getUser_info() {
                    return user_info;
                }

                public void setUser_info(String user_info) {
                    this.user_info = user_info;
                }
            }
        }
    }
}
