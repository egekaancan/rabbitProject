package com.example.order.listener;

import com.example.order.configuration.OrderStatusMQConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class PizzaListener {

    @RabbitListener(queues = OrderStatusMQConfig.ORDER_STATUS_QUEUE)
    public void listenOrderStatus(String message) {
        System.out.println(message);
    }
}
