package cn.johnyu.day02.entity;

import java.util.List;

public class Wordsdata {

    /**
     * log_id : 5833022606917974127
     * words_result_num : 5
     * words_result : [{"words":"于夏情期望失望"},{"words":"者认为之有些什么"},{"words":"么把"},{"words":"星"},{"words":"又导后的天形"}]
     */

    private long log_id;
    private int words_result_num;
    private List<WordsResultBean> words_result;

    public long getLog_id() {
        return log_id;
    }

    public void setLog_id(long log_id) {
        this.log_id = log_id;
    }

    public int getWords_result_num() {
        return words_result_num;
    }

    public void setWords_result_num(int words_result_num) {
        this.words_result_num = words_result_num;
    }

    public List<WordsResultBean> getWords_result() {
        return words_result;
    }

    public void setWords_result(List<WordsResultBean> words_result) {
        this.words_result = words_result;
    }

    public static class WordsResultBean {
        /**
         * words : 于夏情期望失望
         */

        private String words;

        public String getWords() {
            return words;
        }

        public void setWords(String words) {
            this.words = words;
        }
    }
}
