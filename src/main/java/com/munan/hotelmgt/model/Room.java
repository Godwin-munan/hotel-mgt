package com.munan.hotelmgt.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

import static jakarta.persistence.GenerationType.IDENTITY;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@SQLDelete(sql = "UPDATE room SET deleted = true WHERE id=?")
@Where(clause = "deleted=false")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Room implements Serializable {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    
    @Column(name = "room_code")
    private String code;
    
    @Column(name = "deleted")
    private boolean deleted = Boolean.FALSE;
    
    @Column(name = "room_status")
    private String status;

    @ManyToOne
    private RoomType roomType;
    

    @ManyToMany(fetch = FetchType.LAZY,
        cascade = {
                CascadeType.ALL
                },
                mappedBy = "rooms")
        @JsonIgnore
        private Set<Guest> guests = new CopyOnWriteArraySet<>();
    
}
