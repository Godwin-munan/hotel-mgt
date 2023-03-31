package com.munan.hotelmgt.service;

import static com.munan.hotelmgt.constant.GenConstant.succesResponse;
import com.munan.hotelmgt.exception.AlreadyExistException;
import com.munan.hotelmgt.exception.NotFoundException;
import com.munan.hotelmgt.model.Shift;
import com.munan.hotelmgt.repository.ShiftRepository;
import com.munan.hotelmgt.utils.HttpResponse;
import jakarta.transaction.Transactional;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class ShiftService {

    private final ShiftRepository shiftRepository;

    //ADD SHIFT
    public ResponseEntity<HttpResponse<?>> addShift(Shift shift) throws AlreadyExistException {
        Optional<Shift> findShift = shiftRepository.findByType(shift.getType());
        
        if(findShift.isPresent()) {throw new AlreadyExistException(shift.getType()+" Job already exist");} 
        
        Shift newShift = new Shift();
        newShift.setType(shift.getType());
        
        return ResponseEntity.ok(
                new HttpResponse<>(
                        HttpStatus.OK.value(),
                        HttpStatus.OK,
                        succesResponse,
                        shiftRepository.save(newShift)
                ));
    }

    //GET ALL SHIFT
    public ResponseEntity<HttpResponse<?>> getAllShift() {
        return ResponseEntity.ok(
                new HttpResponse<>(
                        HttpStatus.OK.value(),
                        HttpStatus.OK,
                        succesResponse,
                        shiftRepository.findAll())
        );
    }

    //GET SHIFT BY ID
    public ResponseEntity<HttpResponse<?>> getById(Long id) throws NotFoundException {
        Shift findShift = findById(id);

        return ResponseEntity.ok(
                new HttpResponse<>(HttpStatus.OK.value(),
                        HttpStatus.OK,
                        succesResponse,
                        findShift
                ));
    }

    //DELETE SHIFT BY ID
    public ResponseEntity<HttpResponse<?>> deleteById(Long id) throws NotFoundException {
        Shift findShift = findById(id);

        String type = findShift.getType();

        shiftRepository.delete(findShift);

        return ResponseEntity.ok(
                new HttpResponse<>(
                        HttpStatus.OK.value(),
                        HttpStatus.OK,
                        succesResponse,
                        "Shift of type " + type +" has been deleted"
                ));
    }

    public ResponseEntity<HttpResponse<?>> update(Shift shift) {
        Shift savedShift = new Shift();
        
        if(shift.getId() != null){
            savedShift.setId( shift.getId());
        }
        
        savedShift.setType(shift.getType());
        
        return ResponseEntity.ok(
                new HttpResponse<>(
                        HttpStatus.OK.value(),
                        HttpStatus.OK,
                        succesResponse,
                        shiftRepository.save(savedShift))
        ); 
    }
    
    //METHOD TO FIND BY ID
    public Shift findById(Long id) throws NotFoundException {

        return shiftRepository.findById(id)
                .orElseThrow(()->new NotFoundException("Shift with id "+id+", Does not exist"));

    }
    
    //FIND SHIFT BY TYPE
    public Shift findShiftByType(String type) throws NotFoundException{
        return shiftRepository.findByType(type).orElseThrow(()-> new NotFoundException(type+" shift not Found"));
    }
}
