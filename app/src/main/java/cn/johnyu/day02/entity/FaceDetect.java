package cn.johnyu.day02.entity;

import java.util.List;

public class FaceDetect {

    /**
     * result : {"face_num":1,"face_list":[{"face_shape":{"probability":0.63,"type":"heart"},"beauty":31.27,"liveness":{"livemapscore":1},"angle":{"roll":4.07,"pitch":25.51,"yaw":-16.69},"face_token":"57bee60eafe8fb267b521908a46967b1","location":{"top":634.22,"left":510.83,"rotation":1,"width":121,"height":119},"face_probability":1,"age":26}]}
     * log_id : 5520115895599
     * error_msg : SUCCESS
     * cached : 0
     * error_code : 0
     * timestamp : 1573280856
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
         * face_num : 1
         * face_list : [{"face_shape":{"probability":0.63,"type":"heart"},"beauty":31.27,"liveness":{"livemapscore":1},"angle":{"roll":4.07,"pitch":25.51,"yaw":-16.69},"face_token":"57bee60eafe8fb267b521908a46967b1","location":{"top":634.22,"left":510.83,"rotation":1,"width":121,"height":119},"face_probability":1,"age":26}]
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
             * face_shape : {"probability":0.63,"type":"heart"}
             * beauty : 31.27
             * liveness : {"livemapscore":1}
             * angle : {"roll":4.07,"pitch":25.51,"yaw":-16.69}
             * face_token : 57bee60eafe8fb267b521908a46967b1
             * location : {"top":634.22,"left":510.83,"rotation":1,"width":121,"height":119}
             * face_probability : 1
             * age : 26
             */

            private FaceShapeBean face_shape;
            private double beauty;
            private LivenessBean liveness;
            private AngleBean angle;
            private String face_token;
            private LocationBean location;
            private double face_probability;
            private int age;

            public FaceShapeBean getFace_shape() {
                return face_shape;
            }

            public void setFace_shape(FaceShapeBean face_shape) {
                this.face_shape = face_shape;
            }

            public double getBeauty() {
                return beauty;
            }

            public void setBeauty(double beauty) {
                this.beauty = beauty;
            }

            public LivenessBean getLiveness() {
                return liveness;
            }

            public void setLiveness(LivenessBean liveness) {
                this.liveness = liveness;
            }

            public AngleBean getAngle() {
                return angle;
            }

            public void setAngle(AngleBean angle) {
                this.angle = angle;
            }

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

            public double getFace_probability() {
                return face_probability;
            }

            public void setFace_probability(int face_probability) {
                this.face_probability = face_probability;
            }

            public int getAge() {
                return age;
            }

            public void setAge(int age) {
                this.age = age;
            }

            public static class FaceShapeBean {
                /**
                 * probability : 0.63
                 * type : heart
                 */

                private double probability;
                private String type;

                public double getProbability() {
                    return probability;
                }

                public void setProbability(double probability) {
                    this.probability = probability;
                }

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }
            }

            public static class LivenessBean {
                /**
                 * livemapscore : 1
                 */

                private double livemapscore;

                public double getLivemapscore() {
                    return livemapscore;
                }

                public void setLivemapscore(int livemapscore) {
                    this.livemapscore = livemapscore;
                }
            }

            public static class AngleBean {
                /**
                 * roll : 4.07
                 * pitch : 25.51
                 * yaw : -16.69
                 */

                private double roll;
                private double pitch;
                private double yaw;

                public double getRoll() {
                    return roll;
                }

                public void setRoll(double roll) {
                    this.roll = roll;
                }

                public double getPitch() {
                    return pitch;
                }

                public void setPitch(double pitch) {
                    this.pitch = pitch;
                }

                public double getYaw() {
                    return yaw;
                }

                public void setYaw(double yaw) {
                    this.yaw = yaw;
                }
            }

            public static class LocationBean {
                /**
                 * top : 634.22
                 * left : 510.83
                 * rotation : 1
                 * width : 121
                 * height : 119
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
        }
    }
}
