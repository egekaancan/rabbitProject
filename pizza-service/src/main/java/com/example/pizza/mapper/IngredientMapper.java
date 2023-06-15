package com.example.pizza.mapper;

import com.example.pizza.dto.IngredientDto;
import com.example.pizza.entity.Ingredient;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IngredientMapper {

    Ingredient ingredientDtoToIngredient(IngredientDto ingredientDto);

    IngredientDto ingredientToIngredientDto(Ingredient ingredient);
}
