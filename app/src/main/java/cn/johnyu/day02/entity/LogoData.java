package cn.johnyu.day02.entity;

import java.util.List;

public class LogoData {

    /**
     * log_id : 6649713117077845263
     * result_num : 2
     * result : [{"name":"丝格丽","probability":0.43510013222694,"location":{"width":183,"top":103,"left":208,"height":166},"type":1},{"name":"美国司沃康","probability":0.19139737876274,"location":{"width":177,"top":276,"left":210,"height":35},"type":1}]
     */

    private long log_id;
    private int result_num;
    private List<ResultBean> result;

    public long getLog_id() {
        return log_id;
    }

    public void setLog_id(long log_id) {
        this.log_id = log_id;
    }

    public int getResult_num() {
        return result_num;
    }

    public void setResult_num(int result_num) {
        this.result_num = result_num;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * name : 丝格丽
         * probability : 0.43510013222694
         * location : {"width":183,"top":103,"left":208,"height":166}
         * type : 1
         */

        private String name;
        private double probability;
        private LocationBean location;
        private int type;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public double getProbability() {
            return probability;
        }

        public void setProbability(double probability) {
            this.probability = probability;
        }

        public LocationBean getLocation() {
            return location;
        }

        public void setLocation(LocationBean location) {
            this.location = location;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public static class LocationBean {
            /**
             * width : 183
             * top : 103
             * left : 208
             * height : 166
             */

            private int width;
            private int top;
            private int left;
            private int height;

            public int getWidth() {
                return width;
            }

            public void setWidth(int width) {
                this.width = width;
            }

            public int getTop() {
                return top;
            }

            public void setTop(int top) {
                this.top = top;
            }

            public int getLeft() {
                return left;
            }

            public void setLeft(int left) {
                this.left = left;
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
