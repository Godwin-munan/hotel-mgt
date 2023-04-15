package com.munan.hotelmgt.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Payment implements Serializable {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    
    @Column(name = "amount")
    private Double amount;
    
    @Column(name = "payment_date")
    private LocalDate paymentDate;
    
    @ManyToOne
    @JsonIgnore
    private Invoice invoice;
    
    @ManyToOne
    private PaymentMethod paymentMethod;

}
