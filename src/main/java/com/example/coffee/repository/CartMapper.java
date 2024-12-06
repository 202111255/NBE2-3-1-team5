package com.example.coffee.repository;


import com.example.coffee.model.cart.*;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import java.util.Map;

@Mapper
public interface CartMapper {
    Long getCartIdByMemberId(Long userId);

    List<ViewCartListDTO> viewCartList(Long memberId);

    ViewDetailsDTO getViewDetails(Long cartId);

    void cartProductAdd(Map<String, Object> params);

    void deleteCartList(Map<String, Object> params);

    void updateCartList(UpdateProductDetailsDTO updateDTO);

    String selectCartId(Long cartId);

    String getProductById(Long productId);

    int getPriceById(Long productId);

    int countProductByCartIdAndProductId(CartAddRequestDTO request);

    String findProductInCart(FindProductInCartDTO to);

    void updateTotals(Long cartId);
}
