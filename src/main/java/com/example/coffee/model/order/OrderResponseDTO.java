package com.example.coffee.model.order;

import lombok.Getter;
import org.apache.ibatis.type.Alias;

import java.time.LocalDateTime;
import java.util.List;

@Alias(value = "order")
@Getter
public class OrderResponseDTO {
    private Long orderId;
    private Long memberId;
    private String address;
    private String zipcode;

    //orderList 불러오기
    private List<OrderListResponseDTO> orderListResponseDTOS;

    private int totalQuantity;
    private double totalPrice;
    private boolean isCancelled;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
