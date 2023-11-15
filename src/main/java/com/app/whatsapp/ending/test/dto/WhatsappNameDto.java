package com.app.whatsapp.ending.test.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WhatsappNameDto {
    private String formatted_name;
    private String first_name;
    private String last_name;
    private String middle_name;
    private String suffix;
    private String prefix;
}
