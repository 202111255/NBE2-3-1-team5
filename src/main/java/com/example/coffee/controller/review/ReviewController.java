package com.example.coffee.controller.review;


import com.example.coffee.common.Result;
import com.example.coffee.model.review.ReviewResponseDTO;

import com.example.coffee.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "Review API", description = "리뷰 관련 API")
@RequestMapping("/api/review")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;


    @Operation(summary = "리뷰 작성", description = "리뷰 작성 API 입니다. 로그인 필수 / 요청항목 : 상품(product)ID, 리뷰내용. " )
    public Result insertReview(@AuthenticationPrincipal Long userId, @RequestBody ReviewRequestDTO reviewRequestDTO){
        return reviewService.insertReview(userId, reviewRequestDTO);
    }

    @PutMapping("/{reviewId}")
    @Operation(summary = "리뷰 수정", description = "리뷰 수정 API 입니다. 로그인 필수 / 요청항목 : 리뷰(review)ID, 리뷰내용" )
    public Result updateReview(@AuthenticationPrincipal Long userId, @PathVariable Long reviewId, @RequestBody String content){
        return reviewService.updateReview(userId, reviewId, content);
    }

    @DeleteMapping("/{reviewId}")
    @Operation(summary = "리뷰 삭제", description = "리뷰 삭제 API 입니다. 로그인 필수 / 요청항목 : 리뷰(review)ID" )
    public Result deleteReview(@AuthenticationPrincipal Long userId, @PathVariable Long reviewId){
        return reviewService.deleteReview(userId, reviewId);
    }

}
