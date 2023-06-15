package com.example.stock.controller;

import com.example.stock.dto.StockDto;
import com.example.stock.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stockApi")
@RequiredArgsConstructor
public class StockController {

    private final StockService stockService;

    @GetMapping("/getAll")
    public ResponseEntity<List<StockDto>> getAllStock() {
        return ResponseEntity.ok(stockService.getAllStock());
    }

    @GetMapping("/getStock/id/{id}")
    public ResponseEntity<StockDto> getStockById(@PathVariable String id){
        return ResponseEntity.ok(stockService.getStockById(id));
    }

    @GetMapping("/getStock/name/{name}")
    public ResponseEntity<StockDto> getStockByName(@PathVariable String name) {
        return ResponseEntity.ok(stockService.getStockByName(name));
    }

    @PostMapping("/createStock")
    public ResponseEntity<StockDto> createStock(@RequestBody StockDto stockDto) {
        return ResponseEntity.ok(stockService.createStock(stockDto));
    }

    @PutMapping("/updateStock")
    public ResponseEntity<StockDto> updateStock(@RequestBody StockDto stockDto) {
        return ResponseEntity.ok(stockService.updateStock(stockDto));
    }

    @DeleteMapping("/deleteStock/{id}")
    public ResponseEntity<String> deleteStock(@PathVariable String id) {
        return ResponseEntity.ok(stockService.deleteStock(id));
    }
}
