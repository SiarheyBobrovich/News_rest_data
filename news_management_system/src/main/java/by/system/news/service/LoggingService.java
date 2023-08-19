package by.system.news.service;

import by.system.news.config.RabbitMqLoggingConfig;
import by.system.news.data.Log;
import by.system.news.entity.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.annotation.HandleAfterCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeDelete;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RepositoryEventHandler
@RequiredArgsConstructor
public class LoggingService {

    private final RabbitMqLoggingConfig rabbitConfig;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final RabbitTemplate rabbitTemplate;

    @Value("${spring.kafka.topic}")
    private String topic;

    @HandleAfterCreate
    public void handleBeforeCreate(Comment comment) {
        Log create = new Log("Create", comment);
        kafkaTemplate.send(topic, create);
    }

    @HandleBeforeDelete
    public void handleBeforeDelete(Comment comment) {
        Log log = new Log("Delete", comment);
        rabbitTemplate.convertAndSend(
                rabbitConfig.getLoggingExchangeName(),
                rabbitConfig.getLoggingRoutingKey(),
                log
        );
    }
}
