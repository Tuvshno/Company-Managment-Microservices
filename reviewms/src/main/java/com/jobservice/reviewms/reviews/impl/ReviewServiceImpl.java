package com.jobservice.reviewms.reviews.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.jobservice.reviewms.reviews.Review;
import com.jobservice.reviewms.reviews.ReviewRepository;
import com.jobservice.reviewms.reviews.ReviewService;


@Service
public class ReviewServiceImpl implements ReviewService {

    ReviewRepository reviewRepository;

    public ReviewServiceImpl(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @Override
    public List<Review> findall(Long companyId) {
        return reviewRepository.findByCompanyId(companyId);
    }

    @Override
    public HttpStatus addReview(Long companyId, Review review) {
        try {
            review.setCompanyId(companyId);
            reviewRepository.save(review);
            return HttpStatus.CREATED;
        } catch (Exception e) {
            return HttpStatus.NOT_ACCEPTABLE;
        }
    }

    @Override
    public Review getReview(Long reviewId) {
        Optional<Review> review = reviewRepository.findById(reviewId);
        if (review.isPresent()) {
            return review.get();
        }

        return null;
    }

    @Override
    public HttpStatus updateReview(Long reviewId, Review review) {
        try {
            Optional<Review> existing = reviewRepository.findById(reviewId);
            if (existing.isPresent()) {
                Review existingReview = existing.get();
                existingReview.setStars(review.getStars());
                reviewRepository.save(existingReview);
            }

            return HttpStatus.OK;

        } catch (Exception e) {
            return HttpStatus.NOT_FOUND;

        }
    }

    @Override
    public HttpStatus deleteReview(Long reviewId) {
        try {
            reviewRepository.deleteById(reviewId);
            return HttpStatus.ACCEPTED;
        } catch (Exception e) {
            return HttpStatus.NOT_FOUND;
        }
    }

}
