package cn.johnyu.day02;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import cn.johnyu.day02.entity.FaceData;
import cn.johnyu.day02.entity.FaceGroup;
import cn.johnyu.day02.entity.Wordsdata;
import cn.johnyu.day02.helper.CompressHelper;
import cn.johnyu.day02.helper.ImgHepler;
import cn.johnyu.day02.helper.RegGeneral;

import android.Manifest;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class LetterActivity extends AppCompatActivity {
    private String TAG = photoActivity.class.getName();
    //private CompressHelper compressHelper = new CompressHelper();
    private String person = "";
    private Gson gson = new Gson();
    private Uri imguri;
    private ImageView imageView;
    private String uploadFilename;
    private byte[] fileBuf;
    private Matrix matrix = new Matrix();
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
        setContentView(R.layout.activity_letter);
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
            Toast.makeText(LetterActivity.this,"还未选择图片奥~",Toast.LENGTH_LONG).show();
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

    public void recongnizeletter(View view) {
        new Thread() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void run() {
                if(!ImgBase64.isEmpty()){
                String data = RegGeneral.general(ImgBase64);
                Log.i("s", data);
                if (data.length() < 100) {
                    FaceData faceData = gson.fromJson(data, FaceData.class);
                    Looper.prepare();
                    Log.i("error", faceData.getError_msg());
                    Toast.makeText(LetterActivity.this, faceData.getError_msg(), Toast.LENGTH_LONG).show();
                    Looper.loop();
                } else {
                    // Log.i("msg", data);
                    Wordsdata wordsdata = gson.fromJson(data, Wordsdata.class);
                    //Log.i("beauty", .getResult().getUser_list().get(0).getGroup_id()+ "");
                    for (int i = 0; i < wordsdata.getWords_result_num(); ++i) {
                        person += "   " + wordsdata.getWords_result().get(i).getWords() + "\n";
                    }
                    if (person.length()>60) person = person.substring(0,60);
                    Log.i("数据", person);
                    handler.post(runnableUi);
                    if (copy(person)) {
                        Looper.prepare();
                        // Log.i("error",faceData.getError_msg());
                        //Toast.makeText(photoActivity.this,faceData.getError_msg(),Toast.LENGTH_LONG).show();
                        Toast.makeText(LetterActivity.this, "已将内容复制到剪切板", Toast.LENGTH_LONG).show();
                        Looper.loop();
                    }
                    //textView.setText(person);
                    Log.i("数据", data + "....");

                }
                }
                else {
                    Looper.prepare();
                    // Log.i("error",faceData.getError_msg());
                    Toast.makeText(LetterActivity.this,"还未选择图片奥~",Toast.LENGTH_LONG).show();
                    Looper.loop();
                }
            }}.start();

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
                Intent intent = new Intent(LetterActivity.this,LogoActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.anim_in,R.anim.anim_out);
                return true;
            }
            if((startX - endX)>50&&Math.abs(startY-endY)<200){
                Intent intent = new Intent(LetterActivity.this,photoActivity.class);
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

    /**
     * 复制内容到剪切板
     *
     * @param copyStr
     * @return
     */
    private boolean copy(String copyStr) {
        try {
            //获取剪贴板管理器
            ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            // 创建普通字符型ClipData
            ClipData mClipData = ClipData.newPlainText("Label", copyStr);
            // 将ClipData内容放到系统剪贴板里。
            cm.setPrimaryClip(mClipData);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    Runnable   runnableUi=new  Runnable(){
        @Override
        public void run() {
            //更新界面
            textView.setText("");
            textView.setText(person);
        }

    };
}
