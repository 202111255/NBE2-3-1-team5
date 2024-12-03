package com.example.coffee.model.order;

import lombok.Getter;
import org.apache.ibatis.type.Alias;


@Alias(value = "orderList")
@Getter
public class OrderListResponseDTO {
    private Long orderItemId;
    private Long productId;
    private Long orderId;
    private int quantity;
    private double price;
}
