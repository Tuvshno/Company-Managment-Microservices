package com.jobservice.jobms.job.clients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.jobservice.jobms.job.external.Review;

@FeignClient(name = "REVIEWMS")
public interface ReviewClient {
  
  @GetMapping("/reviews")
  List<Review> getReview(@RequestParam Long companyId);
}
