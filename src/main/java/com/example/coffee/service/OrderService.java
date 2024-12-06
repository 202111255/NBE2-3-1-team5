package com.example.coffee.service;

import com.example.coffee.common.Result;
import com.example.coffee.common.ResultCode;
import com.example.coffee.model.order.OrderByMemberResponseDTO;
import com.example.coffee.model.order.OrderRequestDTO;
import com.example.coffee.model.order.OrderResponseDTO;
import com.example.coffee.model.user.RequestMemberDTO;
import com.example.coffee.repository.MemberMapper;
import com.example.coffee.repository.OrderMapper;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Service
public class OrderService {
    private final OrderMapper orderMapper;
    private final MemberMapper memberMapper;

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

//    public Result getOrderByMemberId(Long memberId) {
//        try {
//            // 1. memberId 검증
//            if (memberId == null || memberId <= 0) {
//                return new Result(ResultCode.INVALID_PARAMETER);
//            }
//
//            // 2. 주문 조회
//            List<OrderByMemberResponseDTO> order = orderMapper.getOrderByMemberId(memberId);
//
//            // 3. 조회 결과 검증
//            if (order == null) {
//                return new Result(ResultCode.ORDER_NOT_FOUND);
//            }
//
//            // 4. 정상 결과 반환
//            return new Result(ResultCode.SUCCESS, order);
//
//        } catch (DataAccessException dae) {
//            // 5. 데이터베이스 관련 예외 처리
//            return new Result(ResultCode.DB_ERROR);
//
//        } catch (Exception e) {
//            // 6. 일반적인 시스템 예외 처리
//            return new Result(ResultCode.SYSTEM_ERROR);
//        }
//    }

    public Result getOrderByMemberId(Long memberId) {
        try {
            // 1. memberId 가져오기
            RequestMemberDTO member = memberMapper.userInfo(memberId);
            Long Id = member.getMemberId();

            // 2. 주문 조회
            List<OrderByMemberResponseDTO> order = orderMapper.getOrderByMemberId(Id);

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
    public Result createOrder(Long memberId, OrderRequestDTO orderRequestDTO) {
        try {
            // 0. 입력 값 검증
            if (orderRequestDTO == null || orderRequestDTO.getOrderLists() == null || orderRequestDTO.getOrderLists().isEmpty()) {
                return new Result(ResultCode.INVALID_PARAMETER, "주문 정보가 유효하지 않습니다.");
            }

            // 1. memberId 가져오기
            RequestMemberDTO member = memberMapper.userInfo(memberId);
            Long Id = member.getMemberId();

            // 2. Order 등록
            orderMapper.insertOrder(Id, orderRequestDTO);

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


    public Result updateOrder(Long orderId) {
        try {
            if (orderId == null || orderId <= 0) {
                return new Result(ResultCode.INVALID_PARAMETER);
            }

            //주문 있는지 가져오기
            OrderResponseDTO order = orderMapper.getOrderById(orderId);
            if (order == null) {
                return new Result(ResultCode.ORDER_NOT_FOUND);
            }

            //이미 주문 취소된 건인지 확인
            if (order.isCancelled()) {
                return new Result(ResultCode.ORDER_ALREADY_CANCELLED);
            }

            LocalDateTime now = LocalDateTime.now();

//            System.out.println("생성시간" + order.getCreatedAt().toString());
//            System.out.println("현재시간" + now.toString());

            int order_d = Integer.parseInt(order.getCreatedAt().toString().substring(8, 10));
            int order_h = Integer.parseInt(order.getCreatedAt().toString().substring(11, 13));

            int now_d = Integer.parseInt(now.toString().substring(8, 10));
            int now_h = Integer.parseInt(now.toString().substring(11, 13));

            //2일 이상 전의 주문일 경우 취소 불가
            if (now_d-order_d > 1) {
                return new Result(ResultCode.FAIL_TO_CANCEL_ORDER);
            }
            //어제 14시 이후 주문일 경우 오늘 14시가 넘으면 취소 불가
            if (now_d-order_d == 1) { //어제 주문
                if (order_h < 14) { //어제 14시 전 주문
                    return new Result(ResultCode.FAIL_TO_CANCEL_ORDER);
                }
                if (now_h > 14) { //어제 14시 이후 주문이지만 오늘 14시가 지났을 경우
                    return new Result(ResultCode.FAIL_TO_CANCEL_ORDER);
                }
            }
            //오늘 14시 이전 주문일 경우 오늘 14시가 넘으면 취소 불가
            if (now_d-order_d == 0) { //오늘 주문
                if (order_h < 14 && now_h > 14) { //오늘 14시 전 주문인데 현재 14시가 지났을 경우
                    return new Result(ResultCode.FAIL_TO_CANCEL_ORDER);
                }
            }
            //취소 가능한 경우
            orderMapper.updateOrder(orderId);

            return new Result(ResultCode.SUCCESS, "주문 취소 완료");

        } catch (DataAccessException dae) {
            System.out.println("error :" + dae.getMessage());
            return new Result(ResultCode.DB_ERROR);

        } catch (Exception e) {
            System.out.println("error :" + e.getMessage());
            return new Result(ResultCode.SYSTEM_ERROR);
        }
    }


}