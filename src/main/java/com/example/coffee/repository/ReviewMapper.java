package com.example.coffee.repository;

import com.example.coffee.model.review.ReviewResponseDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ReviewMapper {

    void insertReview(ReviewResponseDTO reviewResponseDTO);

    int updateReview(ReviewResponseDTO reviewResponseDTO);
    int deleteReview(ReviewResponseDTO reviewResponseDTO);
}
