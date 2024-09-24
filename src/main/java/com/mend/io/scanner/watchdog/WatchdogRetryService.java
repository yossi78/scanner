package com.mend.io.scanner.watchdog;

import com.mend.io.scanner.data_layer.Scan;
import com.mend.io.scanner.model.ScanAction;
import com.mend.io.scanner.service.ScanService;
import com.mend.io.scanner.util.FileUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;
import org.springframework.scheduling.annotation.Scheduled;
import java.util.List;


@Service
@AllArgsConstructor
@Slf4j
@EnableScheduling
public class WatchdogRetryService {

    private final WatchdogFileService watchdogFileService;

    private final ScanService scanService;

    @Scheduled(fixedRate = 60000)
    public void performTask() {
        List<ScanAction> userActions = watchdogFileService.readAllOperations();
        for ( ScanAction scanAction: userActions)
        {
            switch (scanAction.getScanActionType()) {
                case ADD:
                    log.info("Add new scan from file ");
                    Scan scan = scanService.initiateScan(scanAction.getScan(),false);
                    if(scan!=null && scan.getIsScanned()){
                        FileUtil.deleteFile(scan.getFilePath());
                    }
                    break;
            }
        }


    }
}
