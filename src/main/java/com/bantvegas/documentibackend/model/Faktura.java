package com.bantvegas.documentibackend.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import java.util.List;

@Entity
public class Faktura {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String cisloFaktury;
    private String variabilnySymbol;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate datumVydania;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate datumDodania;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate datumSplatnosti;

    private String stav;

    // Dodávateľ
    private String dodavatelMeno;
    private String dodavatelIco;
    private String dodavatelDic;
    private String dodavatelIcdph;
    private String dodavatelUlica;
    private String dodavatelMesto;
    private String dodavatelPsc;
    private String dodavatelStat;
    private String dodavatelEmail;
    private String dodavatelIban;
    private String dodavatelSwift;

    // Odberateľ
    private String odberatelMeno;
    private String odberatelIco;
    private String odberatelDic;
    private String odberatelIcdph;
    private String odberatelUlica;
    private String odberatelMesto;
    private String odberatelPsc;
    private String odberatelStat;

    // Platobné údaje
    private String formaUhrady;
    private String cisloUctu;
    private String poznamka;

    private Double suma; // voliteľné

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "faktura_id")
    private List<Polozka> polozky;

    // ==== GETTERY a SETTERY ====

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCisloFaktury() { return cisloFaktury; }
    public void setCisloFaktury(String cisloFaktury) { this.cisloFaktury = cisloFaktury; }

    public String getVariabilnySymbol() { return variabilnySymbol; }
    public void setVariabilnySymbol(String variabilnySymbol) { this.variabilnySymbol = variabilnySymbol; }

    public LocalDate getDatumVydania() { return datumVydania; }
    public void setDatumVydania(LocalDate datumVydania) { this.datumVydania = datumVydania; }

    public LocalDate getDatumDodania() { return datumDodania; }
    public void setDatumDodania(LocalDate datumDodania) { this.datumDodania = datumDodania; }

    public LocalDate getDatumSplatnosti() { return datumSplatnosti; }
    public void setDatumSplatnosti(LocalDate datumSplatnosti) { this.datumSplatnosti = datumSplatnosti; }

    public String getStav() { return stav; }
    public void setStav(String stav) { this.stav = stav; }

    public String getDodavatelMeno() { return dodavatelMeno; }
    public void setDodavatelMeno(String dodavatelMeno) { this.dodavatelMeno = dodavatelMeno; }

    public String getDodavatelIco() { return dodavatelIco; }
    public void setDodavatelIco(String dodavatelIco) { this.dodavatelIco = dodavatelIco; }

    public String getDodavatelDic() { return dodavatelDic; }
    public void setDodavatelDic(String dodavatelDic) { this.dodavatelDic = dodavatelDic; }

    public String getDodavatelIcdph() { return dodavatelIcdph; }
    public void setDodavatelIcdph(String dodavatelIcdph) { this.dodavatelIcdph = dodavatelIcdph; }

    public String getDodavatelUlica() { return dodavatelUlica; }
    public void setDodavatelUlica(String dodavatelUlica) { this.dodavatelUlica = dodavatelUlica; }

    public String getDodavatelMesto() { return dodavatelMesto; }
    public void setDodavatelMesto(String dodavatelMesto) { this.dodavatelMesto = dodavatelMesto; }

    public String getDodavatelPsc() { return dodavatelPsc; }
    public void setDodavatelPsc(String dodavatelPsc) { this.dodavatelPsc = dodavatelPsc; }

    public String getDodavatelStat() { return dodavatelStat; }
    public void setDodavatelStat(String dodavatelStat) { this.dodavatelStat = dodavatelStat; }

    public String getDodavatelEmail() { return dodavatelEmail; }
    public void setDodavatelEmail(String dodavatelEmail) { this.dodavatelEmail = dodavatelEmail; }

    public String getDodavatelIban() { return dodavatelIban; }
    public void setDodavatelIban(String dodavatelIban) { this.dodavatelIban = dodavatelIban; }

    public String getDodavatelSwift() { return dodavatelSwift; }
    public void setDodavatelSwift(String dodavatelSwift) { this.dodavatelSwift = dodavatelSwift; }

    public String getOdberatelMeno() { return odberatelMeno; }
    public void setOdberatelMeno(String odberatelMeno) { this.odberatelMeno = odberatelMeno; }

    public String getOdberatelIco() { return odberatelIco; }
    public void setOdberatelIco(String odberatelIco) { this.odberatelIco = odberatelIco; }

    public String getOdberatelDic() { return odberatelDic; }
    public void setOdberatelDic(String odberatelDic) { this.odberatelDic = odberatelDic; }

    public String getOdberatelIcdph() { return odberatelIcdph; }
    public void setOdberatelIcdph(String odberatelIcdph) { this.odberatelIcdph = odberatelIcdph; }

    public String getOdberatelUlica() { return odberatelUlica; }
    public void setOdberatelUlica(String odberatelUlica) { this.odberatelUlica = odberatelUlica; }

    public String getOdberatelMesto() { return odberatelMesto; }
    public void setOdberatelMesto(String odberatelMesto) { this.odberatelMesto = odberatelMesto; }

    public String getOdberatelPsc() { return odberatelPsc; }
    public void setOdberatelPsc(String odberatelPsc) { this.odberatelPsc = odberatelPsc; }

    public String getOdberatelStat() { return odberatelStat; }
    public void setOdberatelStat(String odberatelStat) { this.odberatelStat = odberatelStat; }

    public String getFormaUhrady() { return formaUhrady; }
    public void setFormaUhrady(String formaUhrady) { this.formaUhrady = formaUhrady; }

    public String getCisloUctu() { return cisloUctu; }
    public void setCisloUctu(String cisloUctu) { this.cisloUctu = cisloUctu; }

    public String getPoznamka() { return poznamka; }
    public void setPoznamka(String poznamka) { this.poznamka = poznamka; }

    public Double getSuma() { return suma; }
    public void setSuma(Double suma) { this.suma = suma; }

    public List<Polozka> getPolozky() { return polozky; }
    public void setPolozky(List<Polozka> polozky) { this.polozky = polozky; }
}
