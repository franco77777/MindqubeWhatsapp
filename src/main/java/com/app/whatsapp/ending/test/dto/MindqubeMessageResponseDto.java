package com.app.whatsapp.ending.test.dto;

import com.app.whatsapp.ending.test.entity.WhatsappMessageEntity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MindqubeMessageResponseDto {
    private String phone;
    private WhatsappMessageEntity message;
}
