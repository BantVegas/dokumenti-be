package com.bantvegas.documentibackend.service;

import com.bantvegas.documentibackend.model.Faktura;
import com.bantvegas.documentibackend.repository.FakturaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FakturaService {

    @Autowired
    private FakturaRepository fakturaRepository;

    public Faktura save(Faktura faktura) {
        // Auto-celkom na položky
        if (faktura.getPolozky() != null) {
            faktura.getPolozky().forEach(p -> p.setCelkom(p.getPocet() * p.getCena()));
        }
        // Číslovanie faktúr
        if (faktura.getCisloFaktury() == null || faktura.getCisloFaktury().isEmpty()) {
            faktura.setCisloFaktury(generateCisloFaktury());
        }
        return fakturaRepository.save(faktura);
    }

    public List<Faktura> getAll() {
        return fakturaRepository.findAll();
    }

    public Optional<Faktura> getById(Long id) {
        return fakturaRepository.findById(id);
    }

    public void delete(Long id) {
        fakturaRepository.deleteById(id);
    }

    public String generateCisloFaktury() {
        Faktura posledna = fakturaRepository.findTopByOrderByIdDesc();
        int poradoveCislo = 1;
        if (posledna != null) {
            try {
                poradoveCislo = Integer.parseInt(posledna.getCisloFaktury().replaceAll("\\D", "")) + 1;
            } catch (Exception e) {
                poradoveCislo = 1;
            }
        }
        return "2025" + String.format("%03d", poradoveCislo); // 2025001, 2025002...
    }
}

