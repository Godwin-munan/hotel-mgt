package com.munan.hotelmgt.service;

import com.munan.hotelmgt.repository.BillingRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class BillingService {

    private BillingRepository billingRepository;
}
