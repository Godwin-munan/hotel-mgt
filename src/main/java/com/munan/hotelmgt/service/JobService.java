package com.munan.hotelmgt.service;

import static com.munan.hotelmgt.constant.GenConstant.succesResponse;
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

    //ADD NEW JOB
    public ResponseEntity<HttpResponse<?>> add(JobDto job) throws AlreadyExistException {

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
                        HttpStatus.OK.value(),
                        HttpStatus.OK,
                        succesResponse,
                        jobRepository.save(newJob)
                )
        );
    }

    //GET ALL JOBS
    public ResponseEntity<HttpResponse<?>> getAll() {
        return ResponseEntity.ok(
                new HttpResponse<>(HttpStatus.OK.value(),
                        HttpStatus.OK,
                        succesResponse,
                        jobRepository.findAll())
        );
    }

    //GET JOB BY ID
    public ResponseEntity<HttpResponse<?>> getById(Long id) throws NotFoundException {

        Job findJob = findById(id);

        return ResponseEntity.ok(
                new HttpResponse<>(HttpStatus.OK.value(),
                        HttpStatus.OK,
                        succesResponse,
                        findJob)
        );
    }

    //DELETE JOB BY ID
    public ResponseEntity<HttpResponse<?>> deleteById(Long id) throws NotFoundException {

        Job findJob = findById(id);

        String title = findJob.getTitle();

        jobRepository.delete(findJob);

        return ResponseEntity.ok(
                new HttpResponse<>(
                        HttpStatus.OK.value(),
                        HttpStatus.OK,
                        succesResponse,
                        "Job with title " + title +" "+ " has been deleted")
        );
    }

    //UPDATE JOB
    public ResponseEntity<HttpResponse<?>> update(Job job){
        
        Job savedJob = new Job();
        
        if(job.getId() != null){
            savedJob.setId(job.getId());
        }
        
        savedJob.setDescription(job.getDescription());
        savedJob.setSalary(job.getSalary());
        savedJob.setTitle(job.getTitle());
        
        return ResponseEntity.ok(
                new HttpResponse<>(
                        HttpStatus.OK.value(),
                        HttpStatus.OK,
                        succesResponse,
                        jobRepository.save(savedJob))
        );
    }
    
    
    //FIND JOB BY TYPE
    public Job findJobByTitle(String title) throws NotFoundException{
        return jobRepository.findByTitle(title).orElseThrow(()-> new NotFoundException("Job with "+title+" not Found"));
    }
    
    //PRIVATE METHOD TO FIND BY ID
    public Job findById(Long id) throws NotFoundException {

        return jobRepository.findById(id)
                .orElseThrow(()->new NotFoundException("Job with id "+id+", Does not exist"));

    }
        
}
