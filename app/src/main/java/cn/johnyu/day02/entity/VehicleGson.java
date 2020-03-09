package cn.johnyu.day02.entity;

import java.util.List;

public class VehicleGson {

    /**
     * log_id : 5053792215950224239
     * location_result : {"width":788.5726318359375,"top":106.1950225830078,"height":396.6568603515625,"left":13.60648155212402}
     * result : [{"score":0.9995930790901184,"name":"奥迪R8","year":"2007-2017"},{"score":1.704681053524837E-4,"name":"布加迪Chiron","year":"2018"},{"score":5.463857087306678E-5,"name":"NobleM15","year":"无年份信息"},{"score":3.679461951833218E-5,"name":"奥迪RS7","year":"2014-2016"},{"score":2.635523742355872E-5,"name":"奥迪RS4","year":"2018"}]
     * color_result : 蓝色
     */

    private long log_id;
    private LocationResultBean location_result;
    private String color_result;
    private List<ResultBean> result;

    public long getLog_id() {
        return log_id;
    }

    public void setLog_id(long log_id) {
        this.log_id = log_id;
    }

    public LocationResultBean getLocation_result() {
        return location_result;
    }

    public void setLocation_result(LocationResultBean location_result) {
        this.location_result = location_result;
    }

    public String getColor_result() {
        return color_result;
    }

    public void setColor_result(String color_result) {
        this.color_result = color_result;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class LocationResultBean {
        /**
         * width : 788.5726318359375
         * top : 106.1950225830078
         * height : 396.6568603515625
         * left : 13.60648155212402
         */

        private double width;
        private double top;
        private double height;
        private double left;

        public double getWidth() {
            return width;
        }

        public void setWidth(double width) {
            this.width = width;
        }

        public double getTop() {
            return top;
        }

        public void setTop(double top) {
            this.top = top;
        }

        public double getHeight() {
            return height;
        }

        public void setHeight(double height) {
            this.height = height;
        }

        public double getLeft() {
            return left;
        }

        public void setLeft(double left) {
            this.left = left;
        }
    }

    public static class ResultBean {
        /**
         * score : 0.9995930790901184
         * name : 奥迪R8
         * year : 2007-2017
         */

        private double score;
        private String name;
        private String year;

        public double getScore() {
            return score;
        }

        public void setScore(double score) {
            this.score = score;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getYear() {
            return year;
        }

        public void setYear(String year) {
            this.year = year;
        }
    }
}
