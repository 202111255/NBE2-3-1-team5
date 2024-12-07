package com.example.coffee.controller.cart;

import com.example.coffee.common.Result;
import com.example.coffee.model.cart.CartDeleteRequestDTO;
import com.example.coffee.model.cart.CartAddRequestDTO;

import com.example.coffee.model.cart.CartUpdateRequestDTO;
import com.example.coffee.service.CartService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CartController {
    @Autowired
    private CartService cartService;

    @Operation(summary = "장바구니 목록 조회", description = "장바구니 목록 조회 API 입니다. 요청항목 : 로그인 필수" )
    @GetMapping("/cart")
    public Result viewCartList(@AuthenticationPrincipal Long userId) {
        return cartService.viewCartList(userId);
    }

    @Operation(summary = "장바구니 목록 상품 추가", description = "장바구니 상품 추가 API 입니다. 요청항목 : 로그인 필수 / 상품(product) ID, 상품 개수" )
    @PostMapping("/cart/products")
    public Result addProductToCart(@AuthenticationPrincipal Long userId, @RequestBody CartAddRequestDTO request) {
        return cartService.cartProductAdd(userId, request);
    }

    @Operation(summary = "장바구니 생성", description = "장바구니 생성 API 입니다. 요청항목 : 로그인 필수 " )
    @PostMapping("/cart")
    public Result createCart(@AuthenticationPrincipal Long userId) {
        return cartService.createCart(userId);
    }

    @Operation(summary = "장바구니 상품 개수 변경", description = "장바구니 상품 개수 변경 API 입니다. 요청항목 : 로그인 필수 / 상품(product) ID, 상품 개수" )
    @PutMapping("/cart")
    public Result updateCartList(@AuthenticationPrincipal Long userId, @RequestBody CartUpdateRequestDTO request) {
        return cartService.updateCartList(userId, request);
    }

    @Operation(summary = "장바구니 상품 삭제", description = "장바구니에서 상품 빼기 API 입니다. 요청항목 : 로그인 필수 / 상품(product) ID" )
    @DeleteMapping("/cart")
    public Result deleteCartList(@AuthenticationPrincipal Long userId, @RequestParam Long productId) {
        return cartService.deleteCartList(userId, productId);
    }
}