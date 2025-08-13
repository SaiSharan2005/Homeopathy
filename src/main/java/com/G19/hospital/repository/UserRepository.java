package com.G19.hospital.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.G19.hospital.model.Role;
import com.G19.hospital.model.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    // Find a user by their username (unique)
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    Optional<User> findById(Long id);
    Boolean existsByUserId(String userId);
    
    // Find a user by their phone number (unique)
    Optional<User> findByPhoneNumber(String phoneNumber);

    // Check if a user exists by their username (for registration validation)
    boolean existsByUsername(String username);
    boolean existsByPhoneNumber(String phoneNumber);

    // Find a user by their doctor ID (now directly in the User entity)
    Optional<User> findByUserId(String doctorId);

    // Get all users with a specific role (e.g., DOCTOR or PATIENT)
    List<User> findByRoles(Role role);

    // new pageable query:
    Page<User> findByRoles(Role role, Pageable pageable);
    // Count the number of users with a specific role (e.g., count all doctors)
    long countByRoles(Role role);

    boolean deleteById(Long id);
    // Search for users (doctors) by name or specialization
    // Adjusted: Assuming specialization might still come from DoctorDetails (if needed)
  
    @Query("SELECT u FROM User u LEFT JOIN u.doctorDetails d WHERE " +
           "(LOWER(u.username) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(d.specialization) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(u.userId) LIKE LOWER(CONCAT('%', :keyword, '%'))) AND :role MEMBER OF u.roles")
    Page<User> searchUsers(@Param("keyword") String keyword,
                           @Param("role") Role role,
                           Pageable pageable);


    @Query("""
      SELECT u
      FROM User u
      WHERE :role MEMBER OF u.roles
        AND (
              LOWER(u.username) LIKE LOWER(CONCAT('%', :kw, '%'))
           OR LOWER(u.userId) LIKE LOWER(CONCAT('%', :kw, '%'))
           OR LOWER(u.phoneNumber) LIKE LOWER(CONCAT('%', :kw, '%'))
           OR LOWER(u.email) LIKE LOWER(CONCAT('%', :kw, '%'))
        )
      """)
    Page<User> searchPatients(
      @Param("kw") String keyword,
      @Param("role") Role role,
      Pageable pageable
    );

    boolean existsByEmail(String email);
}