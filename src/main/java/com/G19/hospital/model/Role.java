package com.G19.hospital.model;


import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.*;

// import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "roles")
public class Role extends BaseEntity{

    @Column(name = "name",unique = true,nullable = false)
    private String name;


    @Override
    public String toString() {
        return "Role{" +
                "id='" + getId() + '\'' +
                "name='" + name + '\'' +
                '}';
    }
    @JsonIgnore
    @ManyToMany(mappedBy = "roles")
    private Set<User> users = new HashSet<>();


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



}
