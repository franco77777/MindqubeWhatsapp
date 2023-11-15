package com.app.whatsapp.ending.test.repository;

import com.app.whatsapp.ending.test.entity.WhatsappMessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface IWhatsappRepository extends JpaRepository<WhatsappMessageEntity, Long> {
    @Query(value = "SELECT * FROM whatsapp WHERE whatsapp_id=?1", nativeQuery = true)
    WhatsappMessageEntity findByWhatsapp_id(String id);
}
