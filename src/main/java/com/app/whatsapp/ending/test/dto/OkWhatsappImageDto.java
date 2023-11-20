package com.app.whatsapp.ending.test.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OkWhatsappImageDto {
    private String url;
    private String mime_type;
    private String sha256;
    private long file_size;
    private String id;
    private String messaging_product;
}
