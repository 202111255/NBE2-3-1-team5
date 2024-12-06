package com.example.coffee.controller.review;


import com.example.coffee.common.Result;
import com.example.coffee.model.review.ReviewResponseDTO;
import com.example.coffee.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/review")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @PostMapping
    public Result insertReview(@AuthenticationPrincipal Long userId, @RequestBody ReviewResponseDTO reviewResponseDTO){
        return reviewService.insertReview(userId, reviewResponseDTO);
    }

    @PutMapping("/{reviewId}")
    public Result updateReview(@AuthenticationPrincipal Long userId, @PathVariable Long reviewId, @RequestBody ReviewResponseDTO reviewResponseDTO){
        return reviewService.updateReview(userId, reviewId, reviewResponseDTO);
    }

    @DeleteMapping("/{reviewId}")
    public Result deleteReview(@AuthenticationPrincipal Long userId, @PathVariable Long reviewId){
        return reviewService.deleteReview(userId, reviewId);
    }

}
