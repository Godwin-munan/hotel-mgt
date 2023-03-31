package com.munan.hotelmgt.model;

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
public class Staff implements Serializable {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    
    @Column(name = "first_name")
    private String firstName;
    
    @Column(name = "last_name")
    private String lastName;
    
    @Column(name = "email")
    private String email;
    
    @Column(name = "card_no")
    private String cardNo;
    
    @Column(name = "join_date")
    private LocalDate joinDate;
    
    @Column(name = "leave_date")
    private LocalDate leaveDate;
    
    @ManyToOne
    private IdCard card;
    
    @ManyToOne
    private Gender gender;
    
    @ManyToOne
    private Shift shift;
    
    @ManyToOne
    private Job job;

}
