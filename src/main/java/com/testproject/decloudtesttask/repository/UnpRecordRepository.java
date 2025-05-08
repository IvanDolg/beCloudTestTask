package com.testproject.decloudtesttask.repository;

import com.testproject.decloudtesttask.model.UnpRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UnpRecordRepository extends JpaRepository<UnpRecord, Long> {
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO unp_records (unp) VALUES (:unp) ON CONFLICT (unp) DO NOTHING", nativeQuery = true)
    void insertIgnoreConflict(@Param("unp") String unp);
}
