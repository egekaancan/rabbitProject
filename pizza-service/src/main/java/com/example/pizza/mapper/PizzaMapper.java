package com.example.pizza.mapper;


import com.example.pizza.dto.PizzaDto;
import com.example.pizza.entity.Pizza;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PizzaMapper {

    Pizza pizzaDtoToPizza(PizzaDto pizzaDto);

    PizzaDto pizzaToPizzaDto(Pizza pizza);
}
