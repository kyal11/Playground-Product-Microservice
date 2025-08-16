package com.microservice.product.repository;

import com.microservice.product.entity.Category;
import com.microservice.product.entity.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {
    Optional<ProductImage> findById(Long id);

    Optional<ProductImage> findByName(String name);

    List<ProductImage> findAll();

    List<ProductImage> findAllByDeletedAtIsNull();

    List<ProductImage> findByProductid(Long id);

    Category save(ProductImage image);

    @Modifying
    @Query("UPDATE ProductImage u SET u.deletedAt = CURRENT_TIMESTAMP WHERE u.id = :id")
    void softDeleteById(Long id);

    @Modifying
    @Query("UPDATE ProductImage u SET u.deletedAt = CURRENT_TIMESTAMP WHERE u.name = :name")
    void softDeleteByName(String name);

    void deletedById(Long id);

    void deletedByName(String name);
}
