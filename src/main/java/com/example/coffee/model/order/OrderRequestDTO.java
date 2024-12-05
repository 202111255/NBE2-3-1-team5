package com.example.coffee.model.order;

import lombok.Getter;
import org.apache.ibatis.type.Alias;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class OrderRequestDTO {
    //private Long memberId;
    private String address;
    private String zipcode;
    private int totalQuantity;
    private double totalPrice;
    private List<OrderListRequestDTO> orderLists;
}
