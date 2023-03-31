/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.munan.hotelmgt.repository;

import com.munan.hotelmgt.model.PaymentMethod;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author godwi
 */
public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, Long>{
    Optional<PaymentMethod> findByType(String type);
}
