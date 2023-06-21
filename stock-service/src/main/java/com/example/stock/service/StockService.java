package com.example.stock.service;

import com.example.stock.dto.StockDto;
import com.example.stock.entity.Stock;
import com.example.stock.mapper.StockMapper;
import com.example.stock.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StockService {

    private final StockRepository stockRepository;

    private final StockMapper stockMapper;

    public List<StockDto> getAllStock() {
        return stockRepository.findAll()
                .stream()
                .map(stockMapper::stockToStockDto)
                .collect(Collectors.toList());
    }

    public StockDto getStockById(String id) {
        return stockMapper.stockToStockDto(stockRepository.findById(id).orElse(null));
    }

    public StockDto getStockByName(String name) {
        if(stockRepository.findByName(name) == null)
            return new StockDto();
        return stockMapper.stockToStockDto(stockRepository.findByName(name));
    }

    public StockDto createStock(StockDto stockDto) {
        Stock stock = stockMapper.stockDtoToStock(stockDto);
        stockRepository.insert(stock);
        return stockMapper.stockToStockDto(stock);
    }

    public StockDto updateStock(StockDto stockDto) {
        Stock stock = stockRepository.findById(stockDto.getId()).orElse(null);
        stock.setName(stockDto.getName());
        stock.setAmount(stockDto.getAmount());
        stockRepository.save(stock);
        return stockMapper.stockToStockDto(stock);
    }

    public String deleteStock(String id){
        Optional<Stock> stock = stockRepository.findById(id);
        if(stock.isPresent()) {
            stockRepository.deleteById(id);
            return "Stock is deleted";
        }
        return "Wrong id";
    }
}
