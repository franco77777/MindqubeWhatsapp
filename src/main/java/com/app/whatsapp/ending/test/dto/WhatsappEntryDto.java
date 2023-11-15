package com.app.whatsapp.ending.test.dto;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class WhatsappEntryDto {
    private String id;
    private List<WhatsappChangesDto> changes;

}
