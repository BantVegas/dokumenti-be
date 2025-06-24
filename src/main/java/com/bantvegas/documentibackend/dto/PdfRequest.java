package com.bantvegas.documentibackend.dto;

public class PdfRequest {
    private String type;
    private String legalEvaluation;
    private String risk;
    private String recommendation;
    private String conclusion;
    private String language;

    // Getre a setre
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getLegalEvaluation() { return legalEvaluation; }
    public void setLegalEvaluation(String legalEvaluation) { this.legalEvaluation = legalEvaluation; }

    public String getRisk() { return risk; }
    public void setRisk(String risk) { this.risk = risk; }

    public String getRecommendation() { return recommendation; }
    public void setRecommendation(String recommendation) { this.recommendation = recommendation; }

    public String getConclusion() { return conclusion; }
    public void setConclusion(String conclusion) { this.conclusion = conclusion; }

    public String getLanguage() { return language; }
    public void setLanguage(String language) { this.language = language; }
}
