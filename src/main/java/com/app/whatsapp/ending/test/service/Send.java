/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.whatsapp.ending.test.service;

import com.app.whatsapp.ending.test.dto.OkWhatsappResponseDto;
import com.app.whatsapp.ending.test.dto.Welcome;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @author aresis
 */
@Service
public class Send {

        ObjectMapper objectMapper = new ObjectMapper();

        OkHttpClient client = new OkHttpClient().newBuilder()
                        .build();

        String token = "EAADz6AF76ZA4BO4gILJIdlEeMuM3LhkOGpHMleBLwqC3h7O7PJkG5VyZAseLDF3b6RcBCxzyRzVvXOzMcqX5cvdUnhUGIbfajYoKR4mdKWqPK8ozWZALWeVEqCUZAFpnDteZCih6IPyXgaZBzZCSHNm5FGXw2DCluQRLIicqJvOXQ1oEsoVWMVZBCE7l6ZAsuRHT7Ylp72hTWEK7ejX4TBVFx";
        String facebookUrl = "https://graph.facebook.com/v17.0/108928785480520/messages";

        public OkWhatsappResponseDto templateToClient(String phone) throws IOException {
                objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
                objectMapper.setVisibility(VisibilityChecker.Std.defaultInstance()
                                .withFieldVisibility(JsonAutoDetect.Visibility.ANY));
                MediaType mediaType = MediaType.parse("application/json");
                RequestBody body = RequestBody.create(mediaType,
                                "{\n    \"messaging_product\": \"whatsapp\",\n    \"to\": \"" + phone
                                                + "\",\n    \"type\": \"template\",\n    \"template\": {\n        \"name\": \"hello_world\",\n        \"language\": {\n            \"code\": \"en_US\"\n        }\n    }\n}");

                Request request = new Request.Builder()
                                .url(facebookUrl)
                                .method("POST", body)
                                .addHeader("Content-Type", "application/json")
                                .addHeader("Authorization", "Bearer " + token)
                                .build();
                String response = client.newCall(request).execute().body().string();
                OkWhatsappResponseDto result = objectMapper.readValue(response, OkWhatsappResponseDto.class);
                return result;

        }

        public OkWhatsappResponseDto messageToClient(String phone, String message) throws IOException {
                objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
                objectMapper.setVisibility(VisibilityChecker.Std.defaultInstance()
                                .withFieldVisibility(JsonAutoDetect.Visibility.ANY));
                OkHttpClient client = new OkHttpClient().newBuilder()
                                .build();
                MediaType mediaType = MediaType.parse("application/json");
                // RequestBody body = RequestBody.create(mediaType, "{\n \"messaging_product\":
                // \"whatsapp\", \n \"recipient_type\": \"individual\",\n \"to\": \"" + phone +
                // "\",\n \"type\": \"text\",\n \"text\": {\n \"preview_url\": false,\n
                // \"body\": \"" + message + "\"\n }\n}");
                RequestBody body = RequestBody.create(mediaType,
                                "{\n    \"messaging_product\": \"whatsapp\",    \n    \"recipient_type\": \"individual\",\n    \"to\": \""
                                                + phone
                                                + "\",\n    \"type\": \"text\",\n    \"text\": {\n        \"preview_url\": false,\n        \"body\": \""
                                                + message + "\"\n    }\n}");
                Request request = new Request.Builder()
                                .url(facebookUrl)
                                .method("POST", body)
                                .addHeader("Content-Type", "application/json")
                                .addHeader("Authorization", "Bearer " + token)
                                .build();
                String response = client.newCall(request).execute().body().string();

                OkWhatsappResponseDto result = objectMapper.readValue(response, OkWhatsappResponseDto.class);
                return result;

        }

        public Welcome morty() throws IOException {
                objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
                objectMapper.setVisibility(VisibilityChecker.Std.defaultInstance()
                                .withFieldVisibility(JsonAutoDetect.Visibility.ANY));
                MediaType mediaType = MediaType.parse("application/json");

                Request request = new Request.Builder()
                                .url("https://pokeapi.co/api/v2/version-group/11/")
                                .build();
                String response = client.newCall(request).execute().body().string();
                Welcome result = objectMapper.readValue(response, Welcome.class);

                return result;

        }

        // Response response = client.newCall(request).execute();

}
