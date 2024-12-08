package com.example.coffee.controller.order;


import com.example.coffee.common.Result;
import com.example.coffee.model.order.OrderRequestDTO;
import com.example.coffee.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "Order API", description = "주문 관련 API")
@RequestMapping("/api")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @Operation(summary = "주문 단건 조회", description = "주문 단건 조회(상품ID포함) API 입니다. orderId와 함께 요청해주셔야 합니다." )
    @GetMapping("/orders/{orderId}")
    public Result getOrderById(@PathVariable Long orderId) {
        return orderService.getOrderById(orderId);
    }

    @GetMapping("/orders/members")
    @Operation(summary = "멤버별 주문 전체 조회", description = "멤버별 주문 전체 조회 API 입니다. 요청 파라미터는 없고 로그인 토큰과 함께 요청해주셔야 합니다." )
    public Result getOrderByMemberId(@AuthenticationPrincipal Long memberId) {
        return orderService.getOrderByMemberId(memberId);
    }


    @PostMapping("/orders")
    @Operation(summary = "주문 생성(=결제)", description = "주문 생성(=결제) API 입니다. 로그인 토큰과 함께 Request Body로 주문과 관련된 값들을 전부 입력해주셔야합니다." )
    public Result createOrder(@AuthenticationPrincipal Long memberId, @RequestBody OrderRequestDTO orderRequestDTO) {
        return orderService.createOrder(memberId, orderRequestDTO);
    }

    @PutMapping("/orders/{orderId}")
    @Operation(summary = "주문 취소", description = "주문 취소 API 입니다. orderId와 함께 요청해주셔야 하며, 14시가 지난 경우 14시 이전 주문은 취소가 불가능합니다." )
    public Result updateOrder(@PathVariable Long orderId) {
        return orderService.updateOrder(orderId);
    }

}


