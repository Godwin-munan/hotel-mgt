package com.munan.hotelmgt.repository;

import com.munan.hotelmgt.model.Payment;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    Optional<List<Payment>> findByInvoice_id(Long id);
    Optional<Payment> findByPaymentMethod_id(Long id);
    
}
