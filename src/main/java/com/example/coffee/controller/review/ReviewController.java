package com.example.coffee.controller.review;


import com.example.coffee.common.Result;
import com.example.coffee.model.review.ReviewDTO;
import com.example.coffee.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/review")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @PostMapping
    public Result insertReview(@AuthenticationPrincipal Long userId, @RequestBody ReviewDTO reviewDTO){
        return reviewService.insertReview(userId, reviewDTO);
    }
}
