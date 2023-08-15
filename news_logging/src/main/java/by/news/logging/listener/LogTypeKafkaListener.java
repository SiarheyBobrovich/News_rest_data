package by.news.logging.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@KafkaListener(
        id = "log-group1",
        groupId = "log-group1",
        topics = "${spring.kafka.topic}")
public class LogTypeKafkaListener {

    @KafkaHandler
    public void handleGreeting(String greeting) {
        log.info("log received:{}", greeting);
    }
}