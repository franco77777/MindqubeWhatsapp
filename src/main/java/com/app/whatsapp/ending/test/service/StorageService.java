package com.app.whatsapp.ending.test.service;

import com.app.whatsapp.ending.test.entity.ImageData;
import com.app.whatsapp.ending.test.entity.UserEntity;
import com.app.whatsapp.ending.test.entity.WhatsappMessageEntity;
import com.app.whatsapp.ending.test.repository.IUserRepository;
import com.app.whatsapp.ending.test.repository.IWhatsappRepository;
import com.app.whatsapp.ending.test.repository.StorageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StorageService {


    private StorageRepository repository;
    private final IUserRepository userRepository;
    private final IWhatsappRepository whatsappRepository;


    public WhatsappMessageEntity uploadImage(MultipartFile file, UserEntity user) throws IOException {

        byte[] imageCompressed = ImageUtils.compressImage(file.getBytes());

        var message = WhatsappMessageEntity.builder()
                .image_data(downloadImage(imageCompressed))
                .name("Mindqube")
                .whatsapp_id("empty")
                .image_type(file.getContentType())
                .user(user)
                //.timestamp()
                .build();

        return whatsappRepository.save(message);


    }

//    public byte[] downloadImage(String id) {
//        Optional<WhatsappMessageEntity> dbImageData = whatsappRepository.findById(Long.parseLong(id));
//        return ImageUtils.decompressImage(dbImageData.get().getImage_data());
//    }

    public byte[] downloadImage(byte[] image) {
        //Optional<WhatsappMessageEntity> dbImageData = whatsappRepository.findById(Long.parseLong(id));
        return ImageUtils.decompressImage(image);
    }

}
