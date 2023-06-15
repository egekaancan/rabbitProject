package com.example.pizza.service;

import com.example.pizza.dto.PizzaDto;
import com.example.pizza.entity.Pizza;
import com.example.pizza.mapper.PizzaMapper;
import com.example.pizza.repository.PizzaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class PizzaService {

    private final PizzaRepository pizzaRepository;

    private final PizzaMapper pizzaMapper;


    public List<PizzaDto> getAllPizzas() {
        return pizzaRepository.findAll()
                .stream()
                .map(pizzaMapper::pizzaToPizzaDto)
                .collect(Collectors.toList());
    }

    public PizzaDto getPizzaById(long id) {
        return pizzaMapper.pizzaToPizzaDto(pizzaRepository.findById(id).orElse(null));
    }

    public PizzaDto getPizzaByName(String name) {
        PizzaDto pizzaDto = pizzaMapper.pizzaToPizzaDto(pizzaRepository.findPizzaByName(name));
        if(pizzaDto == null){
            return new PizzaDto();
        }
        return pizzaDto;
    }

    public PizzaDto createPizza(PizzaDto pizzaDto) {
        Pizza pizza = pizzaRepository.save(pizzaMapper.pizzaDtoToPizza(pizzaDto));
        return pizzaMapper.pizzaToPizzaDto(pizza);
    }

    public PizzaDto updatePizza(PizzaDto pizzaDto) {
        Pizza updatedPizza = pizzaMapper.pizzaDtoToPizza(pizzaDto);
        Pizza pizza = pizzaRepository.findById(pizzaDto.getId()).orElse(null);
        pizza.setName(updatedPizza.getName());
        pizza.setIngredients(updatedPizza.getIngredients());
        pizzaRepository.save(pizza);
        return pizzaMapper.pizzaToPizzaDto(pizza);
    }

    public String deletePizza(Long id) {
        pizzaRepository.findById(id).ifPresent(pizzaRepository::delete);
        return "Pizza is deleted";
    }

}
