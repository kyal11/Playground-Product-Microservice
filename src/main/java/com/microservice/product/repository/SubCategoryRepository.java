package com.microservice.product.repository;

import com.microservice.product.entity.SubCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubCategoryRepository extends JpaRepository<SubCategory, Long> {
    Optional<SubCategory> findById(Long id);

    Optional<SubCategory> findByName(String name);

    List<SubCategory> findAll();

    List<SubCategory> findAllByDeletedAtIsNull();

    SubCategory save(SubCategory subCategory);

    @Modifying
    @Query("UPDATE SubCategory u SET u.deletedAt = CURRENT_TIMESTAMP WHERE u.id = :id")
    void softDeleteById(Long id);

    @Modifying
    @Query("UPDATE SubCategory u SET u.deletedAt = CURRENT_TIMESTAMP WHERE u.name = :name")
    void softDeleteByName(String name);

    void deletedById(Long id);

    void deletedByName(String name);
}
