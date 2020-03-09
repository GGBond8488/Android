package cn.johnyu.day02.entity;

import java.util.List;

public class FoodGson {

    /**
     * log_id : 4844594466129179279
     * result_num : 5
     * result : [{"calorie":"260","has_calorie":true,"name":"猪蹄","probability":"0.116462"},{"calorie":"318","has_calorie":true,"name":"糖醋排骨","probability":"0.10912"},{"calorie":"175","has_calorie":true,"name":"三杯鸡","probability":"0.0924444"},{"has_calorie":false,"name":"话梅猪手","probability":"0.0533611"},{"calorie":"197","has_calorie":true,"name":"宫保鸡丁","probability":"0.0328945"}]
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
         * calorie : 260
         * has_calorie : true
         * name : 猪蹄
         * probability : 0.116462
         */

        private String calorie;
        private boolean has_calorie;
        private String name;
        private String probability;

        public String getCalorie() {
            return calorie;
        }

        public void setCalorie(String calorie) {
            this.calorie = calorie;
        }

        public boolean isHas_calorie() {
            return has_calorie;
        }

        public void setHas_calorie(boolean has_calorie) {
            this.has_calorie = has_calorie;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getProbability() {
            return probability;
        }

        public void setProbability(String probability) {
            this.probability = probability;
        }
    }
}
