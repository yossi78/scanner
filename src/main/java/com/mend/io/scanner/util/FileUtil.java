package com.mend.io.scanner.util;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.File;
import java.io.IOException;



public class FileUtil {


    public static void saveToFile(String path, String fileNameAndExtension, Object object) throws IOException {
        ObjectMapper objectMapper =  new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
        if (!path.endsWith(File.separator)) {
            path += File.separator;
        }
        String fullPath = path + fileNameAndExtension;
        objectMapper.writeValue(new File(fullPath), object);
    }



    public static boolean deleteFile(String filePath) {
        try {
            File file = new File(filePath);
            file.delete();
        }catch (Exception e){
            return false;
        }
        return true;
    }



}
