package com.liandroid.lios;


import com.liandroid.lios.helper.BaiduAIHelper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public  class LiosApplicationTests {

    @Test
    public void contextLoads() {
    }

    @Test
    public void testBaiduAI(){
        BaiduAIHelper baiduAIHelper = null;
        String s = "123";
        s =baiduAIHelper.FaceDetec("[B@50da2435");
        System.out.println(s);
    }

}
