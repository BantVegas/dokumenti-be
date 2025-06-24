package com.bantvegas.documentibackend.controller;

import com.bantvegas.documentibackend.dto.AnalysisRequest;
import com.bantvegas.documentibackend.model.AnalysisRecord;
import com.bantvegas.documentibackend.repository.AnalysisRepository;
import com.bantvegas.documentibackend.service.AiAnalysisService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/analysis")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class AnalysisController {

    private final AnalysisRepository repository;
    private final AiAnalysisService aiService;

    @PostMapping
    public ResponseEntity<AnalysisRecord> saveAnalysis(@RequestBody AnalysisRequest req) {
        AnalysisRecord record = new AnalysisRecord();
        record.setTyp(req.getTyp());
        record.setZhodnotenie(req.getZhodnotenie());
        record.setRizika(req.getRizika());
        record.setOdporucania(req.getOdporucania());
        record.setZaver(req.getZaver());
        record.setJazyk(req.getJazyk());
        record.setCreatedAt(LocalDateTime.now());
        return ResponseEntity.ok(repository.save(record));
    }

    @GetMapping
    public ResponseEntity<List<AnalysisRecord>> getAllAnalyses() {
        return ResponseEntity.ok(repository.findAll());
    }

    @PostMapping("/response")
    public ResponseEntity<Map<String, String>> generateResponse(@RequestBody AnalysisRequest req) {
        try {
            // Zoberme si jazyk z requestu, ak nie je nastavený, predpokladajme "sk"
            String lang = (req.getJazyk() != null && !req.getJazyk().isBlank())
                    ? req.getJazyk()
                    : "sk";

            String prompt = """
                Na základe nasledujúcej analýzy dokumentu navrhni profesionálne právne stanovisko v jazyku %s:
                Typ dokumentu: %s
                Právne zhodnotenie: %s
                Riziká: %s
                Odporúčania: %s
                Záver: %s
                """.formatted(
                    lang,
                    req.getTyp(),
                    req.getZhodnotenie(),
                    req.getRizika(),
                    req.getOdporucania(),
                    req.getZaver()
            );

            String aiResponse = aiService.ask(prompt);
            return ResponseEntity.ok(Map.of("odpoved", aiResponse));
        } catch (Exception e) {
            return ResponseEntity
                    .status(500)
                    .body(Map.of("error", "Chyba pri volaní AI: " + e.getMessage()));
        }
    }
}
