package com.example.coffee.model.cart;


import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

@Getter
@Setter
@Alias(value = "UpdateProductDetailsDTO")
public class UpdateProductDetailsDTO {
    private Long cartId;
    private Long productId;
    private int quantity;
    private int price;
}
