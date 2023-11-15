package com.app.whatsapp.ending.test.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class OkWhatsappResponseDto {
    private String messaging_product;
    private List<OkWhatsappContactDto> contacts;
    private List<OkWhatsappMessageDto> messages;
}
