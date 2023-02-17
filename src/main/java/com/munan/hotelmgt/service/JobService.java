package com.munan.hotelmgt.service;

import com.munan.hotelmgt.dto.JobDto;
import com.munan.hotelmgt.exception.AlreadyExistException;
import com.munan.hotelmgt.exception.NotFoundException;
import com.munan.hotelmgt.model.Job;
import com.munan.hotelmgt.repository.JobRepository;
import com.munan.hotelmgt.utils.HttpResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class JobService {

    private final JobRepository jobRepository;

    //PRIVATE METHOD TO FIND BY ID
    private Job findById(Long id) throws NotFoundException {

        Optional<Job> findJob = jobRepository.findById(id);

        findJob.orElseThrow(()->new NotFoundException("Job with id "+id+", Does not exist"));

        return findJob.get();

    }

    //ADD NEW JOB
    public ResponseEntity<HttpResponse<?>> addJob(JobDto job) throws AlreadyExistException {

        Optional<Job> findJob = jobRepository.findByTitle(job.getTitle());

        if(findJob.isPresent()){
            throw new AlreadyExistException(job.getTitle()+" Job already exist");
        }

        Job newJob = new Job();
        newJob.setTitle(job.getTitle());
        newJob.setSalary(job.getSalary());
        newJob.setDescription(job.getDescription());



        return ResponseEntity.ok(
                new HttpResponse<>(
                        HttpStatus.OK.value(), "Successful", jobRepository.save(newJob)
                )
        );
    }

    public ResponseEntity<HttpResponse<?>> getAllJobs(Integer page, Integer size, String field) {
        return ResponseEntity.ok(
                new HttpResponse<>(HttpStatus.OK.value(),
                        "Successful",
                        jobRepository.findAll(PageRequest.of(page,size, Sort.by(Sort.Direction.ASC, field))))
        );
    }

    public ResponseEntity<HttpResponse<?>> getById(Long id) throws NotFoundException {

        Job findJob = findById(id);

        return ResponseEntity.ok(
                new HttpResponse<>(HttpStatus.OK.value(), "Successful", findJob)
        );
    }

    public ResponseEntity<HttpResponse<?>> deleteById(Long id) throws NotFoundException {

        Job findJob = findById(id);

        String title = findJob.getTitle();

        jobRepository.delete(findJob);

        return ResponseEntity.ok(
                new HttpResponse<>(HttpStatus.OK.value(), "successful", "Job with title "+title+", has been deleted")
        );
    }
}
