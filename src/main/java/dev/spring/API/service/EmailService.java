package dev.spring.API.service;

import dev.spring.API.Dto.EmailDetails;
import dev.spring.API.model.Product;
import jakarta.mail.MessagingException;
import jakarta.persistence.EntityExistsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    private static final String PRODUCT_CREATED_TEMPLATE = """
Hello,

A new product has been added to your account.

Product details:
----------------
Name: %s
Price: %s
Stock: %s
Category: %s

Thank you,
Online Shop Team
""";

    private final JavaMailSender javaMailSender;
    public EmailService(JavaMailSender mailSender) {
        this.javaMailSender = mailSender;
    }

    public void sendSimpleMail(Product product){
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setFrom("onlineshop@yahoo.com");
        mailMessage.setTo("user@yahoo.com");
        mailMessage.setSubject("Product added in your account");
        mailMessage.setText(String.format(
                PRODUCT_CREATED_TEMPLATE,
                product.getName(),
                product.getPrice(),
                product.getStock(),
                product.getCategory().getName()
        ));

        javaMailSender.send(mailMessage);
        logger.info("Sending email with the following details:{}, {}, {}, {}", product.getName(),product.getPrice(), product.getStock(), product.getCategory().getName());
    }
}
