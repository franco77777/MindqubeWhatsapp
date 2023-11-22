package com.app.whatsapp.ending.test.service;

import com.app.whatsapp.ending.test.dto.MindqubeMessageDto;
import com.app.whatsapp.ending.test.dto.MindqubeMessageResponseDto;
import com.app.whatsapp.ending.test.dto.OkWhatsappResponseDto;
import com.app.whatsapp.ending.test.entity.UserEntity;
import com.app.whatsapp.ending.test.entity.WhatsappMessageEntity;
import com.app.whatsapp.ending.test.repository.IUserRepository;
import com.app.whatsapp.ending.test.repository.IWhatsappRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MindqubeService {
    private final IUserRepository userRepository;
    private final IWhatsappRepository whatsappRepository;

    public Optional<UserEntity> getUser(String phone) {
        return userRepository.findByPhone(phone);
    }

    public WhatsappMessageEntity saveMessage(UserEntity user, OkWhatsappResponseDto result,
                                             MindqubeMessageDto payload) {
        var messageEntity = WhatsappMessageEntity.builder()
                .whatsapp_id(result.getMessages().get(0).getId())
                .message(payload.getMessage())
                .user(user)
                .status(null)
                .name("Mindqube")
                .build();
        return whatsappRepository.save(messageEntity);
    }

    public MindqubeMessageResponseDto setMessageForClient(UserEntity user,
                                                          WhatsappMessageEntity message) {
        return MindqubeMessageResponseDto.builder()
                .phone(user.getPhone())
                .message(message)
                .build();

    }

    public WhatsappMessageEntity addMessageId(OkWhatsappResponseDto result, WhatsappMessageEntity uploadImage) {
        uploadImage.setWhatsapp_id(result.getMessages().get(0).getId());
        return whatsappRepository.save(uploadImage);

    }
}
