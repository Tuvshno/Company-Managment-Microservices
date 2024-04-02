package com.jobservice.reviewms.reviews;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping
    public ResponseEntity<List<Review>> findAll(@RequestParam Long companyId) {
        return ResponseEntity.ok(reviewService.findall(companyId));
    }

    @PostMapping
    public ResponseEntity<HttpStatus> addReview(@RequestParam Long companyId, @RequestBody Review review) {
        HttpStatus status;
        status = reviewService.addReview(companyId, review);
        return new ResponseEntity<>(status);
    }

    @GetMapping("/{reviewId}")
    public ResponseEntity<Review> getReview(@PathVariable Long reviewId) {
        Review review = reviewService.getReview(reviewId);

        if (review != null) {
            return new ResponseEntity<>(review, HttpStatus.CREATED);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{reviewId}")
    public ResponseEntity<HttpStatus> updateReview(@PathVariable Long reviewId, @RequestBody Review review) {
        return new ResponseEntity<>(reviewService.updateReview(reviewId, review));
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<HttpStatus> deleteReview(@PathVariable Long reviewId) {
        return new ResponseEntity<>(reviewService.deleteReview(reviewId));
    }
}
