package com.example.pizza.listener;

import com.example.pizza.configuration.PizzaMQConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StockListener {

    private String stockStatus;

    @RabbitListener(queues = PizzaMQConfig.STOCK_QUEUE)
    public void listenStock(String message) {
        this.stockStatus = message;
        System.out.println(getStockStatus());
    }

    public String getStockStatus() {
        return stockStatus;
    }

}
