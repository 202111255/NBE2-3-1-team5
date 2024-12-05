package com.example.coffee.service;

import com.example.coffee.common.Result;
import com.example.coffee.common.ResultCode;
import com.example.coffee.model.cart.*;
import com.example.coffee.repository.CartMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class CartService {
    @Autowired
    private CartMapper cartMapper;

    public Result viewCartList(Long cartId) {
        // 잘못된 요청
        if (cartId == null || cartId <= 0) {
            return new Result(ResultCode.INVALID_PARAMETER);
        }

        // 해당 아이디 존재 확인
        if (cartMapper.selectCartId(cartId) == null) {
            return new Result(ResultCode.USER_NOT_FOUND);
        }

        // 목록 없음
        if (cartMapper.viewCartList(cartId).isEmpty()) {
            return new Result(ResultCode.CART_NOT_FOUND);
        }

        // 정상 입력
        return new Result(ResultCode.SUCCESS, cartMapper.viewCartList(cartId));
    }

    public Result cartProductAdd(CartAddRequestDTO request) {
        // 해당 아이디 존재 확인
        if (cartMapper.selectCartId(request.getCartId()) == null) {
            return new Result(ResultCode.USER_NOT_FOUND);
        }

        // 해당 상품 존재 확인
        if (cartMapper.getProductById(request.getProductId()) == null) {
            return new Result(ResultCode.PRODUCT_NOT_FOUND);
        }

        // 이미 추가된 상품인지 확인
        int count = cartMapper.countProductByCartIdAndProductId(request);
        if (count > 0) {
            return new Result(ResultCode.ITEM_ALREADY_IN_CART);
        }

        // 해당 상품 재고 확인
        if (cartMapper.getProductInventoryById(request.getProductId()) - request.getQuantity() < 0) {
            return new Result(ResultCode.OUT_OF_STOCK);
        }

        // 정상 입력
        Map<String, Object> params = new HashMap<>();
        params.put("cartId", request.getCartId());
        params.put("productId", request.getProductId());
        params.put("quantity", request.getQuantity());

        int price = (cartMapper.getPriceById(request.getProductId())) * request.getQuantity();
        params.put("price", price);

        cartMapper.cartProductAdd(params);
        return new Result(ResultCode.SUCCESS);
    }

    public Result updateCartList(CartUpdateRequestDTO request) {
        // 해당 아이디 존재 확인
        if (cartMapper.selectCartId(request.getCartId()) == null) {
            return new Result(ResultCode.USER_NOT_FOUND);
        }

        // 해당 아이디가 가지고 있는 상품인지 확인(X)
        if (cartMapper.getProductById(request.getProductId()) == null) {
            return new Result(ResultCode.PRODUCT_NOT_FOUND);
        }

        // 해당 상품 재고 없음
        if (cartMapper.getProductInventoryById(request.getProductId()) - request.getQuantity() < 0) {
            return new Result(ResultCode.OUT_OF_STOCK);
        }

        // 정상 입력
        CartUpdateRequestDTO to = new CartUpdateRequestDTO();
        to.setCartId(request.getCartId());
        to.setProductId(request.getProductId());
        to.setQuantity(request.getQuantity());
        cartMapper.updateCartList(to);
        return new Result(ResultCode.SUCCESS);
    }

    public Result deleteCartList(CartDeleteRequestDTO request) {
        // 해당 아이디 없음
        if (cartMapper.selectCartId(request.getCartId()) == null) {
            return new Result(ResultCode.USER_NOT_FOUND);
        }

        // 해당 아이디가 가지고 있는 상품인지 확인(X)
        if (cartMapper.getProductById(request.getProductId()) == null) {
            return new Result(ResultCode.PRODUCT_NOT_FOUND);
        }

        // 정상 입력
        cartMapper.deleteCartList(request);
        return new Result(ResultCode.SUCCESS);
    }
}
