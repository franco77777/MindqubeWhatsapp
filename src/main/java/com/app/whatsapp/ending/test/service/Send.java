/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.whatsapp.ending.test.service;

import com.app.whatsapp.ending.test.dto.OkWhatsappImageDto;
import com.app.whatsapp.ending.test.dto.OkWhatsappResponseDto;
import com.app.whatsapp.ending.test.dto.Welcome;
import com.app.whatsapp.ending.test.dto.WhatsappImageDto;
import com.app.whatsapp.ending.test.entity.UserEntity;
import com.app.whatsapp.ending.test.entity.WhatsappMessageEntity;
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
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * @author aresis
 */
@Service
public class Send {

    ObjectMapper objectMapper = new ObjectMapper();

    OkHttpClient client = new OkHttpClient().newBuilder()
            .build();

    String token = "EAADz6AF76ZA4BO8gmTSB05oyCztc1FZAT4IPJhBUZBZCb8n4upDPvwvtHixA4uwqux6X1BqAatFAWYZAWOHmBQ0PZCddvHKm5VxjEzlMfspEf7tfIxhcn5qIgu1vbGCvKBuUEspnV0zZBmu8sYN7FIABf5xp8Co6RDRCAjKsV2QrEkOME4dKZCz1jb8sUOqd3q4ZBtLfA2jZBLsGvtECpyTrlc";
    String facebookUrl = "https://graph.facebook.com/v17.0/108928785480520/messages";
    String imageUrl = "https://wa.mindqube.com/chat/image?id=";

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
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        objectMapper.setVisibility(VisibilityChecker.Std.defaultInstance()
                .withFieldVisibility(JsonAutoDetect.Visibility.ANY));
        String imageId = image.getId();
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Request request = new Request.Builder()
                .url("https://graph.facebook.com/v17.0/" + imageId + "?phone_number_id=108928785480520")
                .addHeader("Authorization", "Bearer " + token)
                .header("Content-Type", "application/json")
                .build();
        String response = client.newCall(request).execute().body().string();
        return objectMapper.readValue(response, OkWhatsappImageDto.class);
    }

    public byte[] imageUrlToWhatsapp(OkWhatsappImageDto imageResponse) throws IOException {
        String url = imageResponse.getUrl();
        try {
            HttpRequest request = HttpRequest.newBuilder()

                    .uri(new URI(url))
                    .header("Authorization", "Bearer " + token)
                    .header("Content-Type", "application/json")
                    //.POST(HttpRequest.BodyPublishers.ofString("{ \"messaging_product\": \"whatsapp\", \"recipient_type\": \"individual\", \"to\": \""+number+"\", \"type\": \"template\", \"template\": { \"name\": \"hello_world\", \"language\": { \"code\": \"en_US\" } } }"))

                    .build();
            HttpClient http = HttpClient.newHttpClient();
            HttpResponse<byte[]> response = http.send(request, HttpResponse.BodyHandlers.ofByteArray());
            System.out.println("soy response body");
            System.out.println(response.body());
            return response.body();


        } catch (URISyntaxException | IOException | InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("nothing happened");
        return null;
    }

    public byte[] imageUrlToWhatsapp2(String url) throws IOException {

        try {
            HttpRequest request = HttpRequest.newBuilder()

                    .uri(new URI(url))
                    .header("Authorization", "Bearer " + token)
                    .header("Content-Type", "application/json")
                    //.POST(HttpRequest.BodyPublishers.ofString("{ \"messaging_product\": \"whatsapp\", \"recipient_type\": \"individual\", \"to\": \""+number+"\", \"type\": \"template\", \"template\": { \"name\": \"hello_world\", \"language\": { \"code\": \"en_US\" } } }"))

                    .build();
            HttpClient http = HttpClient.newHttpClient();
            HttpResponse<byte[]> response = http.send(request, HttpResponse.BodyHandlers.ofByteArray());
            System.out.println("soy response body");
            System.out.println(response.body());
            return response.body();


        } catch (URISyntaxException | IOException | InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("nothing happened");
        return null;
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

    public OkWhatsappResponseDto ImageToClient(UserEntity user, WhatsappMessageEntity uploadImage) throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "{\n    \"messaging_product\": \"whatsapp\",\n    \"recipient_type\": \"individual\",\n    \"to\": \"" + user.getPhone() + "\",\n    \"type\": \"image\",\n    \"image\": {\n        \"link\": \"" + imageUrl + uploadImage.getId() + "\"\n    }\n}");
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

    // Response response = client.newCall(request).execute();

}
