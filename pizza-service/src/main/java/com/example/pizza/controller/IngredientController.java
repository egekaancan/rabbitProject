package com.example.pizza.controller;

import com.example.pizza.dto.IngredientDto;
import com.example.pizza.dto.PizzaDto;
import com.example.pizza.service.IngredientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ingredientapi")
@RequiredArgsConstructor
public class IngredientController {

    private final IngredientService ingredientService;

    @GetMapping("/all")
    public ResponseEntity<List<IngredientDto>> getAllIngredients() {
        return ResponseEntity.ok(ingredientService.getAllIngredients());
    }

    @GetMapping("/ingredient/id/{id}")
    public ResponseEntity<IngredientDto> getIngredientById(@PathVariable long id) {
        return ResponseEntity.ok(ingredientService.getIngredientById(id));
    }

    @GetMapping("/ingredient/name/{name}")
    public ResponseEntity<IngredientDto> getIngredientByName(@PathVariable String name) {
        return ResponseEntity.ok(ingredientService.getIngredientByName(name));
    }

    @PostMapping("/ingredient")
    public ResponseEntity<IngredientDto> createIngredient(@RequestBody IngredientDto ingredientDto) {
        return ResponseEntity.ok(ingredientService.createIngredient(ingredientDto));
    }

    @PutMapping("/ingredient")
    public ResponseEntity<IngredientDto> updateIngredient(@RequestBody IngredientDto ingredientDto){
        return ResponseEntity.ok(ingredientService.updateIngredient(ingredientDto));
    }

    @DeleteMapping("/ingredient/{id}")
    public ResponseEntity<String> deleteIngredient(@PathVariable Long id){
        return ResponseEntity.ok(ingredientService.deleteIngredient(id));
    }

}
