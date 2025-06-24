package com.bantvegas.documentibackend.dto;

import lombok.Data;

@Data
public class VypovedRequest {
    private String meno;
    private String priezvisko;
    private String adresa;
    private String zamestnavatel;
    private String datumNastupu;
    private String pozicia;
    private String dovodVypovede;
    private String datumVypovede;
}
