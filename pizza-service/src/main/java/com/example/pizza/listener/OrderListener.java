package com.example.pizza.listener;


import com.example.pizza.configuration.OrderMQConfig;
import com.example.pizza.configuration.OrderStatusMQConfig;
import com.example.pizza.configuration.PizzaMQConfig;
import com.example.pizza.dto.IngredientDto;
import com.example.pizza.dto.OrderDto;
import com.example.pizza.dto.PizzaDto;
import com.example.pizza.service.PizzaService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OrderListener {

    private final PizzaService pizzaService;

    private final RabbitTemplate rabbitTemplate;

    private final ObjectMapper objectMapper;

    private final StockListener stockListener;

    @RabbitListener(queues = OrderMQConfig.ORDER_QUEUE)
    public void listenOrder(String message) throws JsonProcessingException, InterruptedException {
        OrderDto orderDto = convertMessageToOrder(message);
        PizzaDto pizzaDto = pizzaService.getPizzaByName(orderDto.getMealName());
        if (pizzaDto.getName() == null) {
            orderDto.setOrderStatus("Meal Not Found");
        } else {
            List<String> stocks = pizzaDto.getIngredients()
                    .stream()
                    .map(IngredientDto::getName)
                    .toList();
            sendMessageToStock(convertJsonString(stocks));
            Thread.sleep(3000);
            orderDto.setOrderStatus(stockListener.getStockStatus());
        }
        sendResponseToOrder(convertJsonString(orderDto));
    }

    public void sendMessageToStock(String message) {
        rabbitTemplate.convertAndSend(PizzaMQConfig.PIZZA_EXCHANGE, PizzaMQConfig.PIZZA_ROUTING_KEY, message);
    }

    public void sendResponseToOrder(String response) {
        rabbitTemplate.convertAndSend(OrderStatusMQConfig.ORDER_STATUS_EXCHANGE, OrderStatusMQConfig.ORDER_STATUS_ROUTING_KEY, response);
    }

    private String convertJsonString(Object object) throws JsonProcessingException {
        return objectMapper.writeValueAsString(object);
    }

    private OrderDto convertMessageToOrder(String message) throws JsonProcessingException {
        return objectMapper.readValue(message, OrderDto.class);
    }

}
