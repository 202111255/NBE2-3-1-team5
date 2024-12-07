package com.example.coffee.repository;

import com.example.coffee.model.review.ReviewResponseDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ReviewMapper {

    void insertReview(ReviewResponseDTO reviewResponseDTO);

    int updateReview(ReviewResponseDTO reviewResponseDTO);
    int deleteReview(ReviewResponseDTO reviewResponseDTO);

    // 상품 관련 리뷰 삭제
    int deleteReviewsByProductId(@Param("productId") Long productId);
}
