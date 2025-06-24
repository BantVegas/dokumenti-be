package com.bantvegas.documentibackend.dto;

import lombok.Data;

@Data
public class NajomnaZmluvaRequest {
    private String prenajimatelMeno;
    private String prenajimatelAdresa;
    private String najomcaMeno;
    private String najomcaAdresa;
    private String predmetNajmu;
    private String adresaPredmetu;
    private String datumOd;
    private String datumDo;
    private String vyskaNajomneho;
}
