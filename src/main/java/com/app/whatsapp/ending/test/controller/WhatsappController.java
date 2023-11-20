package com.app.whatsapp.ending.test.controller;

import com.app.whatsapp.ending.test.dto.OkWhatsappImageDto;
import com.app.whatsapp.ending.test.dto.WebsocketMessageDto;
import com.app.whatsapp.ending.test.dto.WhatsappImageDto;
import com.app.whatsapp.ending.test.dto.WhatsappResponseDto;

import com.app.whatsapp.ending.test.dto.WhatsappStatusesDto;
import com.app.whatsapp.ending.test.dto.WhatsappValueDto;
import com.app.whatsapp.ending.test.entity.UserEntity;
import com.app.whatsapp.ending.test.entity.WhatsappMessageEntity;
import com.app.whatsapp.ending.test.repository.IUserRepository;
import com.app.whatsapp.ending.test.repository.IWhatsappRepository;
import com.app.whatsapp.ending.test.service.Send;
import com.app.whatsapp.ending.test.service.WebsocketService;
import com.app.whatsapp.ending.test.service.WhatsappService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/webhook")
@RequiredArgsConstructor
@CrossOrigin("*")
public class WhatsappController {
    private final SimpMessageSendingOperations messagingTemplate;
    private final IUserRepository userRepository;
    private final IWhatsappRepository whatsappRepository;
    private final WhatsappService whatsappService;
    private final WebsocketService websocketService;
    private final Send send;

    @GetMapping("/users")
    ResponseEntity<List<UserEntity>> listOfUsers() {
        List<UserEntity> users = userRepository.findAll();
        return ResponseEntity.ok(users);
    }

    @GetMapping()
    ResponseEntity<String> receive(@RequestParam(value = "hub.mode") String mode,
                                   @RequestParam(value = "hub.challenge") String challenge,
                                   @RequestParam(value = "hub.verify_token") String token) {
        String myToken = "password";

        if (token != null && mode != null) {
            if ("subscribe".equals(mode) && token.equals(myToken)) {
                System.out.println("no problem and sending challenge");
                System.out.println(challenge);
                return ResponseEntity.ok(challenge);
            }
        }
        System.out.println("problem in ");
        System.out.println(challenge);

        return ResponseEntity.ok(challenge);
    }

    @PostMapping()
    ResponseEntity<String> whatsappPayload(@RequestBody WhatsappResponseDto payload) throws IOException {
        List<WhatsappStatusesDto> statuses = payload.getEntry().get(0).getChanges().get(0).getValue().getStatuses();
        WhatsappValueDto value = payload.getEntry().get(0).getChanges().get(0).getValue();
        ObjectMapper mapper = new ObjectMapper();
        System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(payload));
        if (statuses == null) { // this is for users messages received
            Optional<UserEntity> user = whatsappService.userAlreadyExists(value);
            WhatsappImageDto image = value.getMessages().get(0).getImage();
            if (user.isPresent()) {
                if (image != null) {
                    OkWhatsappImageDto imageResponse = send.imageIdToWhatsapp(image);
                    //byte[] imageBinarie = send.imageUrlToWhatsapp(imageResponse.getUrl());
                }
                WhatsappMessageEntity messageSaved = whatsappService.saveClientMessage(user.orElseThrow(), value);
                WebsocketMessageDto socketMessage = websocketService.newMessage(messageSaved,
                        user);
                messagingTemplate.convertAndSend("/topic/public", socketMessage);
                return ResponseEntity.ok("message user saved");
            } else {
                UserEntity newUser = whatsappService.createUser(value);
                WhatsappMessageEntity messageSaved = whatsappService.saveClientMessage(newUser, value);
                WebsocketMessageDto socketMessage = websocketService.newUserMessage(messageSaved, newUser);
                messagingTemplate.convertAndSend("/topic/public", socketMessage);
                return ResponseEntity.ok("new user and message saved");
            }
        } else { // this is for mindqube message sent
            WhatsappMessageEntity messageUpdated = whatsappService.updateMessage(value);
            WebsocketMessageDto updatingMessage = websocketService.sendUpdateMessage(value, messageUpdated);
            messagingTemplate.convertAndSend("/topic/public", updatingMessage);
            return ResponseEntity.ok("message updated");
            // messagingTemplate.convertAndSend("/topic/public", messageToSend);
        }
    }

    @PostMapping("/test")
    ResponseEntity<String> test(@RequestBody WhatsappResponseDto whatsapp) throws IOException {
        List<WhatsappStatusesDto> statuses = whatsapp.getEntry().get(0).getChanges().get(0).getValue().getStatuses();
        WhatsappValueDto value = whatsapp.getEntry().get(0).getChanges().get(0).getValue();
        if (statuses == null) { // this is for users
            Optional<UserEntity> user = whatsappService.userAlreadyExists(value);
            if (user.isPresent()) {
                whatsappService.saveClientMessage(user.orElseThrow(), value);
                return ResponseEntity.ok("message user saved");
            } else {
                UserEntity newUser = whatsappService.createUser(value);
                WhatsappMessageEntity message = whatsappService.saveClientMessage(newUser, value);
                List<WhatsappMessageEntity> messageList = new ArrayList<>();
                messageList.add(message);
                newUser.setWhatsapp(messageList);

                System.out.println("soy el user despues de creado");

                return ResponseEntity.ok("new user and message saved");
            }
        } else { // this is for mindqube
            WhatsappMessageEntity messageUpdated = whatsappService.updateMessage(value);
            return ResponseEntity.ok("message updated");
        }

    }

    @PostMapping("/response")
    ResponseEntity<UserEntity> test2(@RequestBody WhatsappResponseDto whatsapp) throws IOException {
        WhatsappValueDto value = whatsapp.getEntry().get(0).getChanges().get(0).getValue();
        UserEntity newUser = whatsappService.createUser(value);
        WhatsappMessageEntity message = whatsappService.saveClientMessage(newUser, value);
        List<WhatsappMessageEntity> messageList = new ArrayList<>();
        messageList.add(message);
        newUser.setWhatsapp(messageList);
        return ResponseEntity.ok(newUser);

    }

    @GetMapping("/test3")
    ResponseEntity<String> testing() {
        return ResponseEntity.ok("working");
    }
}
// Optional<UserEntity> userDB = userService.userAlreadyExists(value);
// if (userDB.isEmpty()) {
// UserEntity user = userService.saveInDatabase(value);
// userService.saveMessage(user, value);
// return ResponseEntity.ok("saved");
// }
// userService.saveMessage(userDB.orElseThrow(), value);

// Welcome response = send.morty();
// ObjectMapper mapper = new ObjectMapper();
// System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(response));
