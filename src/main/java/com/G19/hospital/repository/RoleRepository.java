package com.G19.hospital.repository;

import com.G19.hospital.model.Role;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role,Integer> {
    // public Role findByName(String name);
        Optional<Role> findByName(String name);
        Boolean existsByName(String name);



}
