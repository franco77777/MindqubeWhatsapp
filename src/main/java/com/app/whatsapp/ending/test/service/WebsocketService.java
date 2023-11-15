package com.app.whatsapp.ending.test.service;

import com.app.whatsapp.ending.test.dto.WebsocketMessageDto;
import com.app.whatsapp.ending.test.dto.WhatsappValueDto;
import com.app.whatsapp.ending.test.entity.UserEntity;
import com.app.whatsapp.ending.test.entity.WhatsappMessageEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class WebsocketService {
    public WebsocketMessageDto newUserMessage(WhatsappMessageEntity messageSave,
            UserEntity user) {
        List<WhatsappMessageEntity> newMessage = new ArrayList<>();
        newMessage.add(messageSave);
        user.setWhatsapp(newMessage);
        return WebsocketMessageDto.builder()
                .user(user)
                .type("new user")
                .build();
    }

    public WebsocketMessageDto newMessage(WhatsappMessageEntity messageSave,
            Optional<UserEntity> user) {
        return WebsocketMessageDto.builder()
                .message(messageSave)
                .phone(user.orElseThrow().getPhone())
                .type("new message")
                .build();
    }

    public WebsocketMessageDto sendUpdateMessage(WhatsappValueDto value, WhatsappMessageEntity message) {
        return WebsocketMessageDto.builder()
                .message_id(value.getStatuses().get(0).getId())
                .status(value.getStatuses().get(0).getStatus())
                .phone(value.getStatuses().get(0).getRecipient_id())
                .timestamps(message.getTimestamp())
                .type("update message")
                .build();

    }
}
