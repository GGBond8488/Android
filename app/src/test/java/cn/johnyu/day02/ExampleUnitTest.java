package cn.johnyu.day02;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void test1() throws Exception{
            OutputStream outputStream=new FileOutputStream("/Users/johnyu/aa.txt",true);
            outputStream.write("aa我是中国人\n".getBytes());
            outputStream.close();

    }

    @Test
    public void test2() throws Exception{
        InputStream inputStream=new FileInputStream("/Users/johnyu/aa.txt");
//        int m=inputStream.read();

        int len=inputStream.available();
        byte[] buff=new byte[len];
        inputStream.read(buff);

        System.out.println(new String(buff,"UTF-8"));
    }

    @Test
    public void test3() throws Exception{
        InputStream inputStream=new FileInputStream("/Users/johnyu/aa.txt");
        BufferedReader reader=new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));
        String line=null;
        int i=0;
        while ((line=reader.readLine())!=null){
            System.out.println(++i+":\t"+line);
        }
        reader.close();

    }
}