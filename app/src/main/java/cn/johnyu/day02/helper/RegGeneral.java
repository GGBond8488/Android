package cn.johnyu.day02.helper;

import android.util.Log;

import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RegGeneral {
    /**
     * 重要提示代码中所需工具类
     * FileUtil,Base64Util,HttpUtil,GsonUtils请从
     * https://ai.baidu.com/file/658A35ABAB2D404FBF903F64D47C1F72
     * https://ai.baidu.com/file/C8D81F3301E24D2892968F09AE1AD6E2
     * https://ai.baidu.com/file/544D677F5D4E4F17B4122FBD60DB82B3
     * https://ai.baidu.com/file/470B3ACCA3FE43788B5A963BF0B625F3
     * 下载
     */
    public static String general(String urlbase64) {
        // 请求url
        String url = "https://aip.baidubce.com/rest/2.0/ocr/v1/general_basic";
        try {
            // 本地文件路径
            /**
            String filePath = "[本地文件路径]";
            byte[] imgData = FileUtil.readFileByBytes(filePath);
            String imgStr = Base64Util.encode(imgData);
            String imgParam = URLEncoder.encode(imgStr, "UTF-8");
            */
            //String param = "image=" + urlbase64;

            // 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
            String accessToken = AuthService.getAuth();
            String posturl = url+"?access_token="+accessToken;
            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(2000, TimeUnit.SECONDS)
                    .readTimeout(2000,TimeUnit.SECONDS)
                    .build();
            RequestBody requestBody = new FormBody.Builder()
                    .add("image",urlbase64)
                   // .add("detect_language","true")
                    .build();
            Request request = new Request.Builder()
                    .header("Content-Type","application/x-www-form-urlencoded")
                    .url(posturl)
                    .post(requestBody)
                    .build();
            Response response = client.newCall(request).execute();
            String result = response.body().string();
            Log.i("data",result);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
