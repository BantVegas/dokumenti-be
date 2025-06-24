package com.bantvegas.documentibackend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class AnalysisRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String typ;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String zhodnotenie;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String rizika;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String odporucania;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String zaver;

    private String jazyk;

    private LocalDateTime createdAt;
}
