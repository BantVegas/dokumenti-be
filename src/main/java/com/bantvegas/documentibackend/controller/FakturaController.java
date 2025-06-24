package com.bantvegas.documentibackend.controller;

import com.bantvegas.documentibackend.model.Faktura;
import com.bantvegas.documentibackend.service.FakturaService;
import com.bantvegas.documentibackend.service.FakturaPdfService;
import com.bantvegas.documentibackend.service.FakturaEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/faktury")
@CrossOrigin(origins = "*") // alebo zadaj konkrétny origin ak treba
public class FakturaController {

    @Autowired
    private FakturaService fakturaService;

    @Autowired
    private FakturaPdfService fakturaPdfService;

    @Autowired
    private FakturaEmailService fakturaEmailService;

    // 1. Vytvorenie faktúry a uloženie (JSON odpoveď)
    @PostMapping
    public ResponseEntity<Faktura> vytvorFakturu(@RequestBody Faktura faktura) {
        Faktura ulozena = fakturaService.save(faktura);
        return ResponseEntity.ok(ulozena);
    }

    // 2. Vytvorenie a okamžitý export PDF (bez uloženia do DB)
    @PostMapping("/pdf")
    public ResponseEntity<byte[]> generatePdf(@RequestBody Faktura faktura) {
        byte[] pdf = fakturaPdfService.generatePdf(faktura);
        String filename = "faktura-" + (faktura.getCisloFaktury() != null ? faktura.getCisloFaktury() : "dokument") + ".pdf";
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, "application/pdf")
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                .body(pdf);
    }

    // 3. Zoznam všetkých faktúr
    @GetMapping
    public List<Faktura> zoznamFaktur() {
        return fakturaService.getAll();
    }

    // 4. Detail faktúry podľa ID
    @GetMapping("/{id}")
    public ResponseEntity<Faktura> detailFaktury(@PathVariable Long id) {
        return fakturaService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 5. Vymazanie faktúry podľa ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> zmazFakturu(@PathVariable Long id) {
        fakturaService.delete(id);
        return ResponseEntity.ok().build();
    }

    // 6. Export faktúry do PDF podľa ID (stiahnutie PDF)
    @GetMapping("/{id}/pdf")
    public ResponseEntity<byte[]> stiahniPdf(@PathVariable Long id) {
        return fakturaService.getById(id)
                .map(faktura -> {
                    byte[] pdf = fakturaPdfService.generatePdf(faktura);
                    return ResponseEntity.ok()
                            .header(HttpHeaders.CONTENT_TYPE, "application/pdf")
                            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=faktura-" + faktura.getCisloFaktury() + ".pdf")
                            .body(pdf);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // 7. Odoslanie faktúry e-mailom s PDF prílohou
    @PostMapping("/{id}/send")
    public ResponseEntity<?> posliFakturu(@PathVariable Long id, @RequestParam String email) {
        return fakturaService.getById(id)
                .map(faktura -> {
                    try {
                        fakturaEmailService.posliFakturuEmailom(faktura, email);
                        return ResponseEntity.ok("Faktúra bola odoslaná na " + email);
                    } catch (Exception e) {
                        return ResponseEntity.status(500).body("Chyba pri odosielaní: " + e.getMessage());
                    }
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // 8. Vyhľadanie firmy podľa IČO (DEMO endpoint, uprav podľa svojho zdroja)
    @GetMapping("/ico/{ico}")
    public ResponseEntity<?> getFirmaByIco(@PathVariable String ico) {
        // Tu môžeš napojiť volanie na Finstat, open API alebo vlastný dataset.
        // Toto je iba DEMO odpoveď pre testovanie FE:
        if ("37380982".equals(ico)) {
            return ResponseEntity.ok(Map.of(
                    "meno", "Martin Lukáč",
                    "ico", "37380982",
                    "dic", "1043839918",
                    "icdph", "",
                    "ulica", "Doležalova 3424/15C",
                    "mesto", "Bratislava - mestská časť Ružinov",
                    "psc", "82104",
                    "krajina", "Slovensko",
                    "email", "martin.lukac@email.sk",
                    "telefon", "+421 950 889 523"
            ));
        }
        return ResponseEntity.status(404).body(Map.of("error", "Firma nebola nájdená."));
    }
}
