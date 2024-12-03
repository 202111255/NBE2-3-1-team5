package com.example.coffee.repository;

import com.example.coffee.model.order.OrderResponseDTO;
import com.example.coffee.model.user.CreateMemberDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface OrderMapper {
    OrderResponseDTO getOrderById(@Param("orderId") Long orderId);
    OrderResponseDTO getOrder(@Param("userId") Long userId);
}
