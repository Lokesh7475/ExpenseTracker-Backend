package com.Lokesh.ExpenseTracker.Email;

import com.Lokesh.ExpenseTracker.Config.RabbitMQConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class EmailQueueConsumer {

    private final EmailService emailService;

    @RabbitListener(queues = RabbitMQConfig.EMAIL_QUEUE)
    public void consumeMessage(Map<String, String> payload){
        emailService.sendOTPEmail(payload.get("email"), payload.get("otp"));
    }
}
