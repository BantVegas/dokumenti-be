package com.bantvegas.documentibackend.model;

public class AiResult {
    private String summary;

    public AiResult() {}

    public AiResult(String summary) {
        this.summary = summary;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }
}

