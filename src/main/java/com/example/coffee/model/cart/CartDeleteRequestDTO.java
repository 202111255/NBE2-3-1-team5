package com.example.coffee.model.cart;

import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

@Getter
@Setter
@Alias(value = "CartDeleteRequestDTO")
public class CartDeleteRequestDTO {
    private Long cartId;
    private Long productId;
}
