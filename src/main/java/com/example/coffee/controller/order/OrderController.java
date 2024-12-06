package com.example.coffee.controller.order;


import com.example.coffee.common.Result;
import com.example.coffee.model.order.OrderRequestDTO;
import com.example.coffee.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class OrderController {
    @Autowired
    private OrderService orderService;


    @GetMapping("/orders/{orderId}")
    public Result getOrderById(@PathVariable Long orderId) {
        return orderService.getOrderById(orderId);
    }

    @GetMapping("/orders/members")
    public Result getOrderByMemberId(@AuthenticationPrincipal Long memberId) {
        return orderService.getOrderByMemberId(memberId);
    }


    @PostMapping("/orders")
    public Result createOrder(@AuthenticationPrincipal Long memberId, @RequestBody OrderRequestDTO orderRequestDTO) {
        return orderService.createOrder(memberId, orderRequestDTO);
    }

    @PutMapping("/orders/{orderId}")
    public Result updateOrder(@PathVariable Long orderId) {
        return orderService.updateOrder(orderId);
    }

}


