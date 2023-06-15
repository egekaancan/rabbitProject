package com.example.pizza.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PizzaDto {

    private Long id;

    private String name;

    private Set<IngredientDto> ingredients;
}
