package dev.spring.API.component;


import dev.spring.API.model.Product;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQConsumer {

    @RabbitListener(queues = "products-notification")
    public void receiveMessage(Product product) {
        System.out.println("Received product: " +product);
    }
}
