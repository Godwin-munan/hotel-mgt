package com.munan.hotelmgt.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Invoice implements Serializable {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(name = "late_charges")
    private Double lateCharges;
    
    @Column(name = "invoice_code")
    private String invoiceCode;
    
    @Column(name = "invoice_total")
    private Double invoiceTotal;
    
    @Column(name = "payment_total")
    private Double paymentTotal;

    @ManyToOne
    private Guest guest;
    
}
