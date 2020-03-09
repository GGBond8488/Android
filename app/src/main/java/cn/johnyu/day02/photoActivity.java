package cn.johnyu.day02;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import cn.johnyu.day02.entity.FaceData;
import cn.johnyu.day02.entity.FaceDetect;
import cn.johnyu.day02.entity.FaceGroup;
import cn.johnyu.day02.entity.SimilarFace;
import cn.johnyu.day02.helper.CompressHelper;
import cn.johnyu.day02.helper.OKHttphelper;
import cn.johnyu.day02.helper.RegGeneral;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.EmptyStackException;
import java.util.concurrent.TimeUnit;

public class photoActivity extends AppCompatActivity {
    private String TAG = photoActivity.class.getName();
    //private CompressHelper compressHelper = new CompressHelper();
    private Gson gson = new Gson();
    private Uri imguri;
    private String person = "";
    private ImageView imageView;
    private String uploadFilename;
    private byte[] fileBuf;
    //private Matrix matrix = new Matrix();
    //private File tempfile;
    private String ImgBase64 = new String();
    //private String urlEncode;
    private TextView textView;
    private String url = "http://47.98.246.49:8080/android";
    private GestureDetector gue;
    private EditText editText;
    private String uid;
    private Handler handler = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        gue = new GestureDetector(this,new MyGestureListener());
        imageView = findViewById(R.id.img);
        textView = findViewById(R.id.beauty);
        editText = findViewById(R.id.editText);
        handler = new Handler();
    }

    public void photo(View view) throws Exception{
        File outImg = new File(getExternalCacheDir(),"temp.jpg");
        if(outImg.exists()) outImg.delete();
        outImg.createNewFile();

        if (Build.VERSION.SDK_INT>=24)
            imguri = FileProvider.getUriForFile(this,"cn.johnyu.day02.fileprovider",outImg);
        else
            imguri = Uri.fromFile(outImg);

        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT,imguri);
        startActivityForResult(intent,1);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 1:
                if (resultCode==RESULT_OK){
                    try{
                        //int radio = 8;
                        Bitmap map = BitmapFactory.decodeStream(getContentResolver().openInputStream(imguri));
                        //imageView.setImageBitmap(map);
                        Log.i("result",String.valueOf(map.getHeight()));
                        /**
                        matrix.setScale(0.05f,0.05f);
                        Bitmap mSrcBitmap = Bitmap.createBitmap(map, 0, 0, map.getWidth(), map.getHeight(), matrix, true);
                         */
                        map = CompressHelper.compressScale(map);
                        Log.i("result",String.valueOf(map.getHeight()));
                        //InputStream inputStream = getContentResolver().openInputStream(imguri);
                        ImgBase64 = convertToBase64(map);
                        imageView.setImageBitmap(map);
                    }catch (FileNotFoundException e){
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }break;
            case 2:
                handleSelect(data);break;
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void handleSelect(Intent intent) {
        Cursor cursor = null;
        if(intent==null){
            // Log.i("error",faceData.getError_msg());
            Toast.makeText(photoActivity.this,"还未选择图片奥~",Toast.LENGTH_LONG).show();
            return;
        }
        Uri uri = intent.getData();
        Log.i("uri", String.valueOf(uri));
        if("content".equalsIgnoreCase(uri.getScheme())){
            cursor = getContentResolver().query(uri,null,null,null);
            Log.i("cursor",String.valueOf(cursor));
            if(cursor.moveToFirst()) {
                int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME);
                uploadFilename = cursor.getString(columnIndex);
            }
            try{
                InputStream inputStream = getContentResolver().openInputStream(uri);
                //InputStream inputStream2 = getContentResolver().openInputStream(uri);
                fileBuf=convertToBytes(inputStream);
                Log.i("filebuf",fileBuf.length+"");
                Bitmap bitmap = BitmapFactory.decodeByteArray(fileBuf, 0, fileBuf.length);
                //imageView.setImageBitmap(bitmap);
                /**
                if(fileBuf.length > 100000){ matrix.setScale(0.05f,0.05f);
                    bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);}
                */
                bitmap = CompressHelper.compressScale(bitmap);
                ImgBase64 = convertToBase64(bitmap);
                imageView.setImageBitmap(bitmap);
            }catch (Exception e){
                e.printStackTrace();
            }
        }else Log.i("other","其他数据类型");
        cursor.close();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //得到了用户的允许
                   // openGallery();
                } else {
                    //用户拒绝
                    Toast.makeText(this,"读相册的操作被拒绝",Toast.LENGTH_LONG).show();
                }
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void getPrem(View view) {
        if(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        }
    }

    public void chooseFile(View view) {
        String[] permissions = new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };
    if(ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED){
        ActivityCompat.requestPermissions(this,permissions,1);
    }
    else {
        openGallery();
    }
    }

    private void openGallery() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent,2);
    }

    private byte[] convertToBytes(InputStream inputStream)throws Exception{
        Log.i("测试","这里被执行了1");
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        int len = 0;
        while ((len = inputStream.read(buf)) > 0) {
            out.write(buf, 0, len);
        }
        out.close();
        inputStream.close();
        return  out.toByteArray();
    }

    //将图片转换为base64编码
    @RequiresApi(api = Build.VERSION_CODES.O)
    private String convertToBase64(Bitmap bitmap)throws Exception{
        Log.i("测试","这里被执行了2");
        //byte[] buf=new byte[inputStream.available()];
        //inputStream.read(buf);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        //BitmapFactory.Options options = new BitmapFactory.Options();
        //options.inSampleSize = 4;
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,out);
        Log.i("compresspic",String.valueOf(out.size()));
       // URL url = Thread.currentThread().getContextClassLoader()
               // .getResource("cage1.jpeg");
        //压缩后转向到内存中
        /*
        Thumbnails.of(inputStream)//可以是文件名或输入流
                .size(100, 100)
               // .rotate(90)//转90度
                .keepAspectRatio(true)//保持比例(默认)
                .toOutputStream(out);
        */
        //将压缩后的图片变成Base64
        String s=Base64.getEncoder().encodeToString(out.toByteArray());
        //urlEncode = Base64.getUrlEncoder().encodeToString(out.toByteArray());
        //System.out.println(s);
        //String s =new String(Base64.getEncoder().encode(buf));
        Log.i("图片编码",s);
       // Log.i("URLBASE64",urlEncode);
        out.close();
        //inputStream.close();
        return s;
    }

    //文件上传的处理
    /**
    public void upload(View view) {
        new Thread() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient.Builder()
                        .connectTimeout(2000, TimeUnit.SECONDS)
                        .readTimeout(2000,TimeUnit.SECONDS)
                        .build();
                //上传文件域的请求体部分
                RequestBody formBody = RequestBody.create(MediaType.parse("image/jpg"),fileBuf);
                //整个上传的请求体部分（普通表单+文件上传域）
                RequestBody requestBody = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("title", "Square Logo")
                        //filename:avatar,originname:abc.jpg
                        .addFormDataPart("avart",uploadFilename,formBody)
                        .build();
                Request request = new Request.Builder()
                        .url(url+"/GradeBeauty")
                        .post(requestBody)
                        .build();

                try {
                    Response response = client.newCall(request).execute();
                    //Log.i("数据", response.body().string() + "....");
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        }.start();
    }
    */
    public void uploadByBase64(View view){
        new Thread() {
            @Override
            public void run() {
                if(!ImgBase64.isEmpty()) {
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
                            .add("avart", ImgBase64)
                            .build();
                    Request request = new Request.Builder()
                            .url(url + "/GradeBeautyFromBase64")
                            .post(requestBody)
                            .build();

                    try {
                        Response response = client.newCall(request).execute();
                        String data = response.body().string();
                        if (data.length() < 400) {
                            FaceData faceData = gson.fromJson(data, FaceData.class);
                            Looper.prepare();
                            Log.i("error", faceData.getError_msg());
                            Toast.makeText(photoActivity.this, faceData.getError_msg(), Toast.LENGTH_LONG).show();
                            Looper.loop();
                        } else {
                            // Log.i("msg", data);
                            FaceDetect faceDetect = gson.fromJson(data, FaceDetect.class);
                            Log.i("beauty", faceDetect.getResult().getFace_list().get(0).getBeauty() + "");
                            person += "     " + "颜值" + faceDetect.getResult().getFace_list().get(0).getBeauty() + "\n" + "     年龄" + faceDetect.getResult().getFace_list().get(0).getAge() + "\n" + "     脸型" + faceDetect.getResult().getFace_list().get(0).getFace_shape().getType() + "\n";
                            handler.post(runnableUi);
                            Log.i("数据", data + "....");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    Looper.prepare();
                    // Log.i("error",faceData.getError_msg());
                    Toast.makeText(photoActivity.this,"还未选择图片奥~",Toast.LENGTH_LONG).show();
                    Looper.loop();
                }
            }
        }.start();
    }


    public void getYourSimilarStar(View view) {
        new Thread() {
            @Override
            public void run() {
                if(!ImgBase64.isEmpty()) {
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
                            .add("avart", ImgBase64)
                            .build();
                    Request request = new Request.Builder()
                            .url(url + "/FindSimilarFace")
                            .post(requestBody)
                            .build();

                    try {
                        Response response = client.newCall(request).execute();
                        String data = response.body().string();
                        if (data.length() < 300) {
                            FaceData faceData = gson.fromJson(data, FaceData.class);
                            Looper.prepare();
                            Log.i("error", faceData.getError_msg());
                            Toast.makeText(photoActivity.this, faceData.getError_msg(), Toast.LENGTH_LONG).show();
                            Looper.loop();
                        } else {
                            // Log.i("msg", data);
                            SimilarFace faceDetect = gson.fromJson(data, SimilarFace.class);
                            Log.i("beauty", faceDetect.getResult().getUser_list().get(0).getGroup_id() + "");
                           // String location = faceDetect.getResult().getUser_list().get(0).getGroup_id();
                            String str = faceDetect.getResult().getUser_list().get(0).getScore()+"";
                            if(str.length()>=6) str = str.substring(0,6);
                            person += "     明星" + faceDetect.getResult().getUser_list().get(0).getUser_info() + "\n     相似度: " + str+ "% \n" + "     地区" + faceDetect.getResult().getUser_list().get(0).getGroup_id();
                            handler.post(runnableUi);
                            Log.i("数据", data + "....");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();

                    }
                }
                else {
                    Looper.prepare();
                    // Log.i("error",faceData.getError_msg());
                    Toast.makeText(photoActivity.this,"还未选择图片奥~",Toast.LENGTH_LONG).show();
                    Looper.loop();
                }
            }
        }.start();

    }


    public void recongnizeMore(View view) {
        new Thread(){
            @Override
            public void run(){
                if(!ImgBase64.isEmpty()){
                String urls = url+"/FindSevFace";
                try {
                    String data = OKHttphelper.okhttphepler(ImgBase64,urls);
                    Log.i("data",data);
                    if(data.length()<300){

                        FaceData faceData = gson.fromJson(data,FaceData.class);
                        Looper.prepare();
                        Log.i("error",faceData.getError_msg());
                        Toast.makeText(photoActivity.this,faceData.getError_msg(),Toast.LENGTH_LONG).show();
                        Looper.loop();
                    }else {
                        // Log.i("msg", data);
                        FaceGroup faceGroup = gson.fromJson(data, FaceGroup.class);
                        //Log.i("beauty", .getResult().getUser_list().get(0).getGroup_id()+ "");
                        //String person = "";
                        for(int i=0;i<faceGroup.getResult().getFace_num();++i){
                            person+="          "+faceGroup.getResult().getFace_list().get(i).getUser_list().get(0).getUser_id();
                        }
                        handler.post(runnableUi);
                        Log.i("数据", data + "....");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }}
                else {
                    Looper.prepare();
                   // Log.i("error",faceData.getError_msg());
                    Toast.makeText(photoActivity.this,"还未选择图片奥~",Toast.LENGTH_LONG).show();
                    Looper.loop();
                }
            }
        }.start();
    }


    public void uploadFaceByBase64(View view) {
        uid = editText.getText().toString();
        Log.i("uid",uid);
        new Thread() {
            @Override
            public void run() {
                if(!ImgBase64.isEmpty()) {
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
                            .add("avart", ImgBase64)
                            .add("uid", uid)
                            .build();
                    Request request = new Request.Builder()
                            .url(url + "/uploadUserPicBybase64")
                            .post(requestBody)
                            .build();

                    try {
                        Response response = client.newCall(request).execute();
                        String data = response.body().string();
                        JSONObject jsonObject = new JSONObject(data);
                        if (jsonObject.get("face_token").toString().isEmpty()) {
                            Looper.prepare();
                            Toast.makeText(photoActivity.this, "上传成功", Toast.LENGTH_LONG).show();
                            Looper.loop();
                        } else {
                            FaceData faceData = gson.fromJson(data, FaceData.class);
                            Looper.prepare();
                            Log.i("error", faceData.getError_msg());
                            Toast.makeText(photoActivity.this, faceData.getError_msg(), Toast.LENGTH_LONG).show();
                            Looper.loop();
                        }
                        Log.i("上传", data);

                        /**
                         if(data.length()<200){
                         Looper.prepare();
                         Toast.makeText(photoActivity.this,"上传成功",Toast.LENGTH_LONG).show();
                         Looper.loop();
                         }else {
                         // Log.i("msg", data);
                         Looper.prepare();
                         Toast.makeText(photoActivity.this,"上传失败",Toast.LENGTH_LONG).show();
                         Looper.loop();
                         /**
                         FaceDetect faceDetect = gson.fromJson(data, FaceDetect.class);
                         Log.i("beauty", faceDetect.getResult().getFace_list().get(0).getBeauty() + "");
                         textView.setText(faceDetect.getResult().getFace_list().get(0).getBeauty() + "");
                         Log.i("数据", data + "....");

                         }
                         */
                    } catch (IOException | JSONException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    Looper.prepare();
                    // Log.i("error",faceData.getError_msg());
                    Toast.makeText(photoActivity.this,"还未选择图片奥~",Toast.LENGTH_LONG).show();
                    Looper.loop();
                }
            }
        }.start();
    }


    class MyGestureListener extends GestureDetector.SimpleOnGestureListener{
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,float velocityY)
        {
            float startX = e1.getX();
            float endX = e2.getX();
            float startY = e1.getY();
            float endY = e2.getY();

            if ((endX - startX)>50&&Math.abs(startY-endY)<200) {
                Intent intent = new Intent(photoActivity.this, LetterActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.anim_in,R.anim.anim_out);
                return true;
            }
            if((startX - endX)>50&&Math.abs(startY-endY)<200){
                Intent intent = new Intent(photoActivity.this, LogoActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.anim_in,R.anim.anim_out);
                return true;
            }

            return false;
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent){
        gue.onTouchEvent(motionEvent);
        return super.onTouchEvent(motionEvent);
    }

    Runnable   runnableUi=new  Runnable(){
        @Override
        public void run() {
            //更新界面
            //textView.setText(null);
            textView.setText(person);
            person="";
        }

    };
}
