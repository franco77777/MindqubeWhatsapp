package com.app.whatsapp.ending.test.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WhatsappMessagesDto {
    private String from;
    private String id;
    private String timestamp;
    private WhatsappTextDto text;
    private String type;
    private WhatsappReactionDto reaction;
    private WhatsappImageDto image;
    private WhatsappStickerDto sticker;
    private List<WhatsappErrorsDto> errors;
    private WhatsappLocationDto location;
    private WhatsappButtonDto button;
    private List<WhatsappContactDto> contacts;
}
