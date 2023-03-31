package com.munan.hotelmgt.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

import static jakarta.persistence.GenerationType.IDENTITY;
import java.time.LocalDate;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import org.hibernate.annotations.Where;
import org.hibernate.annotations.SQLDelete;

@Entity
@SQLDelete(sql = "UPDATE guest SET deleted = true WHERE id=?")
@Where(clause = "deleted=false")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Guest implements Serializable {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;
    
    @Column(name = "deleted")
    private boolean deleted = Boolean.FALSE;
    
    @Column(name = "last_name")
    private String lastName;
    
    @Column(name = "email")
    private String email;
    
    @Column(name = "guest_code")
    private String guestCode;
    
    @Column(name = "check_in")
    private LocalDate checkIn;
    
    @Column(name = "expire_date")
    private LocalDate expireDate;
    
    @Column(name = "check_out")
    private LocalDate checkOut;
    
    @ManyToOne
    private Gender gender;
    
    @ManyToMany(fetch = FetchType.EAGER,
            cascade =  {
                    CascadeType.ALL
            })
    @JoinTable(name = "guest_room",
            joinColumns = { @JoinColumn(name = "guest_id") },
            inverseJoinColumns = { @JoinColumn(name = "room_id") })
    private Set<Room> rooms = new CopyOnWriteArraySet<>();
    
    public void addRoom(Room room) {
        this.rooms.add(room);
        room.getGuests().add(this);
    }

    public void removeRoom(long roomId) {
        Room room = this.rooms.stream().filter(c -> c.getId() == roomId).findFirst().orElse(null);
        if (room != null) {
            this.rooms.remove(room);
            room.getGuests().remove(this);
        }
    }

}
