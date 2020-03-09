package cn.johnyu.day02;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import cn.johnyu.day02.entity.FoodGson;
import cn.johnyu.day02.entity.LogoData;
import cn.johnyu.day02.entity.PlantGson;
import cn.johnyu.day02.entity.VehicleGson;
import cn.johnyu.day02.helper.CompressHelper;
import cn.johnyu.day02.helper.ImgHepler;
import cn.johnyu.day02.helper.ObjiectGeneral;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class LogoActivity extends AppCompatActivity {
    //private GestureDetector gue;
    private String TAG = photoActivity.class.getName();
    //private CompressHelper compressHelper = new CompressHelper();
    private Gson gson = new Gson();
    private Uri imguri;
    private ImageView imageView;
    private String uploadFilename;
    private byte[] fileBuf;
    private String person = "";
    //private Matrix matrix = new Matrix();
    //private File tempfile;
    private String ImgBase64 = new String();
    //private String urlEncode;
    private TextView textView;
    private String url = "http://47.98.246.49:8080/android";
    private GestureDetector gue;
    private Handler handler = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo);
        gue = new GestureDetector(this,new MyGestureListener());
        imageView = findViewById(R.id.img);
        textView = findViewById(R.id.beauty);
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
                        // int radio = 8;
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
                        ImgBase64 = ImgHepler.convertToBase64(map);
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
            Toast.makeText(LogoActivity.this,"还未选择图片奥~",Toast.LENGTH_SHORT).show();
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
                fileBuf=ImgHepler.convertToBytes(inputStream);
                Log.i("filebuf",fileBuf.length+"");
                Bitmap bitmap = BitmapFactory.decodeByteArray(fileBuf, 0, fileBuf.length);
                //imageView.setImageBitmap(bitmap);
                /**
                 if(fileBuf.length > 100000){ matrix.setScale(0.05f,0.05f);
                 bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);}
                 */
                bitmap = CompressHelper.compressScale(bitmap);
                ImgBase64 = ImgHepler.convertToBase64(bitmap);
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

    public void RecFood(View view) {
        new Thread() {
            @Override
            public void run() {
                if (!ImgBase64.isEmpty()) {
                    String data = ObjiectGeneral.FoodRec(ImgBase64);
                    if (data.isEmpty()) {
                        Looper.prepare();
                        //Log.i("error",faceData.getError_msg());
                        Toast.makeText(LogoActivity.this, "查询失败，当前网络可能不佳", Toast.LENGTH_LONG).show();
                        Looper.loop();
                    }
                    else {
                        if (data.length()>130) {
                            FoodGson foodGson = gson.fromJson(data, FoodGson.class);
                            String str = foodGson.getResult().get(0).getProbability();
                            if(str.length()>=6) str = str.substring(0,6);
                            person+="      得分：" +str+ "\n"+"      他一定是:"+foodGson.getResult().get(0).getName();
                            handler.post(runnableUi);
                        }
                        else {
                            Looper.prepare();
                            //Log.i("error",faceData.getError_msg());
                            Toast.makeText(LogoActivity.this, "图片不含菜", Toast.LENGTH_LONG).show();
                            Looper.loop();
                        }
                    }
                    Log.i("data", data);
                }else{
                    Looper.prepare();
                    // Log.i("error",faceData.getError_msg());
                    Toast.makeText(LogoActivity.this,"还未选择图片奥~",Toast.LENGTH_LONG).show();
                    Looper.loop();
                }
            }
        }.start();
    }

    public void RecLog(View view) {
        new Thread() {
            @Override
            public void run() {
                if(!ImgBase64.isEmpty()) {
                    String data = ObjiectGeneral.LogoRec(ImgBase64);
                    if (data.isEmpty()) {
                        Looper.prepare();
                        //Log.i("error",faceData.getError_msg());
                        Toast.makeText(LogoActivity.this, "查询失败，当前网络可能不佳噢~", Toast.LENGTH_LONG).show();
                        Looper.loop();
                    } else {
                        LogoData logoData = gson.fromJson(data, LogoData.class);
                        if (logoData.getResult().isEmpty()) {
                            person = "      当前图片不含logo";
                        } else {
                            String str = ""+logoData.getResult().get(0).getProbability();
                            if(str.length()>=6) str = str.substring(0,6);
                            person = "      得分:" +str+ "\n " +"      该logo是："+ logoData.getResult().get(0).getName();
                        }
                        handler.post(runnableUi);
                    }
                }
                else {
                    Looper.prepare();
                    //Log.i("error",faceData.getError_msg());
                    Toast.makeText(LogoActivity.this, "查询失败，当前网络可能不佳噢~", Toast.LENGTH_LONG).show();
                    Looper.loop();
                }
                //Log.i("data", data);
            }
        }.start();
    }

    public void RecVehicle(View view) {
        new Thread() {
            @Override
            public void run() {
                if(!ImgBase64.isEmpty()) {
                    String data = ObjiectGeneral.VehicleRec(ImgBase64);
                    if (data.isEmpty()) {
                        Looper.prepare();
                        //Log.i("error",faceData.getError_msg());
                        Toast.makeText(LogoActivity.this, "查询失败，当前网络可能不佳噢~", Toast.LENGTH_LONG).show();
                        Looper.loop();
                    }
                    else {
                        //数据显示
                        VehicleGson vehicleGson = gson.fromJson(data,VehicleGson.class);
                        String str = ""+vehicleGson.getResult().get(0).getScore();
                        if(str.length()>=6) str = str.substring(0,6);
                        person = "     得分:"+ str +"% \n"+"     车型"+vehicleGson.getResult().get(0).getName();
                        person+="\n"+"      年份为"+vehicleGson.getResult().get(0).getYear();
                        handler.post(runnableUi);
                    }
                }
                else {
                    Looper.prepare();
                    //Log.i("error",faceData.getError_msg());
                    Toast.makeText(LogoActivity.this, "查询失败，当前网络可能不佳噢~", Toast.LENGTH_LONG).show();
                    Looper.loop();
                }
            }
        }.start();
    }

    public void RecPlant(View view) {
        new Thread() {
            @Override
            public void run() {
                if (!ImgBase64.isEmpty()) {
                    String data = ObjiectGeneral.PlantRec(ImgBase64);
                    if (data.isEmpty()) {
                        Looper.prepare();
                        //Log.i("error",faceData.getError_msg());
                        Toast.makeText(LogoActivity.this, "查询失败，当前网络可能不佳噢~", Toast.LENGTH_LONG).show();
                        Looper.loop();
                    }
                    else {
                        //数据显示
                        PlantGson plantGson = gson.fromJson(data,PlantGson.class);
                        String str = ""+plantGson.getResult().get(0).getScore()+"";
                        if(str.length()>=6) str = str.substring(0,6);
                        person = "      得分:"+str+"\n"+"      植物类型:"+plantGson.getResult().get(0).getName();
                        //person+="\n"+"年份为"+vehicleGson.getResult().get(0).getYear();
                        handler.post(runnableUi);
                    }
                    Log.i("data", data);
                }
                else{
                    Looper.prepare();
                    // Log.i("error",faceData.getError_msg());
                    Toast.makeText(LogoActivity.this,"还未选择图片奥~",Toast.LENGTH_LONG).show();
                    Looper.loop();
                }
            }

        }.start();
    }

    class MyGestureListener extends GestureDetector.SimpleOnGestureListener{
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY)
        {
            float startX = e1.getX();
            float endX = e2.getX();
            float startY = e1.getY();
            float endY = e2.getY();

            if ((endX - startX)>50&&Math.abs(startY-endY)<200) {
                Intent intent = new Intent(LogoActivity.this, photoActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.anim_in,R.anim.anim_out);
                return true;
            }
            if((startX - endX)>50&&Math.abs(startY-endY)<200){
                Intent intent = new Intent(LogoActivity.this, LetterActivity.class);
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
            textView.setText("");
            textView.setText(person);
            person="";
        }

    };

}
