package com.munan.hotelmgt.controller;

import com.munan.hotelmgt.dto.JobDto;
import com.munan.hotelmgt.exception.AlreadyExistException;
import com.munan.hotelmgt.exception.NotFoundException;
import com.munan.hotelmgt.service.JobService;
import com.munan.hotelmgt.utils.HttpResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/job")
@Tag(name = "Job Controller", description = "Job Controller")
@RequiredArgsConstructor
public class JobController {

    private final JobService jobService;

    @Operation(summary = "Add Job", description = "Add Job")
    @PostMapping("/add")
    public ResponseEntity<HttpResponse<?>> addJob(@RequestBody JobDto job) throws AlreadyExistException {
        return jobService.addJob(job);
    }

    @Operation(summary = "RETRIEVE ALL ", description = "Add Job")
    @GetMapping("/get/getAll/{field}/{page}/{size}")
    public ResponseEntity<HttpResponse<?>> getAllJobs(@PathVariable("field") String field,
                                                      @PathVariable("page") Integer page,
                                                      @PathVariable("size") Integer size) {
        return jobService.getAllJobs(page, size, field);
    }

    @Operation(summary = "RETRIEVE BY ID", description = "Retrjeve job by id")
    @GetMapping("/get/{id}")
    public ResponseEntity<HttpResponse<?>> getById(@PathVariable Long id) throws NotFoundException {
        return jobService.getById(id);
    }

    @Operation(summary = "DELETE BY ID", description = "Delete job by id")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpResponse<?>> deleteById(@PathVariable Long id) throws NotFoundException {
        return jobService.deleteById(id);
    }
}
