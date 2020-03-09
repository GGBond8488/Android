package cn.johnyu.day02.helper;

import android.util.Log;

import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ObjiectGeneral {
    private static final String clientId = "beBTttqFQtdT611NGKrasg8X";
    // 官网获取的 Secret Key 更新为你注册的
    private static final String clientSecret = "FRiRv86N9ICGZ1Tlp0gfDDWs2Ew1KN4m";

    //菜品
    public static String FoodRec(String base64){
        String url = "https://aip.baidubce.com/rest/2.0/image-classify/v2/dish";
        return Rec(base64,url);
    }
    //logo
    public static String LogoRec(String base64){
        String url = "https://aip.baidubce.com/rest/2.0/image-classify/v2/logo";
        return Rec(base64,url);
    }
    //车型识别
    public static String VehicleRec(String base64){
        String url = "https://aip.baidubce.com/rest/2.0/image-classify/v1/car";
        return Rec(base64,url);
    }

    //植物识别
    public static String PlantRec(String base64){
        String url = "https://aip.baidubce.com/rest/2.0/image-classify/v1/plant";
        return Rec(base64,url);
    }
    public static String Rec(String base64 ,String url){
        String accsess_token = AuthService.getAuth(clientId,clientSecret);
        //String url = "https://aip.baidubce.com/rest/2.0/image-classify/v2/dish";
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
            if (accessToken.isEmpty()) return null;
            String posturl = url+"?access_token="+accessToken;
            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(2000, TimeUnit.SECONDS)
                    .readTimeout(2000,TimeUnit.SECONDS)
                    .build();
            RequestBody requestBody = new FormBody.Builder()
                    .add("image",base64)
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

