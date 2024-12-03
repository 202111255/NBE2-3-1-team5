package com.example.coffee.repository;


import com.example.coffee.model.cart.CartListDTO;
import com.example.coffee.model.cart.CartDTO;
import com.example.coffee.model.cart.ViewCartListDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;

@Mapper
public interface CartMapper {
    ArrayList<ViewCartListDTO> viewCartList(CartDTO to);
    void addProductToCart(CartListDTO to);

    void deleteCartList(CartListDTO to);

    void updateCartList(CartListDTO to);
}
