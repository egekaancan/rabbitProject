package com.example.stock.listener;

import com.example.stock.configuration.PizzaMQConfig;
import com.example.stock.service.StockService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PizzaListener {

    private final ObjectMapper objectMapper;

    private final StockService stockService;

    private final RabbitTemplate rabbitTemplate;

    @RabbitListener(queues = PizzaMQConfig.PIZZA_QUEUE)
    public void getIngredients(String message) throws JsonProcessingException {
        List<String> stocks = objectMapper.readValue(message, List.class);
        List<Integer> stockAmountList = stocks
                .stream()
                .map(stock -> {
                    int amount;
                    if(stockService.getStockByName(stock).getName() == null){
                        amount = 0;
                    }
                    else {
                        amount = stockService.getStockByName(stock).getAmount();
                    }
                    return amount;
                })
                .toList();
        if (stockAmountList.contains(0)) {
            sendMessageToPizza("No Stock");
        } else {
            sendMessageToPizza("Confirmed");
        }
    }

    public void sendMessageToPizza(String message) {
        rabbitTemplate.convertAndSend(PizzaMQConfig.PIZZA_EXCHANGE, PizzaMQConfig.STOCK_ROUTING_KEY, message);
    }
}
