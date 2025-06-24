package com.bantvegas.documentibackend.service;

import com.bantvegas.documentibackend.model.AnalysisResult;
import com.bantvegas.documentibackend.util.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class DocumentAnalyzerService {

    private final AiAnalysisService aiAnalysisService;

    public DocumentAnalyzerService(AiAnalysisService aiAnalysisService) {
        this.aiAnalysisService = aiAnalysisService;
    }

    public AnalysisResult analyze(MultipartFile file) throws Exception {
        String text = FileUtils.extractText(file);

        // 💬 Upravený prompt – žiadne markdowny, výstup = čistý JSON
        String prompt = """
            Analyzuj nasledujúci právny dokument a vráť iba čistý JSON objekt (bez komentárov alebo formátovania),
            ktorý obsahuje kľúče: typeOfDocument, legalAssessment, risks, recommendations, conclusion.

            Výstup musí byť validný JSON, začínajúci znakom `{` a končiaci znakom `}`. Nepoužívaj markdown bloky ako ```json.

            Dokument:
            """ + text;

        String response = aiAnalysisService.ask(prompt);

        return aiAnalysisService.parseJson(response, AnalysisResult.class);
    }
}

