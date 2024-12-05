package com.example.coffee.repository;

import com.example.coffee.model.order.OrderByMemberResponseDTO;
import com.example.coffee.model.order.OrderListRequestDTO;
import com.example.coffee.model.order.OrderRequestDTO;
import com.example.coffee.model.order.OrderResponseDTO;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OrderMapper {
    //한건의 orderId로 주문 및 아이템 조회
    OrderResponseDTO getOrderById(@Param("orderId") Long orderId);

    //해당 유저의 모든 주문조회
    List<OrderByMemberResponseDTO> getOrderByMemberId(@Param("memberId") Long memberId);

    //주문 등록
    void insertOrder(OrderRequestDTO orderRequestDTO);

    // 마지막 삽입된 Order ID 조회
    Long getLastInsertedOrderId();

    //주문 리스트 등록
    //void insertOrderItems(List<OrderListRequestDTO> orderListRequestDTO);
    void insertOrderItems(@Param("orderId") Long orderId, @Param("orderLists") List<OrderListRequestDTO> orderLists);
}
