package com.bantvegas.documentibackend.repository;

import com.bantvegas.documentibackend.model.AnalysisRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnalysisRepository extends JpaRepository<AnalysisRecord, Long> {
}
