package com.payaman.auctms.kafka;

import com.payaman.auctms.serviceimpl.UserConsumerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserListener {

    private final UserConsumerService userService;

    // Listens for user events (only containing username/email)
    @KafkaListener(topics = "user.creation", groupId = "auction-group")
    public void listen(String message) {
        log.info("Kafka Listener: Received User Data -> {}", message);
        userService.consumeUser(message);
    }
}