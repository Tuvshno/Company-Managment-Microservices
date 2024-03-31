package com.jobservice.jobms.job.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.jobservice.jobms.job.Job;
import com.jobservice.jobms.job.JobRepository;
import com.jobservice.jobms.job.JobService;
import com.jobservice.jobms.job.clients.CompanyClient;
import com.jobservice.jobms.job.clients.ReviewClient;
import com.jobservice.jobms.job.dto.JobWithCompanyDTO;
import com.jobservice.jobms.job.external.Company;
import com.jobservice.jobms.job.external.Review;
import com.jobservice.jobms.job.mapper.JobMapperDTO;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;

@Service
public class JavaServiceImpl implements JobService {

    // private List<Job> jobs = new ArrayList<>();
    JobRepository jobRepository;

    @Autowired
    RestTemplate restTemplate;

    private Long nextId = 1L;

    private CompanyClient companyClient;
    private ReviewClient reviewClient;

    public JavaServiceImpl(JobRepository jobRepository, CompanyClient companyClient, ReviewClient reviewClient) {
        this.jobRepository = jobRepository;
        this.companyClient = companyClient;
        this.reviewClient = reviewClient;
    }

    @Override
    @CircuitBreaker(name = "companyBreaker",
            fallbackMethod = "companyBreakerFallback")
    @Retry(name = "companyBreaker",
            fallbackMethod = "companyBreakerFallback")
    @RateLimiter(name = "companyBreaker",
            fallbackMethod = "companyBreakerFallback")
    public List<JobWithCompanyDTO> findAll() {
        List<Job> jobs = jobRepository.findAll();

        return jobs.stream().map(this::converToDTO).collect(Collectors.toList());

    }

    public List<String> companyBreakerFallback() {
        List<String> list = new ArrayList<>();
        list.add("Fallback Values");
        return list;
    }

    @Override
    public void createJob(Job job) {
        job.setId(nextId++);
        jobRepository.save(job);

    }

    @Override
    public JobWithCompanyDTO getJobById(Long id) {
        Job job = jobRepository.findById(id).orElse(null);
        if (job == null) {
            return null;
        }

        return converToDTO(job);

    }

    @Override
    public HttpStatus deleteJob(Long id) {
        try {
            jobRepository.deleteById(id);
            return HttpStatus.OK;

        } catch (Exception e) {
            return HttpStatus.NOT_FOUND;
        }
    }

    @Override
    public HttpStatus updateJob(Long id, Job updatedJob) {
        Optional<Job> jobOptional = jobRepository.findById(id);
        if (jobOptional.isPresent()) {
            Job job = jobOptional.get();
            if (updatedJob.getTitle() != null) {
                job.setTitle(updatedJob.getTitle());
            }
            if (updatedJob.getDescription() != null) {
                job.setDescription(updatedJob.getDescription());
            }
            if (updatedJob.getMinSalary() != null) {
                job.setMinSalary(updatedJob.getMinSalary());
            }
            if (updatedJob.getMaxSalary() != null) {
                job.setMaxSalary(updatedJob.getMaxSalary());
            }
            if (updatedJob.getLocation() != null) {
                job.setLocation(updatedJob.getLocation());
            }

            jobRepository.save(job);
            return HttpStatus.OK;
        }
        return HttpStatus.NOT_FOUND;

    }

    private JobWithCompanyDTO converToDTO(Job job) {

        JobWithCompanyDTO jobWithCompanyDTO;

        Company company = companyClient.getCompany(job.getCompanyId());
        List<Review> reviews = reviewClient.getReview(job.getCompanyId());

        jobWithCompanyDTO = JobMapperDTO.mapToDTO(job, company, reviews);

        return jobWithCompanyDTO;
    }
}
