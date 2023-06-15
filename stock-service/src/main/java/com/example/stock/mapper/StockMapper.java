package com.example.stock.mapper;

import com.example.stock.dto.StockDto;
import com.example.stock.entity.Stock;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StockMapper {

    Stock stockDtoToStock(StockDto stockDto);

    StockDto stockToStockDto(Stock stock);
}
