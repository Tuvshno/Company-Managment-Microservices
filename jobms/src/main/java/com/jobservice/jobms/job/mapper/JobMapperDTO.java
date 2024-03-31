package com.jobservice.jobms.job.mapper;

import java.util.List;

import com.jobservice.jobms.job.Job;
import com.jobservice.jobms.job.dto.JobWithCompanyDTO;
import com.jobservice.jobms.job.external.Company;
import com.jobservice.jobms.job.external.Review;

public class JobMapperDTO {

    public static JobWithCompanyDTO mapToDTO(Job job, Company company, List<Review> reviews) {
        JobWithCompanyDTO jobWithCompanyDTO = new JobWithCompanyDTO();

        // Set the company
        jobWithCompanyDTO.setCompany(company);

        // Set job details
        jobWithCompanyDTO.setId(job.getId());
        jobWithCompanyDTO.setTitle(job.getTitle());
        jobWithCompanyDTO.setDescription(job.getDescription());
        jobWithCompanyDTO.setMinSalary(job.getMinSalary());
        jobWithCompanyDTO.setMaxSalary(job.getMaxSalary());
        jobWithCompanyDTO.setLocation(job.getLocation());

        // Set reviews
        jobWithCompanyDTO.setReview(reviews);

        return jobWithCompanyDTO;
    }
}
