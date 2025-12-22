package dev.spring.API.component;

import dev.spring.API.model.Product;
import jakarta.persistence.EntityExistsException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQProducer {

    private final RabbitTemplate rabbitTemplate;

    private static final String EXCHANGE_NAME = "products-notification";
    private static final String ROUTING_KEY = "routingKey-products-notification";

    public RabbitMQProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessage(Object payload) {
        if (payload instanceof Product) {
            rabbitTemplate.convertAndSend(EXCHANGE_NAME, ROUTING_KEY, payload);
        }else throw new EntityExistsException("Can not parse this type of object");
    }
}
