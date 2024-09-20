package com.mend.io.scanner.service;


import com.mend.io.scanner.data_layer.Scan;
import com.mend.io.scanner.data_layer.ScanRepository;
import com.mend.io.scanner.exception.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@Slf4j
public class ScanService {

    @Autowired
    private ScanRepository scanRepository;

    public Scan initiateScan(Scan scan) {
        log.debug("Initiate scan");
        scan.setStatus("Pending");
        //processScan(scan);
        return scanRepository.save(scan);
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

    public int getTotalIssuesForAllUsers(){
        List<Scan> allScans =scanRepository.findAll();
        int totalIssues = allScans.stream().mapToInt(Scan::getIssues).sum();
        log.info("Total issues for all users = " + totalIssues);
        return totalIssues;
    }



//    @Async
//    public void processScan(Scan scan) {
//        log.debug("process scan type="+scan.getType());
//        try {
//            switch (scan.getType()) {
//                case "SCA":
//                    Thread.sleep(60000); // Simulating SCA scan
//                    break;
//                case "SAST":
//                    Thread.sleep(90000); // Simulating SAST scan
//                    break;
//                case "RENOVATE":
//                    Thread.sleep(30000); // Simulating RENOVATE scan
//                    break;
//                default:
//                    throw new IllegalArgumentException("Unknown scan type: " + scan.getType());
//            }
//            scan.setStatus("Completed");
//            scanRepository.save(scan);
//        } catch (InterruptedException e) {
//            log.error("Failed to process scan type="+scan.getType());
//            scan.setStatus("Failed");
//            scanRepository.save(scan);
//        }
//    }

}