package com.mend.io.scanner.data_layer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ScanRepository extends JpaRepository<Scan, Long> {

    @Query("SELECT SUM(s.issues) FROM Scan s")
    Integer findTotalIssuesForAllUsers();

    @Query("SELECT SUM(s.issues) FROM Scan s WHERE s.userId = :userId")
    Integer findTotalIssuesByUserId(@Param("userId") Long userId);

    public List<Scan> findByUserId(Long userId);
    public long countByStatus(String status);


}
