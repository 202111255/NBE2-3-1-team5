package com.example.coffee.controller.review;


import com.example.coffee.common.Result;
import com.example.coffee.model.review.ReviewDTO;
import com.example.coffee.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.relational.core.sql.In;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/review")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @PostMapping
    public Result insertReview(@AuthenticationPrincipal Long userId, @RequestBody ReviewDTO reviewDTO){
        return reviewService.insertReview(userId, reviewDTO);
    }

    @PutMapping("/{reviewId}")
    public Result updateReview(@AuthenticationPrincipal Long userId, @PathVariable Long reviewId, @RequestBody ReviewDTO reviewDTO){
        return reviewService.updateReview(userId, reviewId, reviewDTO);
    }

    @DeleteMapping("/{reviewId}")
    public Result deleteReview(@AuthenticationPrincipal Long userId, @PathVariable Long reviewId){
        return reviewService.deleteReview(userId, reviewId);
    }

}
