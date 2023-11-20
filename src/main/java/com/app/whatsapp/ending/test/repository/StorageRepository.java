package com.app.whatsapp.ending.test.repository;

import com.app.whatsapp.ending.test.entity.ImageData;
import com.app.whatsapp.ending.test.entity.WhatsappMessageEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

@Transactional
public interface StorageRepository extends JpaRepository<ImageData, Long> {
    @Query(value = "SELECT * FROM image_data.* WHERE whatsapp_id=?1", nativeQuery = true)
    ImageData findByWhatsapp_id(String id);
}
