package com.example.coffee.repository;


import com.example.coffee.model.cart.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface CartMapper {
    Long getCartIdByMemberId(Long userId);

    List<ViewCartListDTO> viewCartList(Long userId);

    ViewDetailsDTO getViewDetails(Long cartId);

    void cartProductAdd(Map<String, Object> params);

    void deleteCartList(Map<String, Object> params);

    void updateCartList(UpdateProductDetailsDTO updateDTO);

    String selectCartId(Long cartId);

    String selectMemberId(Long memberId);

    String getProductById(Long productId);

    int getPriceById(Long productId);

    int countProductByCartIdAndProductId(@Param("cartId") Long cartId, @Param("productId") Long prodId);

    String findProductInCart(FindProductInCartDTO to);

    void updateTotals(Long cartId);

    void createCart(Long userId);
}
