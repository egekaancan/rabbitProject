package com.example.stock.repository;

import com.example.stock.entity.Stock;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface StockRepository extends MongoRepository<Stock, String> {

    Stock findStockByName(String name);

}
