package com.example.pizza.repository;

import com.example.pizza.entity.Pizza;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PizzaRepository extends JpaRepository<Pizza, Long> {

    Pizza findPizzaByName(String name);
}
