package com.bantvegas.documentibackend.service;

import com.bantvegas.documentibackend.dto.ZivotopisRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;

@Service
public class AiCvService {
    private static final String OPENAI_URL = "https://api.openai.com/v1/chat/completions";
    private static final String OPENAI_API_KEY = "YOUR_API_KEY_HERE";
    public String[] generateCvAndMotivationLetter(ZivotopisRequest req) {
        String prompt = """
                Vytvor profesionálny životopis a motivačný list pre túto osobu:
                Meno: %s
                Priezvisko: %s
                Dátum narodenia: %s
                Adresa: %s
                Telefón: %s
                Email: %s
                Vzdelanie: %s
                Pracovné skúsenosti: %s
                Zručnosti a schopnosti: %s

                Najskôr vygeneruj sekciu "Životopis" (vo formáte textového životopisu pre recruiterov, nie tabuľkový výpis!).
                Potom sekciu "Motivačný list" (max 1 strana), v slovenčine.
                Výsledok oddeľaj tagom "### MOTIVACNY_LIST".
                """.formatted(
                req.getMeno(), req.getPriezvisko(), req.getDatumNarodenia(),
                req.getAdresa(), req.getTelefon(), req.getEmail(),
                req.getVzdelanie(), req.getSkusenosti(), req.getZrucnosti()
        );

        RestTemplate rest = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(OPENAI_API_KEY);

        String body = """
                {
                  "model": "gpt-4o",
                  "messages": [
                    {"role": "user", "content": "%s"}
                  ],
                  "max_tokens": 1200
                }
                """.formatted(prompt.replace("\"", "\\\""));

        HttpEntity<String> entity = new HttpEntity<>(body, headers);
        ResponseEntity<String> response = rest.postForEntity(OPENAI_URL, entity, String.class);

        String result = extractContentFromOpenAiResponse(response.getBody());
        // Rozdelí podľa tagu na dve časti
        String[] parts = result.split("### MOTIVACNY_LIST", 2);
        String zivotopis = parts[0].trim();
        String motivacnyList = parts.length > 1 ? parts[1].trim() : "";

        return new String[]{zivotopis, motivacnyList};
    }

    // Robustná extrakcia contentu z OpenAI odpovede
    private String extractContentFromOpenAiResponse(String json) {
        if (json == null) return "";
        try {
            // Nájde začiatok a koniec obsahu
            int idx = json.indexOf("\"content\":\"");
            if (idx < 0) return "";
            int start = idx + "\"content\":\"".length();
            int end = json.indexOf("\"", start);
            // Ošetrenie prípadného zalomenia
            StringBuilder sb = new StringBuilder();
            boolean escape = false;
            for (int i = start; i < json.length(); i++) {
                char c = json.charAt(i);
                if (c == '\\' && !escape) {
                    escape = true;
                } else {
                    if (escape) {
                        if (c == 'n') sb.append('\n');
                        else if (c == 't') sb.append('\t');
                        else sb.append(c);
                        escape = false;
                    } else if (c == '"') {
                        // Skonči, ak nie je escape sekvencia
                        break;
                    } else {
                        sb.append(c);
                    }
                }
            }
            return sb.toString();
        } catch (Exception e) {
            return "";
        }
    }
}
