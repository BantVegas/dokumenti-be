package com.bantvegas.documentibackend.dto;

import lombok.Data;

@Data
public class AnalysisRequest {
    private String typ;
    private String zhodnotenie;
    private String rizika;
    private String odporucania;
    private String zaver;
    private String jazyk;
}
