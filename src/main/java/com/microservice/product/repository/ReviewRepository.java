package com.microservice.product.repository;

import com.microservice.product.entity.Category;
import com.microservice.product.entity.ProductVideo;
import com.microservice.product.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    Optional<Review> findById(Long id);

    Optional<Review> findByName(String name);

    List<Review> findAll();

    List<Review> findAllByDeletedAtIsNull();

    Category save(Review video);

    @Modifying
    @Query("UPDATE Review u SET u.deletedAt = CURRENT_TIMESTAMP WHERE u.id = :id")
    void softDeleteById(Long id);

    @Modifying
    @Query("UPDATE Review u SET u.deletedAt = CURRENT_TIMESTAMP WHERE u.name = :name")
    void softDeleteByName(String name);

    void deletedById(Long id);

    void deletedByName(String name);
}
