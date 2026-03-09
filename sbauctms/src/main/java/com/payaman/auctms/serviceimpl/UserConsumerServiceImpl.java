package com.payaman.auctms.serviceimpl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.payaman.auctms.entity.UserData;
import com.payaman.auctms.model.User;
import com.payaman.auctms.repository.UserDataRepository;
import com.payaman.auctms.service.UserConsumerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserConsumerServiceImpl implements UserConsumerService {

    private final UserDataRepository userRepository;
    private final ObjectMapper objectMapper;

    @Override
    @Transactional
    public void consumeUser(User user) {
        try {
            UserData userData = new UserData();
            userData.setUserId(user.getUserId());

            userData.setUsername(user.getUsername());
            userData.setEmail(user.getEmail());

            userData.setPasswordHash("TEMP_HASH_123");
            userData.setRole("BUYER");
            userData.setStatus("ACTIVE");

            userRepository.save(userData);
        } catch (Exception e) {
            log.error("Service Error processing user: {}", e.getMessage());
        }
    }
}