package com.example.coffee.controller.cart;

import com.example.coffee.common.Result;
import com.example.coffee.model.cart.CartDeleteRequestDTO;
import com.example.coffee.model.cart.CartAddRequestDTO;

import com.example.coffee.model.cart.CartUpdateRequestDTO;
import com.example.coffee.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CartController {
    @Autowired
    private CartService cartService;

    @GetMapping("/cart/{cartid}")
    public Result viewCartList(@PathVariable Long cartid) {
        return cartService.viewCartList(cartid);
    }

    @PostMapping("/cart")
    public Result addProductToCart(@RequestBody CartAddRequestDTO request) {
        return cartService.cartProductAdd(request);
    }

    @PutMapping("/cart")
    public Result updateCartList(@RequestBody CartUpdateRequestDTO request) {
        return cartService.updateCartList(request);
    }

    @DeleteMapping("/cart")
    public Result deleteCartList(@RequestBody CartDeleteRequestDTO request) {
        return cartService.deleteCartList(request);
    }
}