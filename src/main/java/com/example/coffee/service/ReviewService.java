package com.example.coffee.service;

import com.example.coffee.common.Result;
import com.example.coffee.common.ResultCode;
import com.example.coffee.model.review.ReviewRequestDTO;
import com.example.coffee.model.review.ReviewResponseDTO;
import com.example.coffee.repository.ReviewMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class ReviewService {

    private final ReviewMapper reviewMapper;


    public Result insertReview(Long userId, ReviewRequestDTO reviewRequestDTO) {
        try {
            ReviewResponseDTO reviewResponseDTO = new ReviewResponseDTO();
            reviewResponseDTO.setMemberId(userId);
            reviewResponseDTO.setProductId(reviewRequestDTO.getProductId());
            reviewResponseDTO.setContent(reviewRequestDTO.getContent());
            reviewMapper.insertReview(reviewResponseDTO);

            return new Result(ResultCode.SUCCESS, reviewRequestDTO);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new Result(ResultCode.DB_ERROR, "리뷰 등록 중 오류가 발생했습니다.");
        }
    }

    // 리뷰 수정
    public Result updateReview(Long userId, Long reviewId , String content){
        try {
            ReviewResponseDTO reviewResponseDTO = new ReviewResponseDTO();
            reviewResponseDTO.setMemberId(userId);
            reviewResponseDTO.setReviewId(reviewId);
            reviewResponseDTO.setContent(content);

            int flag = 0;
            flag = reviewMapper.updateReview(reviewResponseDTO);

            if (flag==1){
                return new Result(ResultCode.SUCCESS, "수정된 reviewId" + reviewId);
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
            ReviewResponseDTO reviewResponseDTO = new ReviewResponseDTO();
            reviewResponseDTO.setMemberId(userId);
            reviewResponseDTO.setReviewId(reviewId);

            int flag = 0;
            flag = reviewMapper.deleteReview(reviewResponseDTO);

            if (flag==1){
                return new Result(ResultCode.SUCCESS,"삭제된 reviewId" + reviewId);
            }else {
                return new Result(ResultCode.INVALID_TOKEN);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new Result(ResultCode.DB_ERROR);
        }

    }
}
