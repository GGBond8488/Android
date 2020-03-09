package com.liandroid.lios.Service;

import com.liandroid.lios.helper.BaiduAIHelper;
import com.liandroid.lios.helper.Base64Helper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.List;
import java.util.Base64;

@Service
public class handleFileUpload {

    private byte[] Base64Img = null;
    private Base64Helper base64Helper =new Base64Helper();
    //private Base64 base64 = new Base64();
    //public String newFilePath = new String();
    private BaiduAIHelper baiduAIHelper = new BaiduAIHelper();

    public String handleFileupload(List<MultipartFile> files){

        return baiduAIHelper.FaceDetec(base64Helper.getImaBase64(files));
    }

    public String handleUplUserPic(List<MultipartFile> files,String uid){

        return baiduAIHelper.FaceUpload(base64Helper.getImaBase64(files),uid);
    }

    public String SearchUid(List<MultipartFile> files){
        return baiduAIHelper.FindSimilarFace(base64Helper.getImaBase64(files));
    }

}
