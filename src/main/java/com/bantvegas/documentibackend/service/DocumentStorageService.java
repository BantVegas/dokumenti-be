package com.bantvegas.documentibackend.service;

import com.bantvegas.documentibackend.model.AnalysisRecord;
import com.bantvegas.documentibackend.repository.AnalysisRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DocumentStorageService {

    private final AnalysisRepository repository;

    public DocumentStorageService(AnalysisRepository repository) {
        this.repository = repository;
    }

    public void saveAnalysis(AnalysisRecord record) {
        repository.save(record);
    }

    public List<AnalysisRecord> getAllAnalyses() {
        return repository.findAll();
    }
}
