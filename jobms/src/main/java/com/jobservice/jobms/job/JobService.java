package com.jobservice.jobms.job;

import java.util.List;

import org.springframework.http.HttpStatus;

import com.jobservice.jobms.job.dto.JobWithCompanyDTO;

public interface JobService {

    List<JobWithCompanyDTO> findAll();

    void createJob(Job job);

    JobWithCompanyDTO getJobById(Long id);

    HttpStatus deleteJob(Long id);

    HttpStatus updateJob(Long id, Job job);
}
