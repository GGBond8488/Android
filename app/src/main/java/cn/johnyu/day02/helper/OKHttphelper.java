package cn.johnyu.day02.helper;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OKHttphelper {
    public static String okhttphepler(String base64, String url) throws IOException {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(2000, TimeUnit.SECONDS)
                .readTimeout(2000, TimeUnit.SECONDS)
                .build();
        //上传文件域的请求体部分
        //RequestBody formBody = RequestBody.create(MediaType.parse("image/jpeg"),fileBuf);
        //整个上传的请求体部分（普通表单+文件上传域）
        /**
         RequestBody requestBody = new MultipartBody.Builder()
         .setType(MultipartBody.FORM)
         .addFormDataPart("title", "Square Logo")
         //filename:avatar,originname:abc.jpg
         .addFormDataPart("avart",uploadFilename,formBody)
         .build();
         */
        RequestBody requestBody = new FormBody.Builder()
                .add("avart", base64)
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();

        Response response = client.newCall(request).execute();
        String data = response.body().string();
        return data;

    }
}
