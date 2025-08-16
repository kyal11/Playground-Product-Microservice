package com.microservice.product.repository;

import com.microservice.product.entity.Category;
import com.microservice.product.entity.ProductImage;
import com.microservice.product.entity.ProductVideo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductVideoRepository extends JpaRepository<ProductVideo, Long> {
    Optional<ProductVideo> findById(Long id);

    Optional<ProductVideo> findByName(String name);

    List<ProductVideo> findAll();

    List<ProductVideo> findAllByDeletedAtIsNull();

    Category save(ProductVideo video);

    @Modifying
    @Query("UPDATE ProductVideo u SET u.deletedAt = CURRENT_TIMESTAMP WHERE u.id = :id")
    void softDeleteById(Long id);

    @Modifying
    @Query("UPDATE ProductVideo u SET u.deletedAt = CURRENT_TIMESTAMP WHERE u.name = :name")
    void softDeleteByName(String name);

    void deletedById(Long id);

    void deletedByName(String name);
}
