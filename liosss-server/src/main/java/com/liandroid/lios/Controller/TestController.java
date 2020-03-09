package com.liandroid.lios.Controller;

import com.fasterxml.jackson.databind.ser.Serializers;
import com.liandroid.lios.Service.handleFileUpload;
import com.liandroid.lios.helper.BaiduAIHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class TestController {

    @Autowired private handleFileUpload handleFileUpload;
    private BaiduAIHelper baiduAIHelper = new BaiduAIHelper();

    @RequestMapping("/test")
    @ResponseBody
    public String test(HttpServletRequest request){

        return request.getAuthType();
    }

    @RequestMapping("/GradeBeauty")
    @ResponseBody
    public String handleFileUpload(HttpServletRequest request){
        MultipartHttpServletRequest params = (MultipartHttpServletRequest)request;
        List<MultipartFile> files = ((MultipartHttpServletRequest)request).getFiles("avart");
        String title = params.getParameter("title");
        System.out.println(title);
        return handleFileUpload.handleFileupload(files);
    }

    @RequestMapping("/GradeBeautyFromBase64")
    @ResponseBody
    public String handleFileUploadBytes(@RequestParam(value = "avart")String Img){
        return baiduAIHelper.FaceDetec(Img);
    }

   @RequestMapping("/uploadUserPic")
   @ResponseBody
    public String handleUplUserPic(HttpServletRequest request){
        MultipartHttpServletRequest params = (MultipartHttpServletRequest)request;
        List<MultipartFile> files = ((MultipartHttpServletRequest)request).getFiles("avart");
        String uid = params.getParameter("UID");
        return handleFileUpload.handleUplUserPic(files,uid);
    }

    @RequestMapping("/uploadUserPicBybase64")
    @ResponseBody
    public String handleUplUserPicBybase64(@RequestParam(value = "avart")String Img ,@RequestParam(value = "uid")String uid){
        return baiduAIHelper.FaceUpload(Img,uid);
    }

    @RequestMapping("/FindSimilarFace")
    @ResponseBody
    public String finduser(@RequestParam(value = "avart")String Img){
        return baiduAIHelper.FindSimilarFace(Img);
    }

    @RequestMapping("/FindSevFace")
    @ResponseBody
    public String findSevuser(@RequestParam(value = "avart")String Img){
        return baiduAIHelper.FindFace(Img);
    }

    @RequestMapping("/loginorregister")
    @ResponseBody
    public String processloginorregister(@RequestParam(value = "user")String user,@RequestParam(value = "pwd")String password){
        return "";
    }

}
