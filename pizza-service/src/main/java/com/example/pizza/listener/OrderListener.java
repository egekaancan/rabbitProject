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
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderListener {

    private String stockMessage;

    private final PizzaService pizzaService;

    private final RabbitTemplate rabbitTemplate;

    private final ObjectMapper objectMapper;

    @RabbitListener(queues = OrderMQConfig.ORDER_QUEUE)
    public void listenOrder(String message) throws JsonProcessingException {
        OrderDto orderDto = convertMessageToOrder(message);
        PizzaDto pizzaDto = pizzaService.getPizzaByName(orderDto.getMealName());
        if (pizzaDto.getName() == null) {
            orderDto.setOrderStatus("Meal Not Found");
        } else {
            sendMessageToStock(convertJsonString(pizzaDto.getIngredients()));
            if (getStockMessage().equals("no"))
                orderDto.setOrderStatus("No Stock");
            else
                orderDto.setOrderStatus("Confirmed");
        }
        sendResponseToOrder(convertJsonString(orderDto));
    }

    @RabbitListener(queues = PizzaMQConfig.PIZZA_QUEUE)
    public void listenStock(String message) {
        stockMessage = message;
    }

    public void sendResponseToOrder(String response) {
        rabbitTemplate.convertAndSend(OrderStatusMQConfig.ORDER_STATUS_EXCHANGE, OrderStatusMQConfig.ORDER_STATUS_ROUTING_KEY, response);
    }

    public void sendMessageToStock(String message) {
        rabbitTemplate.convertAndSend(PizzaMQConfig.PIZZA_EXCHANGE, PizzaMQConfig.PIZZA_ROUTING_KEY, message);
    }

    private String convertJsonString(Object object) throws JsonProcessingException {
        return objectMapper.writeValueAsString(object);
    }

    private OrderDto convertMessageToOrder(String message) throws JsonProcessingException {
        return objectMapper.readValue(message, OrderDto.class);
    }

    private String getStockMessage() {
        return stockMessage;
    }
}
