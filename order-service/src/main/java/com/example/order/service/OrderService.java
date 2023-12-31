package com.example.order.service;

import com.example.order.configuration.OrderMQConfig;
import com.example.order.dto.OrderDto;
import com.example.order.listener.OrderStatusListener;
import com.example.order.mapper.OrderMapper;
import com.example.order.repository.OrderRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {


    private final OrderRepository orderRepository;

    private final OrderMapper orderMapper;

    private final RabbitTemplate rabbitTemplate;

    private final ObjectMapper objectMapper;

    private final OrderStatusListener statusListener;

    public List<OrderDto> getAll() {
        return orderRepository.findAll()
                .stream()
                .map(orderMapper::orderToOrderDto)
                .collect(Collectors.toList());
    }

    public List<OrderDto> getOrdersByMealName(String mealName) {
        return orderRepository.findOrdersByMealName(mealName)
                .stream()
                .map(orderMapper::orderToOrderDto)
                .collect(Collectors.toList());
    }

    public void postOrders(List<String> meals) {
        List<OrderDto> orders = new ArrayList<>();
        meals.forEach(meal -> {
            OrderDto orderDto = new OrderDto();
            orderDto.setMealName(meal);
            orderDto.setOrderStatus("none");
            orders.add(orderDto);
        });
        orders.forEach(orderDto -> {
            try {
                String message = objectMapper.writeValueAsString(orderDto);
                rabbitTemplate.convertAndSend(OrderMQConfig.ORDER_EXCHANGE, OrderMQConfig.ORDER_ROUTING_KEY, message);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
