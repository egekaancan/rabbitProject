package com.example.pizza.service;

import com.example.pizza.dto.IngredientDto;
import com.example.pizza.entity.Ingredient;
import com.example.pizza.mapper.IngredientMapper;
import com.example.pizza.repository.IngredientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class IngredientService {

    private final IngredientRepository ingredientRepository;

    private final IngredientMapper ingredientMapper;


    public List<IngredientDto> getAllIngredients() {
        return ingredientRepository.findAll()
                .stream()
                .map(ingredientMapper::ingredientToIngredientDto)
                .collect(Collectors.toList());
    }

    public IngredientDto getIngredientById(long id) {
        return ingredientMapper.ingredientToIngredientDto(ingredientRepository.findById(id).orElse(null));
    }

    public IngredientDto getIngredientByName(String name) {
        return ingredientMapper.ingredientToIngredientDto(ingredientRepository.findIngredientByName(name));
    }

    public IngredientDto createIngredient(IngredientDto ingredientDto) {
        Ingredient ingredient = ingredientRepository.save(ingredientMapper.ingredientDtoToIngredient(ingredientDto));
        return ingredientMapper.ingredientToIngredientDto(ingredient);
    }

    public IngredientDto updateIngredient(IngredientDto ingredientDto) {
        Ingredient ingredient = ingredientRepository.findById(ingredientDto.getId()).orElse(null);
        ingredient.setName(ingredientDto.getName());
        ingredientRepository.save(ingredient);
        return ingredientMapper.ingredientToIngredientDto(ingredient);
    }

    public String deleteIngredient(Long id) {
        ingredientRepository.findById(id).ifPresent(ingredientRepository::delete);
        return "Ingredient is deleted";
    }
}
