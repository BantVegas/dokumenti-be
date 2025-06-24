package com.bantvegas.documentibackend.controller;

import com.bantvegas.documentibackend.dto.*;
import com.bantvegas.documentibackend.service.AiCvService;
import com.bantvegas.documentibackend.service.PdfService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/generate")
@RequiredArgsConstructor
public class DocumentGenerationController {

    private final AiCvService aiCvService;
    private final PdfService pdfService;

    // 1. ŽIVOTOPIS (AI generovaný + motivačný list)
    @PostMapping("/zivotopis")
    public ResponseEntity<byte[]> generateZivotopis(@RequestBody ZivotopisRequest request) {
        String[] aiOutput = aiCvService.generateCvAndMotivationLetter(request);
        String zivotopisText = aiOutput[0];
        String motivationText = aiOutput[1];
        byte[] pdf = pdfService.generateZivotopisFullPdf(zivotopisText, motivationText, request);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "zivotopis.pdf");
        return new ResponseEntity<>(pdf, headers, HttpStatus.OK);
    }

    // 2. SPLNOMOCNENIE
    @PostMapping("/splnomocnenie")
    public ResponseEntity<byte[]> generateSplnomocnenie(@RequestBody SplnomocnenieRequest request) {
        byte[] pdf = pdfService.generateSplnomocnenie(request);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "splnomocnenie.pdf");
        return new ResponseEntity<>(pdf, headers, HttpStatus.OK);
    }

    // 3. NAJOMNA ZMLUVA
    @PostMapping("/najomna-zmluva")
    public ResponseEntity<byte[]> generateNajomnaZmluva(@RequestBody NajomnaZmluvaRequest request) {
        byte[] pdf = pdfService.generateNajomnaZmluva(request);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "najomna_zmluva.pdf");
        return new ResponseEntity<>(pdf, headers, HttpStatus.OK);
    }

    // 4. VYPOVED Z PRACE
    @PostMapping("/vypoved")
    public ResponseEntity<byte[]> generateVypoved(@RequestBody VypovedRequest request) {
        byte[] pdf = pdfService.generateVypovedZPrace(request);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "vypoved_z_prace.pdf");
        return new ResponseEntity<>(pdf, headers, HttpStatus.OK);
    }

    // 5. KUPNA ZMLUVA
    @PostMapping("/kupna-zmluva")
    public ResponseEntity<byte[]> generateKupnaZmluva(@RequestBody KupnaZmluvaRequest request) {
        byte[] pdf = pdfService.generateKupnaZmluva(request);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "kupna_zmluva.pdf");
        return new ResponseEntity<>(pdf, headers, HttpStatus.OK);
    }

    // 6. PRACOVNA ZMLUVA
    @PostMapping("/pracovna-zmluva")
    public ResponseEntity<byte[]> generatePracovnaZmluva(@RequestBody PracovnaZmluvaRequest request) {
        byte[] pdf = pdfService.generatePracovnaZmluva(request);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "pracovna_zmluva.pdf");
        return new ResponseEntity<>(pdf, headers, HttpStatus.OK);
    }

    // 7. GENERICKY PDF – právna analýza (ak používaš)
    @PostMapping("/pdf")
    public ResponseEntity<byte[]> generatePdf(@RequestBody PdfRequest request) {
        byte[] pdf = pdfService.createPdf(request);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "analyza.pdf");
        return new ResponseEntity<>(pdf, headers, HttpStatus.OK);
    }
}

