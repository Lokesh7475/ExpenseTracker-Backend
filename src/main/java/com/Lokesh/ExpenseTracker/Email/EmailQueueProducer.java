package com.Lokesh.ExpenseTracker.Email;

import com.Lokesh.ExpenseTracker.Config.RabbitMQConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class EmailQueueProducer {
    private final RabbitTemplate rabbitTemplate;

    public void sendOTPMessage(String email, String otp){
        Map<String, String> payload = new HashMap<>();

        payload.put("email", email);
        payload.put("otp", otp);

        rabbitTemplate.convertAndSend(RabbitMQConfig.EMAIL_QUEUE, payload);
    }
}
