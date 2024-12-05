package com.example.coffee.model.cart;

import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

@Getter
@Setter
@Alias(value = "CartUpdateRequestDTO")
public class CartUpdateRequestDTO {
    private Long cartId;
    private Long productId;
    private Long quantity;
}
