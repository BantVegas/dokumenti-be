package com.bantvegas.documentibackend.model;

public class AnalysisResult {

    private String typeOfDocument;
    private String legalAssessment;
    private String risks;
    private String recommendations;
    private String conclusion;
    private String language;

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

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}

