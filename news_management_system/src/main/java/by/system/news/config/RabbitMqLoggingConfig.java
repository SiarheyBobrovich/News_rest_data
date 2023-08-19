package by.system.news.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Getter
@EnableRabbit
@Configuration
public class RabbitMqLoggingConfig {

    @Value("${spring.rabbitmq.publisher.logging.queue}")
    private String loggingQueueName;

    @Value("${spring.rabbitmq.publisher.logging.exchange}")
    private String loggingExchangeName;

    @Value("${spring.rabbitmq.publisher.logging.routing.key}")
    private String loggingRoutingKey;

    @Bean
    public Queue queue() {
        return new Queue(loggingQueueName);
    }

    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(loggingExchangeName);
    }

    @Bean
    public Binding binding(Queue queue,
                           TopicExchange exchange) {
        return BindingBuilder.bind(queue)
                .to(exchange)
                .with(loggingRoutingKey);
    }

    @Bean
    public MessageConverter messageConverter(ObjectMapper mapper) {
        return new Jackson2JsonMessageConverter(mapper);
    }

    @Bean
    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory,
                                     MessageConverter converter) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(converter);
        return rabbitTemplate;
    }
}
