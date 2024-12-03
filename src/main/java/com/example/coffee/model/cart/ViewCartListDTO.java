package com.example.coffee.model.cart;

import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

@Getter
@Setter
@Alias( value = "ViewCartListDTO")
public class ViewCartListDTO {
    private Long cartItemId;
    private Long productId;
    private int quantity;
    private int price;
}