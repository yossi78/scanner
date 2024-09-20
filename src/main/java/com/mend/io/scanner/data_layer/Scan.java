package com.mend.io.scanner.data_layer;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "scans")
@Data
public class Scan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Auto-generate ID for each scan
    private Long id;

    private String type;     // SCA, SAST, RENOVATE
    private String status;   // Pending, Running, Completed
    private String repo;     // Repository name
    private String branch;   // Branch name
    private String commitId; // Commit ID
    private int issues;      // Number of issues found
    private boolean valid;   // Whether scan is valid (no issues)
    private Long userId;     // Reference to user


}
