package com.example.coffee.model.cart;


import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

@Getter
@Setter
@Alias(value ="FindProductInCartDTO")
public class FindProductInCartDTO {
    private Long productId;
    private Long cartId;
}
