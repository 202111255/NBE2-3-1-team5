package com.example.coffee.model.cart;

import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

import java.util.Date;

@Getter
@Setter
@Alias( value = "CartDTO")
public class CartDTO {
    private Long cartId;
    private Long memberId;
    private int totalQuantity;
    private int totalPrice;
    private Date createdAt;
    private Date updatedAt;
}
