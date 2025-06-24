package com.bantvegas.documentibackend.service;

import okhttp3.*;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class OpenAiService {

    @Value("${openai.api.key}")
    private String apiKey;

    private static final String OPENAI_API_URL = "https://api.openai.com/v1/chat/completions";

    public String ask(String prompt) {
        OkHttpClient client = new OkHttpClient();

        JSONObject message = new JSONObject()
                .put("role", "user")
                .put("content", prompt);

        JSONObject requestBody = new JSONObject()
                .put("model", "gpt-4")
                .put("messages", new org.json.JSONArray().put(message))
                .put("temperature", 0.7);

        Request request = new Request.Builder()
                .url(OPENAI_API_URL)
                .post(RequestBody.create(
                        requestBody.toString(),
                        MediaType.parse("application/json")
                ))
                .header("Authorization", "Bearer " + apiKey)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Chyba pri volaní OpenAI API: " + response);

            String responseBody = response.body().string();
            JSONObject json = new JSONObject(responseBody);
            return json
                    .getJSONArray("choices")
                    .getJSONObject(0)
                    .getJSONObject("message")
                    .getString("content")
                    .trim();
        } catch (Exception e) {
            throw new RuntimeException("OpenAI chyba: " + e.getMessage(), e);
        }
    }
}
