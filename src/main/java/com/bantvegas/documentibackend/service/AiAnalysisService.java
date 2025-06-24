package com.bantvegas.documentibackend.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import okhttp3.*;
import org.springframework.stereotype.Service;

import java.net.SocketTimeoutException;
import java.util.concurrent.TimeUnit;

@Service
public class AiAnalysisService {

    private static final String OPENAI_API_URL = "https://api.openai.com/v1/chat/completions";
    private static final String OPENAI_API_KEY = System.getenv("OPENAI_API_KEY");

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final OkHttpClient client;

    public AiAnalysisService() {
        this.client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
    }

    public String ask(String prompt) throws Exception {
        if (OPENAI_API_KEY == null) {
            throw new IllegalStateException("❌ OPENAI_API_KEY nie je nastavený.");
        }

        // Vytvorenie JSON payload pomocou Jackson
        ObjectNode payload = objectMapper.createObjectNode();
        payload.put("model", "gpt-4");

        ArrayNode messages = objectMapper.createArrayNode();
        messages.add(objectMapper.createObjectNode()
                .put("role", "system")
                .put("content", "Si právny asistent. Odpovedaj iba čistým JSON-om bez formátovania."));
        messages.add(objectMapper.createObjectNode()
                .put("role", "user")
                .put("content", prompt));

        payload.set("messages", messages);
        payload.put("temperature", 0.3);

        String requestBody = objectMapper.writeValueAsString(payload);

        Request request = new Request.Builder()
                .url(OPENAI_API_URL)
                .header("Authorization", "Bearer " + OPENAI_API_KEY)
                .header("Content-Type", "application/json")
                .post(RequestBody.create(requestBody, MediaType.parse("application/json")))
                .build();

        try (Response response = client.newCall(request).execute()) {
            String body = response.body().string();

            if (!response.isSuccessful()) {
                throw new RuntimeException(
                        "❌ AI odpoveď zlyhala: " + response +
                                "\nTelo odpovede: " + body
                );
            }

            // Vyriešenie prípadných obalení úvodzovkami
            if (body.startsWith("\"") && body.endsWith("\"")) {
                body = body.substring(1, body.length() - 1);
            }

            JsonNode root = objectMapper.readTree(body);
            return root.get("choices")
                    .get(0)
                    .get("message")
                    .get("content")
                    .asText();
        } catch (SocketTimeoutException e) {
            throw new RuntimeException("❌ Časový limit pri požiadavke na OpenAI", e);
        }
    }

    public <T> T parseJson(String rawJson, Class<T> clazz) {
        // 1) odstráni BOM ak existuje
        String cleaned = rawJson.replace("\uFEFF", "");
        // 2) odstráni spätné úvodzovky
        cleaned = cleaned.replace("```json", "").replace("```", "").trim();

        try {
            return objectMapper.readValue(cleaned, clazz);
        } catch (Exception e) {
            // vypíše do štandardnej chyby celý JSON, ktorý sa nepodarilo parsovať
            System.err.println("Nepodarilo sa parsovať nasledovný JSON:\n" + cleaned);
            throw new RuntimeException("❌ Chyba pri parsovaní JSON: " + e.getMessage(), e);
        }
    }
}

