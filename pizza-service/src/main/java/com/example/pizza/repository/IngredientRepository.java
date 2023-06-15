package com.example.pizza.repository;

import com.example.pizza.entity.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {

    Ingredient findIngredientByName(String name);
}
