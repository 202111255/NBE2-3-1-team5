package com.example.coffee.model.cart;

import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

@Getter
@Setter
@Alias( value = "CartViewRequestDTO")
public class CartViewRequestDTO {
    private Long cartItemId;
    private Long productId;
    private int quantity;
    private int price;
}