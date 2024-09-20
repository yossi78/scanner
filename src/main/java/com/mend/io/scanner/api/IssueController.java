package com.mend.io.scanner.api;



import com.mend.io.scanner.exception.ResourceNotFoundException;
import com.mend.io.scanner.service.ScanService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/api/issue")
@Slf4j
public class IssueController {


    private ScanService scanService;

    @Autowired
    public IssueController(ScanService scanService) {
        this.scanService = scanService;
    }


    @GetMapping
    public ResponseEntity<Integer> getTotalIssuesByUserId(@RequestParam Long userId) {
        try {
            int totalIssues = scanService.getTotalIssuesByUserId(userId);
            return ResponseEntity.ok(totalIssues);
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            log.error("An error occurred while fetching total issues for userId " + userId, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/total")
    public ResponseEntity<Integer> getTotalIssuesForAllUsers() {
        try {
            int totalIssues = scanService.getTotalIssuesForAllUsers();
            return ResponseEntity.ok(totalIssues);
        } catch (Exception e) {
            log.error("An error occurred while fetching total issues for all users", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
