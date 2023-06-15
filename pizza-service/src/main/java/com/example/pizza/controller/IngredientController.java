package com.example.pizza.controller;

import com.example.pizza.dto.IngredientDto;
import com.example.pizza.dto.PizzaDto;
import com.example.pizza.service.IngredientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ingredientApi")
@RequiredArgsConstructor
public class IngredientController {

    private final IngredientService ingredientService;

    @GetMapping("/getAll")
    public ResponseEntity<List<IngredientDto>> getAllIngredients() {
        return ResponseEntity.ok(ingredientService.getAllIngredients());
    }

    @GetMapping("/getIngredient/id/{id}")
    public ResponseEntity<IngredientDto> getIngredientById(@PathVariable long id) {
        return ResponseEntity.ok(ingredientService.getIngredientById(id));
    }

    @GetMapping("/getIngredient/name/{name}")
    public ResponseEntity<IngredientDto> getIngredientByName(@PathVariable String name) {
        return ResponseEntity.ok(ingredientService.getIngredientByName(name));
    }

    @PostMapping("/createIngredient")
    public ResponseEntity<IngredientDto> createIngredient(@RequestBody IngredientDto ingredientDto) {
        return ResponseEntity.ok(ingredientService.createIngredient(ingredientDto));
    }

    @PutMapping("/updateIngredient")
    public ResponseEntity<IngredientDto> updateIngredient(@RequestBody IngredientDto ingredientDto){
        return ResponseEntity.ok(ingredientService.updateIngredient(ingredientDto));
    }

    @DeleteMapping("/deleteIngredient/{id}")
    public ResponseEntity<String> deleteIngredient(@PathVariable Long id){
        return ResponseEntity.ok(ingredientService.deleteIngredient(id));
    }

}
