package com.app.whatsapp.ending.test.controller;

import com.app.whatsapp.ending.test.dto.MindqubeMessageDto;
import com.app.whatsapp.ending.test.dto.MindqubeMessageResponseDto;
import com.app.whatsapp.ending.test.dto.MindqubeMessageTemplateDto;
import com.app.whatsapp.ending.test.dto.OkWhatsappResponseDto;
import com.app.whatsapp.ending.test.entity.UserEntity;

import com.app.whatsapp.ending.test.entity.WhatsappMessageEntity;
import com.app.whatsapp.ending.test.repository.IUserRepository;
import com.app.whatsapp.ending.test.repository.IWhatsappRepository;
import com.app.whatsapp.ending.test.service.MindqubeService;
import com.app.whatsapp.ending.test.service.Send;
import lombok.RequiredArgsConstructor;
import okhttp3.ResponseBody;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Optional;
import okhttp3.Response;

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

    @GetMapping("/testByte")
    ResponseEntity<Response> testing2(@RequestParam String url) throws IOException {
        Response image = send.imageUrlToWhatsapp(url);
        System.out.println("im image of controller");
        System.out.println(image);
        return ResponseEntity.ok(image);
    }

}
