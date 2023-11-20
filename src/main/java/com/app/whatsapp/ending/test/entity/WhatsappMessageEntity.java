package com.app.whatsapp.ending.test.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "whatsapp")
public class WhatsappMessageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String message;
    private String name;
    private String status;
    @NotNull
    private String whatsapp_id;
    private String image_type;
    @Lob
    @JsonIgnore
    //@Column(name = "imagedata",length = 1000)
    private byte[] image_data;

    // @ManyToOne(fetch = FetchType.EAGER, optional = true)
    // @JoinColumn(name = "id_role")
    // @ManyToOne(fetch = FetchType.EAGER, optional = true)

    // @JoinColumn(name = "id_role")
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    // @JoinColumn(name = "DOCUMENT_REVISION_ID")
    private UserEntity user;

    private String timestamp;
}
