package com.microservice.product.repository;

import com.microservice.product.entity.Category;
import com.microservice.product.entity.ProductVariant;
import com.microservice.product.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductVariantRepository extends JpaRepository<ProductVariant, Long> {
    Optional<ProductVariant> findById(Long id);

    Optional<ProductVariant> findByName(String name);

    List<ProductVariant> findAll();

    List<ProductVariant> findAllByDeletedAtIsNull();

    List<ProductVariant> findByProductId(Long id);

    Category save(ProductVariant video);

    @Modifying
    @Query("UPDATE ProductVariant u SET u.deletedAt = CURRENT_TIMESTAMP WHERE u.id = :id")
    void softDeleteById(Long id);

    @Modifying
    @Query("UPDATE ProductVariant u SET u.deletedAt = CURRENT_TIMESTAMP WHERE u.name = :name")
    void softDeleteByName(String name);

    void deletedById(Long id);

    void deletedByName(String name);
}
