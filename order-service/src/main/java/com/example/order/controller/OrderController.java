package com.example.order.controller;

import com.example.order.dto.OrderDto;
import com.example.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orderApi")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/getAll")
    public ResponseEntity<List<OrderDto>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAll());
    }


    @GetMapping("/getOrders/name/{mealName}")
    public ResponseEntity<List<OrderDto>> getOrdersByMealName(@PathVariable String mealName) {
        return ResponseEntity.ok(orderService.getOrdersByMealName(mealName));
    }

    @PostMapping("/giveOrders")
    public ResponseEntity<List<OrderDto>> postOrders(@RequestBody List<String> meals) {
        return ResponseEntity.ok(orderService.postOrders(meals));
    }

}
