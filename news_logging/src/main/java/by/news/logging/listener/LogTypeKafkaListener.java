package by.news.logging.listener;

import by.system.news.data.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class LogTypeKafkaListener {

    @KafkaListener(
            groupId = "log-group1",
            topics = "${spring.kafka.topic}")
    public void handleGreeting(Log greeting) {
        log.info("{}:{}", greeting.type(), greeting.comment());
    }
}
