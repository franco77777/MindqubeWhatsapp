package com.app.whatsapp.ending.test.controller;

import com.app.whatsapp.ending.test.dto.MindqubeMessageDto;
import com.app.whatsapp.ending.test.dto.MindqubeMessageResponseDto;
import com.app.whatsapp.ending.test.dto.MindqubeMessageTemplateDto;
import com.app.whatsapp.ending.test.dto.OkWhatsappResponseDto;
import com.app.whatsapp.ending.test.dto.ImageRequestDto;
import com.app.whatsapp.ending.test.entity.ImageData;
import com.app.whatsapp.ending.test.entity.UserEntity;

import com.app.whatsapp.ending.test.entity.WhatsappMessageEntity;
import com.app.whatsapp.ending.test.repository.IUserRepository;
import com.app.whatsapp.ending.test.repository.IWhatsappRepository;
import com.app.whatsapp.ending.test.repository.StorageRepository;
import com.app.whatsapp.ending.test.service.ImageUtils;
import com.app.whatsapp.ending.test.service.MindqubeService;
import com.app.whatsapp.ending.test.service.Send;
import com.app.whatsapp.ending.test.service.StorageService;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;

@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
@CrossOrigin("*")
public class MindqubeController {
    private final IUserRepository userRepository;
    private final MindqubeService mindqubeService;
    private final IWhatsappRepository whatsappRepository;
    private final Send send;

    @PostMapping("/enviar")
    ResponseEntity<OkWhatsappResponseDto> enviar(@RequestBody MindqubeMessageTemplateDto payload) throws IOException {

        OkWhatsappResponseDto result = send.templateToClient(payload.getPhoneNumber());

        return ResponseEntity.ok(result);
    }

    @PostMapping("/reenviar")
    ResponseEntity<MindqubeMessageResponseDto> reenviar(@RequestBody MindqubeMessageDto payload) throws IOException {
        Optional<UserEntity> user = mindqubeService.getUser(payload.getPhoneNumber());
        OkWhatsappResponseDto result = send.messageToClient(payload.getPhoneNumber(), payload.getMessage());
        if (user.isPresent() && result != null) { // until now we are sending messages to users in DB
            WhatsappMessageEntity whatsappMessageEntity = mindqubeService.saveMessage(user.orElseThrow(), result,
                    payload);
            MindqubeMessageResponseDto response = mindqubeService.setMessageForClient(user.orElseThrow(),
                    whatsappMessageEntity);
            return ResponseEntity.ok(response);

        }
        System.out.println("message mindqube not saved");
        return null;
    }

    @GetMapping("/test2")
    ResponseEntity<String> testing() {
        return ResponseEntity.ok("working");
    }


    @Autowired
    private StorageService service;

    ObjectMapper objectMapper = new ObjectMapper();

    private final StorageRepository storageRepository;


    @GetMapping("/testByte")
    ResponseEntity<byte[]> testing2(@RequestBody ImageRequestDto url) throws IOException {
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        objectMapper.setVisibility(VisibilityChecker.Std.defaultInstance()
                .withFieldVisibility(JsonAutoDetect.Visibility.ANY));
        //Response image = send.imageUrlToWhatsapp(url.getUrl());
        byte[] image = send.imageUrlToWhatsapp2(url.getImageId());


        var imageDb = ImageData.builder()
                .whatsapp_id("wamid.HBgNNTQ5Mzg3NTYxMDYwNhUCABIYFjNFQjA1QTYwQzE5Q0VENDc0M0I4NDEA")
                .imageData(image)
                .build();

        storageRepository.save(imageDb);
        //byte[] imageData = service.downloadImage(image.toString());
        //byte[] result = objectMapper.readValue(Arrays.toString(image), byte[].class);
        System.out.println("soy result");
        System.out.println(image);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf("image/jpeg"))
                .body(image);
    }


    public byte[] sendMessageReady(String url) {

        try {
            HttpRequest request = HttpRequest.newBuilder()

                    .uri(new URI(url))
                    .header("Authorization", "Bearer EAADz6AF76ZA4BOxQZAZCft95bxIbC269z2n2Ne20kjPxXHfVUtnXCiOZABZBHuyijaFv7sBphhmB28CHPA03FNFcQPu6T2XN0nYr4uUVPflCXx3TZATnr2rgCiZBosBwMDZArYw6F1gY9BpfnrxjHjv3WFx2GEggK0aJ9NHZBfgIQ5wwQfdw9liP95bw5teOXqAkohtdjHBTLuAsg95zNxHkZD")
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

    @GetMapping("image")
    ResponseEntity<byte[]> getimagen(@RequestParam String id) {
        WhatsappMessageEntity message = whatsappRepository.findByWhatsapp_id(id);
        //ImageData image = storageRepository.findByWhatsapp_id(id);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf(message.getImage_type()))
                //.contentType(MediaType.valueOf("image/jpeg"))
                .body(message.getImage_data());
    }


}
