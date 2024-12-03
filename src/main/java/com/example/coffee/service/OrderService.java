package com.example.coffee.service;

import com.example.coffee.common.Result;
import com.example.coffee.common.ResultCode;
import com.example.coffee.model.order.OrderResponseDTO;
import com.example.coffee.model.user.CreateMemberDTO;
import com.example.coffee.repository.OrderMapper;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.Map;

@AllArgsConstructor
@Service
public class OrderService {
    private final OrderMapper orderMapper;

//    public Result getOrderById(Long orderId) {
//        return new Result(ResultCode.SUCCESS, orderMapper.getOrderById(orderId));
//    }

    public Result getOrderById(Long orderId) {
        try {
            // 1. orderId 검증
            if (orderId == null || orderId <= 0) {
                return new Result(ResultCode.INVALID_PARAMETER);
            }

            // 2. 주문 조회
            OrderResponseDTO order = orderMapper.getOrderById(orderId);

            // 3. 조회 결과 검증
            if (order == null) {
                return new Result(ResultCode.ORDER_NOT_FOUND);
            }

            // 4. 정상 결과 반환
            return new Result(ResultCode.SUCCESS, order);

        } catch (DataAccessException dae) {
            // 5. 데이터베이스 관련 예외 처리
            return new Result(ResultCode.DB_ERROR);

        } catch (Exception e) {
            // 6. 일반적인 시스템 예외 처리
            return new Result(ResultCode.SYSTEM_ERROR);
        }
    }
}