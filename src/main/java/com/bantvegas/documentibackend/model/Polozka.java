package com.bantvegas.documentibackend.model;

import jakarta.persistence.*;

@Entity
public class Polozka {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String popis;
    private int pocet;
    private String mj; // merná jednotka (napr. ks, hod.)
    private double cena;
    private double celkom;

    // --- GETTERY a SETTERY ---

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getPopis() {
        return popis;
    }
    public void setPopis(String popis) {
        this.popis = popis;
    }

    public int getPocet() {
        return pocet;
    }
    public void setPocet(int pocet) {
        this.pocet = pocet;
    }

    public String getMj() {
        return mj;
    }
    public void setMj(String mj) {
        this.mj = mj;
    }

    public double getCena() {
        return cena;
    }
    public void setCena(double cena) {
        this.cena = cena;
    }

    public double getCelkom() {
        return celkom;
    }
    public void setCelkom(double celkom) {
        this.celkom = celkom;
    }
}
