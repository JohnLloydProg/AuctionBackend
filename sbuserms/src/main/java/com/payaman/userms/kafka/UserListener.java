package com.payaman.userms.kafka;
import com.payaman.userms.service.UserConsumeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
@Component
@RequiredArgsConstructor
@Slf4j
public class UserListener {
    private final UserConsumeService userService;
    @KafkaListener(topics = "user.creation", groupId = "user-group")
    public void listen(String message){
        log.info("Kafka Listener: Received User Data -> {}", message);
        userService.consumeUser(message);
    }
}
