package com.app.whatsapp.ending.test.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MindqubeMessageDB {
    private Long id;
    private String name;
    private String message;
    private String status;
    private String timestamp;
}
