package com.example.coffee.model.order;

import lombok.Getter;
import org.apache.ibatis.type.Alias;

import java.util.List;

@Alias(value = "allOrder")
@Getter
public class OrderResponseListDTO {
    private List<OrderListResponseDTO> orderResponseListDTOS;
}
