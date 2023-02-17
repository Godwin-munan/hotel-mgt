package com.munan.hotelmgt.service;

import com.munan.hotelmgt.repository.ShiftRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class ShiftService {

    private final ShiftRepository shiftRepository;
}
