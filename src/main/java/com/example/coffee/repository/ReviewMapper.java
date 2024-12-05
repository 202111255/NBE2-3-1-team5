package com.example.coffee.repository;

import com.example.coffee.model.review.ReviewDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ReviewMapper {

    void insertReview(ReviewDTO reviewDTO);
}
