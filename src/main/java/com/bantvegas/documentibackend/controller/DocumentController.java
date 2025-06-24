package com.bantvegas.documentibackend.controller;

import com.bantvegas.documentibackend.dto.PdfRequest;
import com.bantvegas.documentibackend.model.AnalysisResult;
import com.bantvegas.documentibackend.service.DocumentService;
import com.bantvegas.documentibackend.service.PdfService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class DocumentController {

    private final DocumentService documentService;
    private final PdfService pdfService;

    public DocumentController(DocumentService documentService, PdfService pdfService) {
        this.documentService = documentService;
        this.pdfService = pdfService;
    }

    @PostMapping("/upload")
    public ResponseEntity<AnalysisResult> handleUpload(@RequestParam("file") MultipartFile file) {
        try {
            AnalysisResult result = documentService.processFile(file);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            AnalysisResult error = new AnalysisResult();
            error.setConclusion("Chyba počas analýzy: " + e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @PostMapping("/pdf")
    public ResponseEntity<InputStreamResource> generatePdf(@RequestBody PdfRequest request) {
        try {
            byte[] pdfData = pdfService.createPdf(request);
            ByteArrayInputStream bais = new ByteArrayInputStream(pdfData);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=analyza.pdf")
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(new InputStreamResource(bais));
        } catch (Exception e) {
            byte[] errorBytes = ("Chyba: " + e.getMessage()).getBytes(StandardCharsets.UTF_8);
            return ResponseEntity.internalServerError().body(new InputStreamResource(new ByteArrayInputStream(errorBytes)));
        }
    }
}
