package com.example.coffee.model.order;


import lombok.Getter;

@Getter
public class OrderListRequestDTO {
    private Long productId;
    private int quantity;
    private int price;
}
