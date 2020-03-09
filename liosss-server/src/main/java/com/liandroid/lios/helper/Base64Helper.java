package com.liandroid.lios.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Base64;
import java.util.List;

public class Base64Helper {
    private Logger LOG = LoggerFactory.getLogger(this.getClass());

    public String getImaBase64(List<MultipartFile> files) {
        String imgBase64 = null;
        MultipartFile file = null;
        BufferedOutputStream stream = null;
        for (int i = 0; i < files.size(); ++i) {
            file = files.get(i);
            if (!file.isEmpty()) {
                try {
                    byte[] bytes = file.getBytes();
                    // File path = ResourceUtils.getFile("classpath:templates");
                    // String basePath = request.getServletContext().getRealPath("image/");
                    File tempFile = new File(file.getOriginalFilename());
                    if (tempFile.exists()) tempFile.delete();
                    else {
                        tempFile.createNewFile();
                        stream = new BufferedOutputStream(new FileOutputStream(tempFile));
                        LOG.info(stream.toString());
                        LOG.info(tempFile.getAbsolutePath());
                        LOG.info(tempFile.getCanonicalPath());
                        // Base64Img[i] = String.valueOf(Base64.encodeBase64(bytes));
                        stream.write(bytes, 0, bytes.length);
                        InputStream inputStream = new FileInputStream(tempFile);
                        byte[] buf = new byte[inputStream.available()];
                        inputStream.read(buf);
                        imgBase64 = new String(Base64.getEncoder().encode(buf));
                        stream.flush();
                        stream.close();
                        inputStream.close();
                        tempFile.delete();
                    }
                } catch (Exception E) {
                    stream = null;
                    E.printStackTrace();
                    return "error";
                }
            }
        }
        return imgBase64;
    }
}