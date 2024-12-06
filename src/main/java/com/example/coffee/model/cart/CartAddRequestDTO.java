package com.example.coffee.model.cart;


import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

@Getter
@Setter
@Alias(value = "CartAddRequestDTO")
public class CartAddRequestDTO {
    private Long productId;
    private int quantity;
}