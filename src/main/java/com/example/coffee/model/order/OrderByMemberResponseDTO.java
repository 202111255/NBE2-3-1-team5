package com.example.coffee.model.order;

import lombok.Getter;
import org.apache.ibatis.type.Alias;

import java.time.LocalDateTime;

@Alias(value = "OrderByMember")
@Getter
public class OrderByMemberResponseDTO {
    private Long orderId;
    private Long memberId;
    private String address;
    private String zipcode;
    private int totalQuantity;
    private double totalPrice;
    private boolean isCancelled;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
