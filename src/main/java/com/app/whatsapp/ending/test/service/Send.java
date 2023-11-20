/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.whatsapp.ending.test.service;

import com.app.whatsapp.ending.test.dto.OkWhatsappImageDto;
import com.app.whatsapp.ending.test.dto.OkWhatsappResponseDto;
import com.app.whatsapp.ending.test.dto.Welcome;
import com.app.whatsapp.ending.test.dto.WhatsappImageDto;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;

import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okhttp3.Response;
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

        String token = "EAADz6AF76ZA4BOxQZAZCft95bxIbC269z2n2Ne20kjPxXHfVUtnXCiOZABZBHuyijaFv7sBphhmB28CHPA03FNFcQPu6T2XN0nYr4uUVPflCXx3TZATnr2rgCiZBosBwMDZArYw6F1gY9BpfnrxjHjv3WFx2GEggK0aJ9NHZBfgIQ5wwQfdw9liP95bw5teOXqAkohtdjHBTLuAsg95zNxHkZD";
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

        public OkWhatsappImageDto imageIdToWhatsapp(WhatsappImageDto image) throws IOException {
                String imageId = image.getId();
                OkHttpClient client = new OkHttpClient().newBuilder()
                                .build();
                MediaType mediaType = MediaType.parse("application/json");
                RequestBody body = RequestBody.create(mediaType, "");
                Request request = new Request.Builder()
                                .url("https://graph.facebook.com/v17.0/" + imageId + "?phone_number_id=108928785480520")
                                .method("GET", body)
                                .addHeader("Authorization", "Bearer " + token)
                                .build();
                String response = client.newCall(request).execute().body().string();
                return objectMapper.readValue(response, OkWhatsappImageDto.class);
        }

        public Response imageUrlToWhatsapp(String url) throws IOException {

                objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
                objectMapper.setVisibility(VisibilityChecker.Std.defaultInstance()
                                .withFieldVisibility(JsonAutoDetect.Visibility.ANY));
                OkHttpClient client = new OkHttpClient().newBuilder()
                                .build();
                MediaType mediaType = MediaType.parse("text/plain");
                RequestBody body = RequestBody.create(mediaType, "");
                Request request = new Request.Builder()
                                .url(url)

                                .addHeader("Authorization", "Bearer " + token)
                                .addHeader("Content-Type", "text/plain")
                                .build();
                Response response = client.newCall(request).execute();
                System.out.println("soy mapper");
                System.out.println(objectMapper.writerWithDefaultPrettyPrinter()
                                .writeValueAsString(response.body().toString()));
                // byte[] result = objectMapper.readValue(response, byte[].class);
                // System.out.println("soy result");
                // System.out.println(result);
                return response;
        }

        public Welcome morty() throws IOException {
                objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
                objectMapper.setVisibility(VisibilityChecker.Std.defaultInstance()
                                .withFieldVisibility(JsonAutoDetect.Visibility.ANY));

                Request request = new Request.Builder()
                                .url("https://pokeapi.co/api/v2/version-group/11/")
                                .build();
                String response = client.newCall(request).execute().body().string();
                Welcome result = objectMapper.readValue(response, Welcome.class);

                return result;

        }

        // Response response = client.newCall(request).execute();

}
