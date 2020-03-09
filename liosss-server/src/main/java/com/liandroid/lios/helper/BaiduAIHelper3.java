package com.liandroid.lios.helper;

import com.baidu.aip.imageclassify.AipImageClassify;

import java.util.HashMap;

public class BaiduAIHelper3 {
    public static final String APP_ID = "17766761";
    public static final String API_KEY = "beBTttqFQtdT611NGKrasg8X";
    public static final String SECRET_KEY = "FRiRv86N9ICGZ1Tlp0gfDDWs2Ew1KN4m";
    AipImageClassify client = new AipImageClassify(APP_ID, API_KEY, SECRET_KEY);
    HashMap<String, String> options = new HashMap<String, String>();

}
