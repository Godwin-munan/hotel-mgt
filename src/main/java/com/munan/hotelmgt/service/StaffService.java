package com.munan.hotelmgt.service;

import com.munan.hotelmgt.constant.GenConstant;
import static com.munan.hotelmgt.constant.GenConstant.succesResponse;
import com.munan.hotelmgt.dto.StaffDto;
import com.munan.hotelmgt.exception.InvalidEmailException;
import com.munan.hotelmgt.exception.NotFoundException;
import com.munan.hotelmgt.model.Gender;
import com.munan.hotelmgt.model.IdCard;
import com.munan.hotelmgt.model.Job;
import com.munan.hotelmgt.model.Shift;
import com.munan.hotelmgt.model.Staff;
import com.munan.hotelmgt.repository.StaffRepository;
import com.munan.hotelmgt.utils.HttpResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class StaffService {

    private final StaffRepository staffRepository;
    private final IdCardService cardService;
    private final GenderService genderService;
    private final JobService jobService;
    private final ShiftService shiftService;

    //ADD NEW GUEST
    public ResponseEntity<HttpResponse<?>> add(StaffDto staffDto) throws NotFoundException, InvalidEmailException {
        Staff newStaff = addStaffFromDto(staffDto);
   
        return ResponseEntity.ok(
                new HttpResponse<>(
                        HttpStatus.OK.value(),
                        HttpStatus.OK,
                        succesResponse,
                        staffRepository.save(newStaff)
                )
        );
    }

    //GET ALL STAFFS
    public ResponseEntity<HttpResponse<?>> getAll(Integer page, Integer size, String field) {
        return ResponseEntity.ok(
                new HttpResponse<>(HttpStatus.OK.value(),
                        HttpStatus.OK,
                        succesResponse,
                        staffRepository.findAll(PageRequest.of(page,size, Sort.by(Sort.Direction.ASC, field))))
        );
    }

    //GET STAFF BY ID
    public ResponseEntity<HttpResponse<?>> getById(Long id) throws NotFoundException {
        Staff findStaff = findById(id);
        

        return ResponseEntity.ok(
                new HttpResponse<>(HttpStatus.OK.value(),
                        HttpStatus.OK,
                        succesResponse,
                        findStaff)
        );
    }
    
    //GET STAFF BY SHIFT ID
    public ResponseEntity<HttpResponse<?>> getByShiftId(Long id) throws NotFoundException {
        Shift shift = shiftService.findById(id);
        
        return ResponseEntity.ok(
                new HttpResponse<>(HttpStatus.OK.value(),
                        HttpStatus.OK,
                        succesResponse,
                        staffRepository.findByShift_id(shift.getId()))
        );
    }
    
    //GET STAFF BY JOB ID
    public ResponseEntity<HttpResponse<?>> getByJobId(Long id) throws NotFoundException {
        Job job = jobService.findById(id);
        
        return ResponseEntity.ok(
                new HttpResponse<>(HttpStatus.OK.value(),
                        HttpStatus.OK,
                        succesResponse,
                        staffRepository.findByJob_id(job.getId()))
        );
    }

    //DELETE STAFF BY ID
    public ResponseEntity<HttpResponse<?>> deleteById(Long id) throws NotFoundException {
        Staff findStaff = findById(id);

        String name = findStaff.getFirstName();

        staffRepository.delete(findStaff);

        return ResponseEntity.ok(
                new HttpResponse<>(
                        HttpStatus.OK.value(),
                        HttpStatus.OK,
                        succesResponse,
                        "Staff with name " + name +" "+ " has been deleted")
        );
    }

    //UPDATE STAFF
    public ResponseEntity<HttpResponse<?>> update(Staff staff) throws NotFoundException, InvalidEmailException {
        Staff savedStaff = addStaffFromStaff(staff);
        
        if(staff.getId() != null){
            savedStaff.setId(staff.getId());
        }
        
        return ResponseEntity.ok(
                new HttpResponse<>(
                        HttpStatus.OK.value(),
                        HttpStatus.OK,
                        succesResponse,
                        staffRepository.save(savedStaff))
        );
    }
    
    //PRIVATE METHOD TO ADD NEW STAFF FROM DTO
    private Staff addStaffFromDto(StaffDto staff) throws NotFoundException, InvalidEmailException{
    
        Staff newStaff = new Staff();
        IdCard card = cardService.findCardByType(staff.getCardType());
        Gender gender = genderService.findGenderByType(staff.getGenderType());
        Job job = jobService.findJobByTitle(staff.getJobTitle());
        Shift shift = shiftService.findShiftByType(staff.getShiftType());
        
        newStaff.setFirstName(staff.getFirstName());
        newStaff.setLastName(staff.getLastName());
        newStaff.setEmployDate(staff.getEmployDate());
        newStaff.setTerminateDate(staff.getTerminateDate());
        newStaff.setCardNo(staff.getCardNo());
        
        if(!GenConstant.emailValidator(staff.getEmail())){
            throw new InvalidEmailException("Invalid email");
        }
        newStaff.setEmail(staff.getEmail());
        newStaff.setJob(job);
        newStaff.setCard(card);
        newStaff.setShift(shift);
        newStaff.setGender(gender);
        
        return newStaff;
    }
    
    //PRIVATE METHOD TO ADD NEW STAFF FROM GUEST
    private Staff addStaffFromStaff(Staff staff) throws NotFoundException, InvalidEmailException{
    
        Staff newStaff = findById(staff.getId());;
        
        if(!GenConstant.emailValidator(staff.getEmail())){
            throw new InvalidEmailException("Invalid email");
        }
        
        if(staff.getFirstName() != null){
            newStaff.setFirstName(staff.getFirstName());
        }
        
        if(staff.getLastName() != null){
            newStaff.setLastName(staff.getLastName());
        }
        
        if(staff.getEmail() != null){
            newStaff.setEmail(staff.getEmail());
        }
        
        if(staff.getShift() != null){
            newStaff.setShift(staff.getShift());
        }
        
        if(staff.getJob() != null){
            newStaff.setJob(staff.getJob());
        }
        
        if(staff.getTerminateDate() != null){
            newStaff.setTerminateDate(staff.getTerminateDate());
        }
        
        if(staff.getCard() != null){
            newStaff.setCard(staff.getCard());
        }
        
        if(staff.getCardNo() != null){
            newStaff.setCardNo(staff.getCardNo());
        }
        
        return newStaff;
    }
    
    //PRIVATE METHOD TO FIND BY ID
    private Staff findById(Long id) throws NotFoundException {

        return staffRepository.findById(id)
                .orElseThrow(()->new NotFoundException("Staff with id "+id+", Does not exist"));

    }

}
