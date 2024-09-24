package com.mend.io.scanner.api;

import com.mend.io.scanner.data_layer.Scan;
import com.mend.io.scanner.exception.ResourceNotFoundException;
import com.mend.io.scanner.service.ScanService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/scans")
@Slf4j
public class ScanController {

    @Autowired
    private ScanService scanService;

    @PostMapping("/initiate")
    public ResponseEntity<Scan> initiateScan(@RequestBody Scan scan) {
        try {
            log.info("Initiating scan for repo: {}", scan.getRepo());
            Scan savedScan = scanService.initiateScan(scan,true);
            log.info("Scan initiated with ID: {}", savedScan.getId());
            return ResponseEntity.ok(savedScan);
        } catch (Exception e) {
            log.error("Failed to Initiating scan ");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Scan>> getScansByUser(@PathVariable Long userId) {
        try {
            List<Scan> scans = scanService.getScansByUserID(userId);
            return new ResponseEntity<>(scans, HttpStatus.OK);
        }catch (ResourceNotFoundException e) {
            log.debug("User has not been found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            log.error("Failed to get user from DB ");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/pending")
    public ResponseEntity<Integer> countPendingScans() {
        try {
            long pendingCount = scanService.countPendingScans();
            return ResponseEntity.ok((int) pendingCount); // Casting long to int as ResponseEntity<Integer> is expected
        } catch (Exception e) {
            log.error("Error occurred while counting pending scans", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null); // or handle with a custom error message
        }
    }



}
