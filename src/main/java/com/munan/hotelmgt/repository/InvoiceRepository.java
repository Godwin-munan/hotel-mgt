package com.munan.hotelmgt.repository;

import com.munan.hotelmgt.model.Invoice;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

    Optional<Invoice> findByInvoiceCode(String code);
    
    Optional<Invoice> findByGuest_Id(Long id);
    
    
}
