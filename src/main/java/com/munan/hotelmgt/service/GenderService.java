package com.munan.hotelmgt.service;

import static com.munan.hotelmgt.constant.GenConstant.succesResponse;
import com.munan.hotelmgt.exception.AlreadyExistException;
import com.munan.hotelmgt.exception.NotFoundException;
import com.munan.hotelmgt.model.Gender;
import com.munan.hotelmgt.repository.GenderRepository;
import com.munan.hotelmgt.utils.HttpResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.Optional;


@Service
@Transactional
@RequiredArgsConstructor
public class GenderService {

    private final GenderRepository genderRepository;

    private static final String baseRoute = "/api/gender";

    private Logger logger = LoggerFactory.getLogger(GenderService.class);


    //ADD GENDER
    public ResponseEntity<HttpResponse<?>> addGender(Gender gender) throws AlreadyExistException {

        Optional<Gender> existingGender = genderRepository.findByType(gender.getType());

        if(existingGender.isPresent()){
            throw new AlreadyExistException("Gender already exist");
        }

        Gender newGender = new Gender();
        newGender.setType(gender.getType().toLowerCase());

        logger.info("gender {} created", gender.getType());
        
        return ResponseEntity.ok(
                new HttpResponse<>(
                        HttpStatus.OK.value(),
                        HttpStatus.OK,
                        succesResponse,
                        genderRepository.save(newGender))
        );
    }

    //GET ALL GENDER
    public ResponseEntity<HttpResponse<?>> getAllGender() {

        return ResponseEntity.ok(
                new HttpResponse<>(
                        HttpStatus.OK.value(),
                        HttpStatus.OK,
                        succesResponse,
                        genderRepository.findAll())
        );
    }

    //DELETE GENDER BY ID
    public ResponseEntity<HttpResponse<?>> deleteGenderById(Long id) throws  NotFoundException, AlreadyExistException {

        Optional<Gender> findGender = genderRepository.findById(id);

        if(findGender.isEmpty()){
            throw new NotFoundException("This record does not exist");
        }

        logger.info("gender {} deleted", findGender.get().getType());
        genderRepository.delete(findGender.get());
        
        return  ResponseEntity.ok(
                new HttpResponse<>(
                        HttpStatus.OK.value(),
                        HttpStatus.OK,
                        succesResponse,
                        "gender "+findGender.get().getType()+" was deleted"
                        ));

    }
    
    //UPDATE GENDER
    public ResponseEntity<HttpResponse<?>> updateGender(Gender gender) {
        Gender newGender = new Gender();
        newGender.setType(gender.getType().toLowerCase());
        Gender updatedGender = genderRepository.save(newGender);

        return  ResponseEntity.ok(
                new HttpResponse<>(
                        HttpStatus.OK.value(),  
                        HttpStatus.OK,
                        succesResponse,
                        updatedGender)
        );
    }

    //GET GENDER BY ID
    public ResponseEntity<HttpResponse<?>> getById(Long id) throws NotFoundException {
        
        Gender findGender = genderRepository.findById(id).orElseThrow(()-> new NotFoundException("This record does not exist"));
        
        return ResponseEntity.ok(
                new HttpResponse<>(
                        HttpStatus.OK.value(),
                        HttpStatus.OK,
                        succesResponse,
                        findGender)
        );
    }
    
    //FIND GENDER BY TYPE
    public Gender findGenderById(Long id) throws NotFoundException{
        return genderRepository.findById(id).orElseThrow(()-> new NotFoundException("Gender with "+id+" not Found"));
    }
}


//URI uri = getURL(baseRoute+"/add/addGender");
//
//        logger.info("gender '{}' created", gender.getType());
//        return ResponseEntity.created(uri).body(
//                new HttpResponse<>(
//                        HttpStatus.CREATED.value(),
//                        HttpStatus.CREATED,
//                        "successfully added " +gender.getType() + " gender",
//                        genderRepository.save(newGender))
//        );
