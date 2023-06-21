package com.example.pizza.listener;

import com.example.pizza.configuration.PizzaMQConfig;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StockListener {

    private String stockStatus;

    private final ObjectMapper objectMapper;

    @RabbitListener(queues = PizzaMQConfig.STOCK_QUEUE)
    public void listenStock(String message) throws JsonProcessingException {
        String status = objectMapper.readValue(message, String.class);
        this.stockStatus = status;
    }

    public String getStockStatus() {
        return stockStatus;
    }

}
