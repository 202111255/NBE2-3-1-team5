package com.example.coffee.model.cart;

import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;
import java.time.LocalDateTime;

@Alias(value = "ViewDetailsDTO")
@Setter
@Getter
public class ViewDetailsDTO {
    private int totalQuantity;
    private int totalPrice;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
