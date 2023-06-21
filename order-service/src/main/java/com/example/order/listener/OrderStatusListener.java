package com.example.order.listener;

import com.example.order.configuration.OrderStatusMQConfig;
import com.example.order.dto.OrderDto;
import com.example.order.mapper.OrderMapper;
import com.example.order.repository.OrderRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderStatusListener {

    private String orderStatus;

    private final ObjectMapper objectMapper;

    private final OrderMapper orderMapper;

    private final OrderRepository orderRepository;

    @RabbitListener(queues = OrderStatusMQConfig.ORDER_STATUS_QUEUE)
    public void listenOrderStatus(String message) throws JsonProcessingException {
        OrderDto orderDto = objectMapper.readValue(message, OrderDto.class);
        this.orderStatus = orderDto.getOrderStatus();
        orderRepository.save(orderMapper.orderDtoToOrder(orderDto));

    }

    public String getOrderStatus() {
        return orderStatus;
    }

}
