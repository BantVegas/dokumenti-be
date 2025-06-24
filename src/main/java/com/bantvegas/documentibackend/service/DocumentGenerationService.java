package com.bantvegas.documentibackend.service;

import org.springframework.stereotype.Service;
import java.util.Map;

@Service
public class DocumentGenerationService {

    public String generateText(String type, Map<String, String> data) {
        switch (type) {
            case "splnomocnenie":
                return generateSplnomocnenie(data);
            // ... ďalšie typy
            default:
                return "Neznámy typ dokumentu";
        }
    }

    private String generateSplnomocnenie(Map<String, String> data) {
        String splnomocnitel = String.format("%s %s",
                data.getOrDefault("splnomocnitelMeno", ""),
                data.getOrDefault("splnomocnitelPriezvisko", ""));
        String splnomocnitelDatumNarodenia = data.getOrDefault("splnomocnitelDatumNarodenia", "");
        String splnomocnitelRodneCislo = data.getOrDefault("splnomocnitelRodneCislo", "");
        String splnomocnitelAdresa = data.getOrDefault("splnomocnitelAdresa", "");
        String splnomocnenec = String.format("%s", data.getOrDefault("splnomocnenecMeno", ""));
        String splnomocnenecDatumNarodenia = data.getOrDefault("splnomocnenecDatumNarodenia", "");
        String splnomocnenecAdresa = data.getOrDefault("splnomocnenecAdresa", "");
        String ucel = data.getOrDefault("ucel", "");

        StringBuilder sb = new StringBuilder();
        sb.append("\n                SPLNOMOCNENIE\n\n");
        sb.append("Ja, ").append(splnomocnitel)
                .append(", narodený/á: ").append(splnomocnitelDatumNarodenia)
                .append(", rodné číslo: ").append(splnomocnitelRodneCislo)
                .append(", bytom: ").append(splnomocnitelAdresa).append(",\n\n");
        sb.append("týmto splnomocňujem: ").append(splnomocnenec)
                .append(", narodený/á: ").append(splnomocnenecDatumNarodenia)
                .append(", bytom: ").append(splnomocnenecAdresa).append("\n\n");
        sb.append("na vykonanie nasledujúceho úkonu/účelu:\n");
        sb.append(ucel).append("\n\n");
        sb.append("V _______________________ dňa ________________\n\n");
        sb.append("Podpis splnomocniteľa: ____________________________\n");
        return sb.toString();
    }
}
