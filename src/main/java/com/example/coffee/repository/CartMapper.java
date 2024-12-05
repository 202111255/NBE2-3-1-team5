package com.example.coffee.repository;


import com.example.coffee.model.cart.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;
import java.util.Map;

@Mapper
public interface CartMapper {
    ArrayList<CartViewRequestDTO> viewCartList(Long cartId);

    void cartProductAdd(Map<String, Object> params);

    void deleteCartList(CartDeleteRequestDTO request);

    void updateCartList(CartUpdateRequestDTO request);

    String selectCartId(Long cartId);

    String getProductById(Long productId);

    int getProductInventoryById(Long productId);

    int getPriceById(Long productId);

    int countProductByCartIdAndProductId(CartAddRequestDTO request);
}
