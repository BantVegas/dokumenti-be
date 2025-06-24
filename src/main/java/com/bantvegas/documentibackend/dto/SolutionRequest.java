package com.bantvegas.documentibackend.dto;

public class SolutionRequest {
    private String typeOfDocument;
    private String legalAssessment;
    private String risks;
    private String recommendations;
    private String conclusion;

    public SolutionRequest() {
        // defaultný konštruktor
    }

    // ✅ tento konštruktor pridaj:
    public SolutionRequest(String typeOfDocument, String legalAssessment, String risks, String recommendations, String conclusion) {
        this.typeOfDocument = typeOfDocument;
        this.legalAssessment = legalAssessment;
        this.risks = risks;
        this.recommendations = recommendations;
        this.conclusion = conclusion;
    }

    // 🧾 Gettery a settery

    public String getTypeOfDocument() {
        return typeOfDocument;
    }

    public void setTypeOfDocument(String typeOfDocument) {
        this.typeOfDocument = typeOfDocument;
    }

    public String getLegalAssessment() {
        return legalAssessment;
    }

    public void setLegalAssessment(String legalAssessment) {
        this.legalAssessment = legalAssessment;
    }

    public String getRisks() {
        return risks;
    }

    public void setRisks(String risks) {
        this.risks = risks;
    }

    public String getRecommendations() {
        return recommendations;
    }

    public void setRecommendations(String recommendations) {
        this.recommendations = recommendations;
    }

    public String getConclusion() {
        return conclusion;
    }

    public void setConclusion(String conclusion) {
        this.conclusion = conclusion;
    }
}
