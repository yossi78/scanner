package com.mend.io.scanner.watchdog;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.mend.io.scanner.model.ScanAction;
import com.mend.io.scanner.util.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@Slf4j
public class WatchdogFileService {


    private static final String WATCHDOG_FILES_PATH = "c:\\temp\\watchdog\\";
    private final ObjectMapper objectMapper;

    public WatchdogFileService() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    public void appendOperation(ScanAction scanAction) {
        try {
            String fileName = System.currentTimeMillis() + ".json";
            scanAction.getScan().setFilePath(WATCHDOG_FILES_PATH+fileName);
            FileUtil.saveToFile(WATCHDOG_FILES_PATH,fileName,scanAction);
        } catch (IOException e) {
            log.error("Couldn't create new watchdog file the exception is:", e);
        }
    }

    private List<String> getAllFileNames() {
        File folder = new File(WATCHDOG_FILES_PATH);
        List<String> fileNames = new ArrayList<>();

        if (folder.isDirectory()) {
            File[] files = folder.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isFile()) {
                        fileNames.add(file.getName());
                    }
                }
            }
        }
        Collections.sort(fileNames);
        return fileNames;
    }


    public List<ScanAction> readAllOperations() {
        try {
            List<ScanAction> watchdogOperations = new ArrayList<>();
            List<String> fileNames = getAllFileNames();
            for (String fileName : fileNames) {
                watchdogOperations.add(readOperation(fileName));
            }
            return watchdogOperations;
        } catch (Exception e) {
            log.error("Error reading all the watchdog files, the exception is:", e);
            return List.of();
        }

    }

    public ScanAction readOperation(String fileName) {
        ScanAction userAction=null;
        try {
            File file = new File(WATCHDOG_FILES_PATH + fileName);
            userAction = objectMapper.readValue(file, ScanAction.class);
            userAction.getScan().setFilePath(WATCHDOG_FILES_PATH + fileName);
        }catch (Exception e){
            System.out.println("Failed to parse file: " + e.getMessage());
        }
        return userAction;
    }

}
