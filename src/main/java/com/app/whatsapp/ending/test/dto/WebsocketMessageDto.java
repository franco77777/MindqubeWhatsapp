package com.app.whatsapp.ending.test.dto;

import com.app.whatsapp.ending.test.entity.UserEntity;
import com.app.whatsapp.ending.test.entity.WhatsappMessageEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class WebsocketMessageDto {
    private String phone;
    private WhatsappMessageEntity message;
    private String type;
    private UserEntity user;
    private String status;
    private String message_id;
    private String timestamps;
}
