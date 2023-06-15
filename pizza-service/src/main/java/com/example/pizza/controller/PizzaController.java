package com.example.pizza.controller;

import com.example.pizza.dto.PizzaDto;
import com.example.pizza.service.PizzaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pizzaApi")
@RequiredArgsConstructor
public class PizzaController {

    private final PizzaService pizzaService;

    @GetMapping("/getAll")
    public ResponseEntity<List<PizzaDto>> getAllPizzas() {
        return ResponseEntity.ok(pizzaService.getAllPizzas());
    }

    @GetMapping("/getPizza/id/{id}")
    public ResponseEntity<PizzaDto> getPizzaById(@PathVariable long id) {
        return ResponseEntity.ok(pizzaService.getPizzaById(id));
    }

    @GetMapping("/getPizza/name/{name}")
    public ResponseEntity<PizzaDto> getPizzaByName(@PathVariable String name) {
        return ResponseEntity.ok(pizzaService.getPizzaByName(name));
    }

    @PostMapping("/createPizza")
    public ResponseEntity<PizzaDto> createPizza(@RequestBody PizzaDto pizzaDto) {
        return ResponseEntity.ok(pizzaService.createPizza(pizzaDto));
    }

    @PutMapping("/updatePizza")
    public ResponseEntity<PizzaDto> updatePizza(@RequestBody PizzaDto pizzaDto){
        return ResponseEntity.ok(pizzaService.updatePizza(pizzaDto));
    }

    @DeleteMapping("/deletePizza/{id}")
    public ResponseEntity<String> deletePizza(@PathVariable Long id){
        return ResponseEntity.ok(pizzaService.deletePizza(id));
    }

}
