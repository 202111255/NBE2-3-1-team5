package com.example.coffee.controller.order;


import com.example.coffee.common.Result;
import com.example.coffee.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
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

}


