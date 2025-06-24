package com.bantvegas.documentibackend.service;

import com.bantvegas.documentibackend.dto.SolutionRequest;
import org.springframework.stereotype.Service;

@Service
public class SolutionService {

    public String generateSolution(SolutionRequest request) {
        return """
            💡 Návrh riešenia:
            Na základe analýzy dokumentu by som odporučil nasledujúce právne kroky:

            1. Skontrolujte všetky údaje – %s
            2. Zvážte riziká – %s
            3. Postupujte podľa odporúčaní – %s
            4. Záver – %s
            """.formatted(
                request.getLegalAssessment(),
                request.getRisks(),
                request.getRecommendations(),
                request.getConclusion()
        );
    }
}

