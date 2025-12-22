package dev.spring.API.component;


import dev.spring.API.model.Product;
import dev.spring.API.service.EmailService;
import dev.spring.API.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQConsumer {

    private static final Logger logger = LoggerFactory.getLogger(RabbitMQConsumer.class);
    private final EmailService emailService;

    public RabbitMQConsumer(EmailService emailService) {
        this.emailService = emailService;
    }

    @RabbitListener(queues = "products-notification")
    public void receiveMessage(Product product) {
        logger.info("Received product: {}, {}, {} ", product.getName(), product.getPrice(), product.getCategory().getName());
        emailService.sendSimpleMail(product);

    }
}
