package com.example.coffee.service;

import com.example.coffee.common.Result;
import com.example.coffee.common.ResultCode;
import com.example.coffee.model.order.OrderByMemberResponseDTO;
import com.example.coffee.model.order.OrderRequestDTO;
import com.example.coffee.model.order.OrderResponseDTO;
import com.example.coffee.repository.OrderMapper;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import java.util.List;
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

    public Result getOrderByMemberId(Long memberId) {
        try {
            // 1. memberId 검증
            if (memberId == null || memberId <= 0) {
                return new Result(ResultCode.INVALID_PARAMETER);
            }

            // 2. 주문 조회
            List<OrderByMemberResponseDTO> order = orderMapper.getOrderByMemberId(memberId);

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


    //주문 POST
    public Result createOrder(OrderRequestDTO orderRequestDTO) {
        try {
            // 1. 입력 값 검증
            if (orderRequestDTO == null || orderRequestDTO.getOrderLists() == null || orderRequestDTO.getOrderLists().isEmpty()) {
                return new Result(ResultCode.INVALID_PARAMETER, "주문 정보가 유효하지 않습니다.");
            }

            // 2. Order 등록
            orderMapper.insertOrder(orderRequestDTO);

            // 3. 방금 삽입된 주문의 ID 가져오기
            Long orderId = orderMapper.getLastInsertedOrderId();

            if (orderId == null) {
                return new Result(ResultCode.DB_ERROR, "주문 ID 생성 실패");
            }

            // 4. Order Items 등록
            if (orderRequestDTO.getOrderLists() != null && !orderRequestDTO.getOrderLists().isEmpty()) {
                orderMapper.insertOrderItems(orderId, orderRequestDTO.getOrderLists());
            }

            // 5. 성공 결과 반환
            return new Result(ResultCode.SUCCESS, "주문 데이터 삽입 성공");

        } catch (DataAccessException dae) {
            // 데이터베이스 관련 예외 처리
            return new Result(ResultCode.DB_ERROR, "데이터베이스 오류 발생");

        } catch (Exception e) {
            // 시스템 관련 예외 처리
            return new Result(ResultCode.SYSTEM_ERROR, "시스템 오류 발생: " + e.getMessage());
        }
    }


}