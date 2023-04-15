package com.munan.hotelmgt.repository;

import com.munan.hotelmgt.model.Staff;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StaffRepository extends JpaRepository<Staff, Long> {
    
    List<Staff> findByShift_id(Long id);
    
    List<Staff> findByJob_id(Long id);

}
