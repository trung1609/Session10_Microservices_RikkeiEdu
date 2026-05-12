package com.example.notificationservice;

import com.example.notificationservice.event.OrderEvent;
import lombok.extern.apachecommons.CommonsLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class EmailConsumer {
    @Autowired
    private JavaMailSender mailSender;

    @KafkaListener(topics = "medicine-stock-events", groupId = "email-service")
    public void sendOrderConfirmation(OrderEvent request) {
        log.info("Received order confirmation: {}", request.getUserEmail());
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("trung8d2005@gmail.com"); // địa chỉ gửi
        message.setTo(request.getUserEmail());           // địa chỉ nhận
        message.setSubject("Xác nhận đơn hàng");
        message.setText("Cảm ơn bạn đã đặt hàng! Đơn hàng của bạn đã được xác nhận và đang được xử lý.");

        mailSender.send(message);
    }
}
