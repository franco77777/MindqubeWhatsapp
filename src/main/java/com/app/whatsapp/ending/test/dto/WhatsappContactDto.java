package com.app.whatsapp.ending.test.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WhatsappContactDto {
    private List<WhatsappAdressDto> addresses;
    private String birthday;
    private List<WhatsappEmailDto> emails;
    private WhatsappNameDto name;
    private WhatsappOrgDto org;
    private List<WhatsappPhoneDto> phones;
    private List<WhatsappPhoneDto> urls;

}
