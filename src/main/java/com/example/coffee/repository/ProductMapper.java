package com.example.coffee.repository;

import com.example.coffee.model.product.ProductDTO;
import com.example.coffee.model.review.ReviewResponseDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ProductMapper {
    List<ProductDTO> findAll();
    ProductDTO findById(@Param("productId") Long productId);
    void insert(ProductDTO product);
    int update(ProductDTO product);
    int delete(@Param("productId") Long productId);

    List<ReviewResponseDTO> findReviewsByProductId(@Param("productId") Long productId); // 리뷰 조회
}
