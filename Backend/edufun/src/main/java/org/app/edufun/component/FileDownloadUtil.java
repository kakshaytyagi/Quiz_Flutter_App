package org.app.edufun.component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
 
public class FileDownloadUtil {
    private Path foundFile;
     
    public Resource getFileAsResource(String fileCode) throws IOException {
        Path dirPath = Paths.get("D:\\\\GIT PROJECT\\\\srn-recruiter-app-main\\\\Recruiter\\\\src\\\\main\\\\resources\\\\Upload_Files");
         
        Files.list(dirPath).forEach(file -> {
            if (file.getFileName().toString().startsWith(fileCode)) {
                foundFile = file;
                return;
            }
        });
 
        if (foundFile != null) {
            return new UrlResource(foundFile.toUri());
        }
         
        return null;
    }
}
