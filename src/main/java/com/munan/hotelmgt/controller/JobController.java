package com.munan.hotelmgt.controller;

import com.munan.hotelmgt.dto.JobDto;
import com.munan.hotelmgt.exception.AlreadyExistException;
import com.munan.hotelmgt.exception.NotFoundException;
import com.munan.hotelmgt.model.Job;
import com.munan.hotelmgt.service.JobService;
import com.munan.hotelmgt.utils.HttpResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/job")
@Tag(name = "Job Controller", description = "Job Controller")
@RequiredArgsConstructor
public class JobController {

    private final JobService jobService;

    //ADD
    @Operation(summary = "ADD JOB", description = "Add a new Job")
    @PostMapping("/add")
    public ResponseEntity<HttpResponse<?>> addJob(@RequestBody JobDto job) throws AlreadyExistException {
        return jobService.add(job);
    }

    //GET
    @Operation(summary = "RETRIEVE ALL", description = "Retrieve all Jobs by Pagination")
    @GetMapping("/get/{field}/{page}/{size}")
    public ResponseEntity<HttpResponse<?>> getAllJobs(@PathVariable("field") String field,
                                                      @PathVariable("page") Integer page,
                                                      @PathVariable("size") Integer size) {
        return jobService.getAll(page, size, field);
    }

    @Operation(summary = "GET BY ID", description = "Retrieve job by id")
    @GetMapping("/get/{job_id}")
    public ResponseEntity<HttpResponse<?>> getJobById(@PathVariable(value = "job_id") Long id) throws NotFoundException {
        return jobService.getById(id);
    }

    
    //DELETE
    @Operation(summary = "DELETE BY ID", description = "Remove single job by id")
    @DeleteMapping("/delete/{job_id}")
    public ResponseEntity<HttpResponse<?>> deleteJobById(@PathVariable(value = "job_id") Long id) throws NotFoundException {
        return jobService.deleteById(id);
    }
    
    
    //UPDATE
    @Operation(summary = "UPDATE JOB", description = "Update job record")
    @PutMapping("/update")
    public ResponseEntity<HttpResponse<?>> updateJob(@RequestBody Job job){
        return jobService.update(job);
    }
    
}
