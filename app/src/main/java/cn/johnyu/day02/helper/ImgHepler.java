package cn.johnyu.day02.helper;

import android.graphics.Bitmap;
import android.os.Build;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Base64;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

public class ImgHepler {
    @Override
    public boolean equals(@Nullable Object obj) {
        return super.equals(obj);
    }

    public static byte[] convertToBytes(InputStream inputStream)throws Exception{
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

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String convertToBase64(Bitmap bitmap)throws Exception{
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
        String s= Base64.getEncoder().encodeToString(out.toByteArray());
        //urlEncode = Base64.getUrlEncoder().encodeToString(out.toByteArray());
        //System.out.println(s);
        //String s =new String(Base64.getEncoder().encode(buf));
        Log.i("图片编码",s);
        // Log.i("URLBASE64",urlEncode);
        out.close();
        //inputStream.close();
        return s;
    }
}
