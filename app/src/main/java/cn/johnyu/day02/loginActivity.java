package cn.johnyu.day02;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import androidx.appcompat.app.AppCompatActivity;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class loginActivity extends AppCompatActivity {

    private String email;
    private String password;
    private EditText editText1;
    private EditText editText2;
    private String url = "http://47.98.246.49:8080/android";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        editText1 = findViewById(R.id.username);
        editText2 = findViewById(R.id.password);
        email = editText1.getText().toString();
        password = editText2.getText().toString();
    }

    public void loginOrRegister(View view) throws IOException {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(2000, TimeUnit.SECONDS)
                .readTimeout(2000,TimeUnit.SECONDS)
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
                .add("user",email)
                .add("pwd",password)
                .build();
        Request request = new Request.Builder()
                .url(url+"/loginorregister")
                .post(requestBody)
                .build();
        Response response = client.newCall(request).execute();
        String data = response.body().string();
        Log.i("data",data);
    }
}