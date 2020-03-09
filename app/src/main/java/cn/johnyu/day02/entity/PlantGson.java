package cn.johnyu.day02.entity;

import java.util.List;

public class PlantGson {

    /**
     * log_id : 2156773978763979439
     * result : [{"score":0.14000000059605,"name":"马兜铃"},{"score":0.089646860957146,"name":"贝壳花"},{"score":0.059999998658895,"name":"北马兜铃"},{"score":0.059999998658895,"name":"辣椒"},{"score":0.043586805462837,"name":"臭菘"}]
     */

    private long log_id;
    private List<ResultBean> result;

    public long getLog_id() {
        return log_id;
    }

    public void setLog_id(long log_id) {
        this.log_id = log_id;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * score : 0.14000000059605
         * name : 马兜铃
         */

        private double score;
        private String name;

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
    }
}
