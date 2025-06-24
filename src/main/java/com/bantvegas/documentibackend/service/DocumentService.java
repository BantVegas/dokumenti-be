package com.bantvegas.documentibackend.service;

import com.bantvegas.documentibackend.model.AnalysisResult;
import com.bantvegas.documentibackend.util.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class DocumentService {

    private final AiAnalysisService aiService;

    public DocumentService(AiAnalysisService aiService) {
        this.aiService = aiService;
    }

    public AnalysisResult processFile(MultipartFile file) {
        try {
            String text = FileUtils.extractText(file);

            String prompt = """
Analyzuj nasledujúci právny dokument a vráť štruktúrovanú odpoveď v JSON formáte so špecifikovanými políčkami. 
Zameraj sa na výklad, právne dôsledky a odporúčania.

Polia vo výstupe musia byť nasledovné:
{
  "typeOfDocument": "...",
  "legalAssessment": "...",
  "risks": "...",
  "recommendations": "...",
  "conclusion": "...",
  "language": "sk"
}

Odpovedz výhradne čistým platným JSON objektom, bez komentára alebo úvodu.

Dokument na analýzu:
""" + text;

            // Pošli požiadavku do AI
            String aiRawResponse = aiService.ask(prompt);

            // Log pre istotu
            System.out.println("=== AI RAW STRING ===");
            System.out.println(aiRawResponse);

            // AI odpoveď je string obsahujúci JSON → treba druhý parsing
            AnalysisResult parsed = aiService.parseJson(aiRawResponse.trim(), AnalysisResult.class);
            if (parsed == null) {
                throw new RuntimeException("Nepodarilo sa rozparsovať odpoveď AI.");
            }

            return parsed;

        } catch (Exception e) {
            e.printStackTrace();
            AnalysisResult fallback = new AnalysisResult();
            fallback.setConclusion("❌ Chyba počas AI analýzy: " + e.getMessage());
            return fallback;
        }
    }
}
