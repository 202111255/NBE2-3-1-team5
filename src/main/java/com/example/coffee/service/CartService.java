package com.example.coffee.service;

import com.example.coffee.common.Result;
import com.example.coffee.common.ResultCode;
import com.example.coffee.model.cart.CartListDTO;
import com.example.coffee.model.cart.CartDTO;
import com.example.coffee.model.cart.ViewCartListDTO;
import com.example.coffee.repository.CartMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public class CartService {
    @Autowired
    private CartMapper cartMapper;

    public Result viewCartList(CartDTO to) {
        return new Result(ResultCode.SUCCESS, cartMapper.viewCartList(to));
    }

    public Result addProductToCart(CartListDTO to) {
        cartMapper.addProductToCart(to);
        return new Result(ResultCode.SUCCESS);
    }

    public Result deleteCartList(CartListDTO to) {
        cartMapper.deleteCartList(to);
        return new Result(ResultCode.SUCCESS);
    }

    public Result updateCartList(CartListDTO to) {
        cartMapper.updateCartList(to);
        return new Result(ResultCode.SUCCESS);
    }
}
