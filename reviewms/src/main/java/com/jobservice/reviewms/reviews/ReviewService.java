package com.jobservice.reviewms.reviews;

import java.util.List;

import org.springframework.http.HttpStatus;

public interface ReviewService {

    List<Review> findall(Long companyId);

    HttpStatus addReview(Long companyId, Review review);

    Review getReview(Long reviewId);

    HttpStatus updateReview(Long reviewId, Review review);

    HttpStatus deleteReview(Long reviewId);
}
