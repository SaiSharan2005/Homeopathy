package com.G19.hospital.repository;
import com.G19.hospital.model.Advertisement;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AdvertisementRepository extends JpaRepository<Advertisement, Long> {
    Advertisement findByTargetPageAndIsActive(String targetPage, Boolean isActive);
    // void deactivateAllByTargetPage(String targetPage);
    List<Advertisement> findAll();
    Optional<Advertisement> findById(Long id);
    void deleteById(Long id);
    // @Modifying
    @Query("UPDATE Advertisement a SET a.isActive = false WHERE a.targetPage = :targetPage")
    void deactivateAllByTargetPage(@Param("targetPage") String targetPage);


}
