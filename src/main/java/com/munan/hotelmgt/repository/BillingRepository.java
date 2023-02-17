package com.munan.hotelmgt.repository;

import com.munan.hotelmgt.model.Billing;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillingRepository extends JpaRepository<Billing, Long> {
}
