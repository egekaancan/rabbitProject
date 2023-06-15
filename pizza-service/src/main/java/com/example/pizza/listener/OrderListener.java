package com.example.pizza.listener;


import com.example.pizza.configuration.OrderMQConfig;
import com.example.pizza.configuration.OrderStatusMQConfig;
import com.example.pizza.dto.PizzaDto;
import com.example.pizza.service.PizzaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderListener {

    private final PizzaService pizzaService;

    private final RabbitTemplate rabbitTemplate;

    private final ObjectMapper objectMapper;

    @RabbitListener(queues = OrderMQConfig.ORDER_QUEUE)
    public void listenOrder(String message) {
        String response;
        PizzaDto pizzaDto = pizzaService.getPizzaByName(message);
        if(pizzaDto.getName() == null) {
            response = "Meal Not Found";
        }else {
            response = "Proceeding";
        }
        sendResponse(response);

        //rabbitTemplate.convertAndSend(PizzaMQConfig.PIZZA_EXCHANGE, PizzaMQConfig.PIZZA_ROUTING_KEY, pizzaDto.getIngredients());
    }

    public void sendResponse(String response){
        rabbitTemplate.convertAndSend(OrderStatusMQConfig.ORDER_STATUS_EXCHANGE, OrderStatusMQConfig.ORDER_STATUS_ROUTING_KEY, response);

    }

}
