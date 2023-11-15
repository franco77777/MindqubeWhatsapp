package com.app.whatsapp.ending.test.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class WhatsappValueDto {
    private String messaging_product;
    private WhatsappMetadataDto metadata;
    private List<WhatsappContactsDto> contacts;
    private List<WhatsappMessagesDto> messages;
    private List<WhatsappStatusesDto> statuses;
}
