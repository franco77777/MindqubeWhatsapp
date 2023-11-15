package com.app.whatsapp.ending.test.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WhatsappStatusesDto {
    private String id;
    private String status;
    private String timestamp;
    private String recipient_id;
}
