package cn.johnyu.day02.entity;

public class FaceData {

    /**
     * result : null
     * log_id : 3500145101001
     * error_msg : pic not has face
     * cached : 0
     * error_code : 222202
     * timestamp : 1573458463
     */

    private Object result;
    private long log_id;
    private String error_msg;
    private int cached;
    private int error_code;
    private int timestamp;

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
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
}
