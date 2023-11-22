package com.app.whatsapp.ending.test.service;

import com.app.whatsapp.ending.test.entity.UserEntity;
import com.app.whatsapp.ending.test.entity.WhatsappMessageEntity;
import com.app.whatsapp.ending.test.repository.IUserRepository;
import com.app.whatsapp.ending.test.repository.IWhatsappRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor

public class Runner implements CommandLineRunner {

    private final IUserRepository userRepository;
    private final IWhatsappRepository whatsappRepository;

    @Override
    public void run(String... args) throws Exception {

        var user = UserEntity.builder()
                .name("test")
                .phone("123")
                .build();
        UserEntity userEntity = userRepository.save(user);

        // var messageEntity = WhatsappMessageEntity.builder()
        // .whatsapp_id("wamid.HBgNNTQ5Mzg3NTYxMDYwNhUCABEYEkE1MTRFOThFQjM5NTI1OTQ3MAA=")
        // .message("mock message")
        // .status("delivered")
        // .user(user)
        // .name("Mindqube")
        // .build();

        // WhatsappMessageEntity res3 = whatsappRepository.save(messageEntity);

        // WhatsappMessageEntity tesapp = whatsappRepository.findById(1L).orElseThrow();
        // tesapp.setMessage("funciono ?");
        // whatsappRepository.save(tesapp);

        // List<WhatsappEntity> messages = new ArrayList<>();
        // messages.add(res);
    }

}
