package com.app.whatsapp.ending.test.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MindqubeMessageDto {
    private String message;
    private String phoneNumber;
}
