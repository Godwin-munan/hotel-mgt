package com.munan.hotelmgt.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

import static jakarta.persistence.GenerationType.IDENTITY;
import jakarta.persistence.Lob;
import lombok.Builder;
import lombok.Data;

@Entity
@Table(name = "room_type")
//@Getter
//@Setter
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoomType implements Serializable {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    
    @Column(name = "name")
    private String name;
    
    @Column(name = "price")
    private Double Price;
    
    @Column(name = "property")
    private String property;
    
    @Column(name = "description")
    private String description;
    
    @Lob
    @Column(name = "image")
    private byte[] image;
}
