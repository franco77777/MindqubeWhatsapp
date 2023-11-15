package com.app.whatsapp.ending.test.dto;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class WhatsappResponseDto {
    private String object;
    private List<WhatsappEntryDto> entry;
}
