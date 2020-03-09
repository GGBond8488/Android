package com.liandroid.lios.helper;

import com.baidu.aip.face.AipFace;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.json.JSONObject;

import java.util.HashMap;

public class BaiduAIHelper {
    private static final String APP_ID = "17609660";
    private static final String API_KEY = "gmoQsY701BZEvT3dCP3szsqv";
    private static final String SECRET_KEY = "PvPBFXGEU0heBhDo5veP58Cftj7rl0SA";
    private static final HashMap<String,String> options = new HashMap<>();
    private AipFace client = new AipFace(APP_ID, API_KEY, SECRET_KEY);
    private AipFace client2 = new AipFace("17676403","8QDvA6eIqUzKfP51bHuq9KiG","dso2mPmTGLFLb4E0pw7pYkhpX58xITeL");
    private String famousGroup = "star_woman_asia,star_male_neidi,star_male_hongkong,star_male_taiwan,star_male_hanguo,star_male_riben,star_male_oumei,star_female_neidi,star_female_hongkong,star_female_taiwan";
    private Logger LOG = LoggerFactory.getLogger(this.getClass());
    //private static final String proxy_port = "";

    public String FaceDetec(String base64){
        // 初始化一个AipFace
        options.put("face_field", "age,beauty,face_shape");
        options.put("max_face_num", "2");
        options.put("face_type", "LIVE");
        options.put("liveness_control", "LOW");

        // 可选：设置网络连接参数
        //client.setConnectionTimeoutInMillis(2000);
        //client.setSocketTimeoutInMillis(60000);

        // 可选：设置代理服务器地址, http和socket二选一，或者均不设置
       // client.setHttpProxy("proxy_host", proxy_port);  // 设置http代理
       // client.setSocketProxy("proxy_host", proxy_port);  // 设置socket代理

        // 调用接口
        String image = base64;
        String imageType = "BASE64";

        // 人脸检测
        JSONObject res = client.detect(image, imageType,options);
        LOG.info(image);
        LOG.info(res.toString());
       // System.out.println(res.toString(2));
        return res.toString(2);
    }

    public String FaceUpload(String base64,String uid)
    {
        options.put("quality_control", "NORMAL");
        options.put("liveness_control", "LOW");
        options.put("action_type", "REPLACE");

        String image = base64;
        String imageType = "BASE64";
        String groupId = "Xos";
        String userId = uid;

        // 人脸注册
        JSONObject res = client.addUser(image, imageType, groupId, userId, options);
        System.out.println(res.toString(2));
        return res.toString(2);
    }

    public String FindFace(String base64)
    {
        options.put("max_face_num", "10");
        options.put("match_threshold", "70");
        options.put("quality_control", "NORMAL");
        options.put("liveness_control", "LOW");
        //options.put("user_id", "233451");
        options.put("max_user_num", "10");

        String image = base64;
        String imageType = "BASE64";
        String groupIdList = "Xos";

        // 人脸搜索
        JSONObject res = client.multiSearch(image, imageType, groupIdList, options);
        System.out.println(res.toString(2));
        return res.toString(2);
    }

    public String FindSimilarFace(String base64)
    {
        options.put("max_face_num", "3");
        options.put("match_threshold", "30");
        options.put("quality_control", "NORMAL");
        options.put("liveness_control", "LOW");
        //options.put("user_id", "233451");
        //options.put("max_user_num", "3");

        String image = base64;
        String imageType = "BASE64";
        String groupIdList = famousGroup;

        // 人脸搜索
        JSONObject res = client2.search(image, imageType, groupIdList, options);
        System.out.println(res.toString(2));
        return res.toString(2);
    }
}
