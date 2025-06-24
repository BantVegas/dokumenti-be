package com.bantvegas.documentibackend.repository;

import com.bantvegas.documentibackend.model.Faktura;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FakturaRepository extends JpaRepository<Faktura, Long> {
    Faktura findTopByOrderByIdDesc(); // Na generovanie čísla faktúry
}

