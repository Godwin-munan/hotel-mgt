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
@SQLDelete(sql = "UPDATE role SET deleted = true WHERE id=?")
@Where(clause = "deleted=false")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Role implements Serializable {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    
    @Column(name = "name")
    private String name;
    
    @Column(name = "deleted")
    @JsonIgnore
    private boolean deleted = Boolean.FALSE;

    @ManyToMany(fetch = FetchType.LAZY,
        cascade = {
                CascadeType.ALL
                },
                mappedBy = "roles")
        @JsonIgnore
        private Set<AppUser> users = new CopyOnWriteArraySet<>();

    public Role(String name) {
        this.name = name;
    }
    
    
}
