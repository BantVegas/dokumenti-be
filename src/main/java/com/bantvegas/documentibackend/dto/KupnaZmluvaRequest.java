package com.bantvegas.documentibackend.dto;

import lombok.Data;

@Data
public class KupnaZmluvaRequest {
    private String predavajuciMeno;
    private String predavajuciAdresa;
    private String kupujuciMeno;
    private String kupujuciAdresa;
    private String predmetZmluvy;
    private String cena;
    private String miestoOdovzdania;
    private String datum;
}

