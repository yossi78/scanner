package com.mend.io.scanner.service;


import com.mend.io.scanner.data_layer.Scan;
import com.mend.io.scanner.data_layer.ScanRepository;
import com.mend.io.scanner.exception.ResourceNotFoundException;
import com.mend.io.scanner.model.ScanAction;
import com.mend.io.scanner.model.ScanActionType;
import com.mend.io.scanner.watchdog.WatchdogFileService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class ScanService {

    private ScanRepository scanRepository;
    private WatchdogFileService watchdogFileService;



    public Scan initiateScan(Scan scan,boolean isApi) {
        log.debug("Initiate scan");
        scan.setStatus("Pending");
        if(isApi){
            ScanAction scanAction = ScanAction.builder().scanActionType(ScanActionType.ADD).scan(scan).build();
            watchdogFileService.appendOperation(scanAction);
        }else{
            processScan(scan);
        }
        return scan;
    }

    public List<Scan> getScansByUserID(Long userId) {
        log.info("get scans By user Id="+userId);
        List<Scan> scans= scanRepository.findByUserId(userId);
        if(scans.isEmpty()){
            log.info("The user has not been found");
            throw new ResourceNotFoundException("User has not been found");
        }
        return scans;
    }



    public long countPendingScans() {
        long pendingScan =  scanRepository.countByStatus("Pending");
        log.info("Count pending scans = "+pendingScan);
        return pendingScan;
    }



    public int getTotalIssuesByUserId(Long userId) {
        log.info("Get total Issues By User Id=" + userId);
        Integer totalIssues = scanRepository.findTotalIssuesByUserId(userId);
        if (totalIssues == null) {
            log.info("The user has not been found");
            throw new ResourceNotFoundException("User has not been found");
        }
        log.info("Total issues for userId " + userId + " = " + totalIssues);
        return totalIssues;
    }




    public List<Scan> getAllScans() {
        return scanRepository.findAll(); // This uses JpaRepository's built-in method
    }

    public int getTotalIssuesForAllUsers() {
        Integer totalIssues = scanRepository.findTotalIssuesForAllUsers();
        if (totalIssues == null) {
            totalIssues = 0;
        }
        log.info("Total issues for all users = " + totalIssues);
        return totalIssues;
    }





    public void processScan(Scan scan) {
        log.info("process scan type="+scan.getType());
        try {
            switch (scan.getType()) {
                case "SCA":
                    Thread.sleep(60000); // Simulating SCA scan
                    break;
                case "SAST":
                    Thread.sleep(90000); // Simulating SAST scan
                    break;
                case "RENOVATE":
                    Thread.sleep(30000); // Simulating RENOVATE scan
                    break;
                default:
                    throw new IllegalArgumentException("Unknown scan type: " + scan.getType());
            }
            scan.setStatus("Completed");
            scan.setIsScanned(true);
            scanRepository.save(scan);
        } catch (InterruptedException e) {
            log.error("Failed to process scan type="+scan.getType());
            scan.setStatus("Failed");
            scanRepository.save(scan);
        }
    }

}