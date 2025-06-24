package com.bantvegas.documentibackend.dto;

import lombok.Data;

@Data
public class SplnomocnenieRequest {
    // Splnomocniteľ (ten, kto splnomocňuje)
    private String meno;
    private String priezvisko;
    private String datumNarodenia;
    private String rodneCislo;
    private String adresa;

    // Splnomocnenec (ten, kto je splnomocnený)
    private String splnomocnenecMeno;
    private String splnomocnenecDatumNarodenia;
    private String splnomocnenecAdresa;

    // Účel splnomocnenia
    private String ucel;
}
