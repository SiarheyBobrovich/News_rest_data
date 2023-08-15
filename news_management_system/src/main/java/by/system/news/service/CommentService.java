package by.system.news.service;

import by.system.news.data.Log;
import by.system.news.entity.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

//@Component
@RepositoryEventHandler
@RequiredArgsConstructor
public class CommentService {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Value("${spring.kafka.topic}")
    private String topic;

    @HandleBeforeCreate
    public void handleBeforeCreate(Comment comment) {
        Log create = new Log("Create", comment.toString());
        kafkaTemplate.send(topic, create);
    }
}
