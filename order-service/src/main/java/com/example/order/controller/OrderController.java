package com.example.order.controller;

import com.example.order.dto.OrderDto;
import com.example.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orderapi")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/all")
    public ResponseEntity<List<OrderDto>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAll());
    }


    @GetMapping("/orders/name/{mealName}")
    public ResponseEntity<List<OrderDto>> getOrdersByMealName(@PathVariable String mealName) {
        return ResponseEntity.ok(orderService.getOrdersByMealName(mealName));
    }

    @PostMapping("/orders")
    public void postOrders(@RequestBody List<String> meals) {
        orderService.postOrders(meals);
    }

}
