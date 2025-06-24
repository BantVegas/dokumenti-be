// src/main/java/com/bantvegas/documentibackend/dto/DocumentGenerationRequest.java
package com.bantvegas.documentibackend.dto;

import java.util.Map;

public class DocumentGenerationRequest {
    private String type;
    private Map<String, String> data;

    public DocumentGenerationRequest() {}

    public DocumentGenerationRequest(String type, Map<String, String> data) {
        this.type = type;
        this.data = data;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Map<String, String> getData() {
        return data;
    }

    public void setData(Map<String, String> data) {
        this.data = data;
    }
}
