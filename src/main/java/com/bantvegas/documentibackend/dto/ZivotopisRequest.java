package com.bantvegas.documentibackend.dto;

import lombok.Data;

@Data
public class ZivotopisRequest {
    private String meno;
    private String priezvisko;
    private String datumNarodenia;
    private String adresa;
    private String telefon;
    private String email;
    private String vzdelanie;
    private String skusenosti;
    private String zrucnosti;
    private String jazyk; // napr. "sk" alebo "en"
}
