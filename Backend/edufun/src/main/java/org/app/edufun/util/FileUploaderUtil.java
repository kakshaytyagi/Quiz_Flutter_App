package org.app.edufun.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

public class FileUploaderUtil {

    public static boolean uploadFile(MultipartFile multipartFile, String upload_dir, String filename) {
        boolean f = false;
//        try {
//            if (Profile_Service_Impl.checkFileFormat(multipartFile) == false) {
//                return false;
//            } else {
//                InputStream fIS = multipartFile.getInputStream();
//                byte[] datafile = new byte[fIS.available()];
//                fIS.read(datafile);
//
//                FileOutputStream fOS = new FileOutputStream(upload_dir + File.separator + filename);
//                fOS.write(datafile);
//                fOS.close();
//                f = true;
//                return f;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            return f;
//        }
        return false;
    }
}
