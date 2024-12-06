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

    @GetMapping("/cart")
    public Result viewCartList(@AuthenticationPrincipal Long userId) {
        return cartService.viewCartList(userId);
    }

    @PostMapping("/cart/products")
    public Result addProductToCart(@AuthenticationPrincipal Long userId, @RequestBody CartAddRequestDTO request) {
        return cartService.cartProductAdd(userId, request);
    }

    @PostMapping("/cart")
    public Result createCart(@AuthenticationPrincipal Long userId) {
        return cartService.createCart(userId);
    }

    @PutMapping("/cart")
    public Result updateCartList(@AuthenticationPrincipal Long userId, @RequestBody CartUpdateRequestDTO request) {
        return cartService.updateCartList(userId, request);
    }


    @DeleteMapping("/cart")
    public Result deleteCartList(@AuthenticationPrincipal Long userId, @RequestParam Long productId) {
        return cartService.deleteCartList(userId, productId);
    }
}