package com.bantvegas.documentibackend.dto;

public class PracovnaZmluvaRequest {
    private String meno;
    private String priezvisko;
    private String adresa;
    private String rodneCislo;
    private String zamestnavatel;
    private String miestoVykonuPrace;
    private String pracovnyCas;
    private String datumNastupu;
    private String pozicia;
    private String mzda;

    // GETTERY
    public String getMeno() { return meno; }
    public String getPriezvisko() { return priezvisko; }
    public String getAdresa() { return adresa; }
    public String getRodneCislo() { return rodneCislo; }
    public String getZamestnavatel() { return zamestnavatel; }
    public String getMiestoVykonuPrace() { return miestoVykonuPrace; }
    public String getPracovnyCas() { return pracovnyCas; }
    public String getDatumNastupu() { return datumNastupu; }
    public String getPozicia() { return pozicia; }
    public String getMzda() { return mzda; }

    // SETTERY (na deserializáciu z JSON)
    public void setMeno(String meno) { this.meno = meno; }
    public void setPriezvisko(String priezvisko) { this.priezvisko = priezvisko; }
    public void setAdresa(String adresa) { this.adresa = adresa; }
    public void setRodneCislo(String rodneCislo) { this.rodneCislo = rodneCislo; }
    public void setZamestnavatel(String zamestnavatel) { this.zamestnavatel = zamestnavatel; }
    public void setMiestoVykonuPrace(String miestoVykonuPrace) { this.miestoVykonuPrace = miestoVykonuPrace; }
    public void setPracovnyCas(String pracovnyCas) { this.pracovnyCas = pracovnyCas; }
    public void setDatumNastupu(String datumNastupu) { this.datumNastupu = datumNastupu; }
    public void setPozicia(String pozicia) { this.pozicia = pozicia; }
    public void setMzda(String mzda) { this.mzda = mzda; }
}
