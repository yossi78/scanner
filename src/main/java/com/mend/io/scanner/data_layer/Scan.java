package com.mend.io.scanner.data_layer;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
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

    private Boolean isScanned=false;
    @JsonIgnore
    @Transient
    private String filePath=null;



}
