package com.example.order.mapper;


import com.example.order.dto.OrderDto;
import com.example.order.entity.Order;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    Order orderDtoToOrder(OrderDto orderDto);

    OrderDto orderToOrderDto(Order order);
}
