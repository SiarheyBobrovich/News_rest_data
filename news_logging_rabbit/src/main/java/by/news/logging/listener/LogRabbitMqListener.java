package by.news.logging.listener;

import by.news.logging.data.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class LogRabbitMqListener {
    
    @RabbitListener(queues = "${spring.rabbitmq.consumer.logging.queue}")
    public void log(Log log) {
        LogRabbitMqListener.log.info("{}:{}", log.type(), log.comment());
    }
}
