package com.app.whatsapp.ending.test.service;

import com.app.whatsapp.ending.test.dto.WhatsappValueDto;
import com.app.whatsapp.ending.test.entity.UserEntity;
import com.app.whatsapp.ending.test.entity.WhatsappMessageEntity;
import com.app.whatsapp.ending.test.repository.IUserRepository;
import com.app.whatsapp.ending.test.repository.IWhatsappRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WhatsappService {
    private final IUserRepository userRepository;
    private final IWhatsappRepository whatsappRepository;

    public Optional<UserEntity> userAlreadyExists(WhatsappValueDto value) {
        String phone = value.getMessages().get(0).getFrom();
        return userRepository.findByPhone(phone);
    }

    public UserEntity saveInDatabase(WhatsappValueDto value) {
        String phone = value.getMessages().get(0).getFrom();
        String name = value.getContacts().get(0).getProfile().getName();
        var user = UserEntity.builder()
                .name(name)
                .phone(phone)
                .build();
        return userRepository.save(user);
    }

    public WhatsappMessageEntity saveClientMessage(UserEntity user, WhatsappValueDto value) {
        WhatsappMessageEntity whatsappMessageEntity = WhatsappMessageEntity.builder()
                .name(value.getContacts().get(0).getProfile().getName())
                .whatsapp_id(value.getMessages().get(0).getId())
                .message(value.getMessages().get(0).getText().getBody())
                .status("received")
                .timestamp(value.getMessages().get(0).getTimestamp())
                .user(user)
                .build();
        return whatsappRepository.save(whatsappMessageEntity);

    }

    public WhatsappMessageEntity saveClientImage(UserEntity user, WhatsappValueDto value) {
        WhatsappMessageEntity whatsappMessageEntity = WhatsappMessageEntity.builder()
                .name(value.getContacts().get(0).getProfile().getName())
                .whatsapp_id(value.getMessages().get(0).getId())
                .message("im the image")
                .status("received")
                .timestamp(value.getMessages().get(0).getTimestamp())

                .user(user)
                .build();
        return whatsappRepository.save(whatsappMessageEntity);

    }

    public UserEntity createUser(WhatsappValueDto value) {
        var newUser = UserEntity.builder()
                .name(value.getContacts().get(0).getProfile().getName())
                .phone(value.getMessages().get(0).getFrom())
                .build();
        return userRepository.save(newUser);
    }

    public WhatsappMessageEntity updateMessage(WhatsappValueDto value) {
        WhatsappMessageEntity message = whatsappRepository.findByWhatsapp_id(value.getStatuses().get(0).getId());
        message.setStatus(value.getStatuses().get(0).getStatus());
        if (message.getTimestamp() == null) { // adding timestamps only once
            message.setTimestamp(value.getStatuses().get(0).getTimestamp());
        }
        return whatsappRepository.save(message);
    }

    public UserEntity setMessageInUser(UserEntity newUser, WhatsappMessageEntity messageSaved) {
        List<WhatsappMessageEntity> messageList = new ArrayList<>();
        messageList.add(messageSaved);
        newUser.setWhatsapp(messageList);
        return newUser;
    }
}
