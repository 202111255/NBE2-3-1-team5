package com.example.coffee.service;

import com.example.coffee.common.Result;
import com.example.coffee.common.ResultCode;
import com.example.coffee.model.review.ReviewDTO;
import com.example.coffee.repository.ReviewMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@AllArgsConstructor
@Service
public class ReviewService {

    private final ReviewMapper reviewMapper;


    public Result insertReview(Long userId, ReviewDTO reviewDTO) {
        try {
            reviewDTO.setMemberId(userId);
            LocalDateTime now = LocalDateTime.now();
            reviewDTO.setCreatedAt(now);
            reviewDTO.setUpdatedAt(now);
            reviewMapper.insertReview(reviewDTO);

            return new Result(ResultCode.SUCCESS, reviewDTO);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new Result(ResultCode.DB_ERROR, "리뷰 등록 중 오류가 발생했습니다.");
        }
    }

    // 리뷰 수정
    public Result updateReview(Long userId, Long reviewId ,ReviewDTO reviewDTO){
        try {
            reviewDTO.setMemberId(userId);
            reviewDTO.setReviewId(reviewId);

            int flag = 0;
            flag = reviewMapper.updateReview(reviewDTO);

            if (flag==1){
                return new Result(ResultCode.SUCCESS);
            }else {
                return new Result(ResultCode.INVALID_TOKEN);
            }


        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new Result(ResultCode.DB_ERROR);
        }
    }

    // 리뷰 삭제
    public Result deleteReview(Long userId, Long reviewId){
        try {
            ReviewDTO reviewDTO = new ReviewDTO();
            reviewDTO.setMemberId(userId);
            reviewDTO.setReviewId(reviewId);

            int flag = 0;
            flag = reviewMapper.deleteReview(reviewDTO);

            if (flag==1){
                return new Result(ResultCode.SUCCESS);
            }else {
                return new Result(ResultCode.INVALID_TOKEN);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new Result(ResultCode.DB_ERROR);
        }

    }
}
