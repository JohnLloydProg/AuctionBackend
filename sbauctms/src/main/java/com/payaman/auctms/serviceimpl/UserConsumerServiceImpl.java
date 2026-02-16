package com.payaman.auctms.serviceimpl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.payaman.auctms.entity.UserData;
import com.payaman.auctms.repository.UserDataRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserConsumerServiceImpl implements com.payaman.auctms.service.UserConsumerService {

    private final UserDataRepository userRepository;
    private final ObjectMapper objectMapper;

    @Override
    @Transactional
    public void consumeUser(String message) {
        try {
            JsonNode rootNode = objectMapper.readTree(message);

            String extractedUsername = rootNode.path("username").asText(null);
            String extractedEmail = rootNode.path("email").asText(null);

            if (extractedUsername == null || extractedEmail == null) {
                log.warn("Skipping message: Missing username or email.");
                return;
            }

            UserData user = new UserData();
            user.setUserId(UUID.randomUUID().toString());

            user.setUsername(extractedUsername);
            user.setEmail(extractedEmail);

            user.setPasswordHash("TEMP_HASH_123");
            user.setRole("BUYER");
            user.setStatus("ACTIVE");

            userRepository.save(user);
            log.info("Service: Saved User [User: {}, Email: {}]", extractedUsername, extractedEmail);

        } catch (Exception e) {
            log.error("Service Error processing user: {}", e.getMessage());
        }
    }
}