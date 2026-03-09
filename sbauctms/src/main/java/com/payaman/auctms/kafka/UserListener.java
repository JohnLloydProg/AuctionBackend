package com.payaman.auctms.kafka;

import com.payaman.auctms.model.User;
import com.payaman.auctms.service.UserConsumerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class UserListener {
    Logger logger = Logger.getLogger("UserListener");

    @Autowired
    UserConsumerService userConsumerService;

    // Listens for user events (only containing username/email)
    @KafkaListener(topics = "user.creation", groupId = "auction-group")
    public void listen(User user) {
        logger.info("Kafka Listener: Received User Data " + user.getUserId());
        userConsumerService.consumeUser(user);
    }
}