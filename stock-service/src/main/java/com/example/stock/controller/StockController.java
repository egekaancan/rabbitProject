package com.example.stock.controller;

import com.example.stock.dto.StockDto;
import com.example.stock.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stockapi")
@RequiredArgsConstructor
public class StockController {

    private final StockService stockService;

    @GetMapping("/all")
    public ResponseEntity<List<StockDto>> getAllStock() {
        return ResponseEntity.ok(stockService.getAllStock());
    }

    @GetMapping("/stock/id/{id}")
    public ResponseEntity<StockDto> getStockById(@PathVariable String id){
        return ResponseEntity.ok(stockService.getStockById(id));
    }

    @GetMapping("/stock/name/{name}")
    public ResponseEntity<StockDto> getStockByName(@PathVariable String name) {
        return ResponseEntity.ok(stockService.getStockByName(name));
    }

    @PostMapping("/stock")
    public ResponseEntity<StockDto> createStock(@RequestBody StockDto stockDto) {
        return ResponseEntity.ok(stockService.createStock(stockDto));
    }

    @PutMapping("/stock")
    public ResponseEntity<StockDto> updateStock(@RequestBody StockDto stockDto) {
        return ResponseEntity.ok(stockService.updateStock(stockDto));
    }

    @DeleteMapping("/stock/{id}")
    public ResponseEntity<String> deleteStock(@PathVariable String id) {
        return ResponseEntity.ok(stockService.deleteStock(id));
    }
}
