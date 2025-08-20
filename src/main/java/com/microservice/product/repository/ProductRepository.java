package com.microservice.product.repository;

import com.microservice.product.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("""
            SELECT p FROM Product  p
            JOIN p.subCategory sc
            JOIN sc.category c
            WHERE (:name is NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%')))
              AND (:categoryId IS NULL OR c.id = :categoryId)
              AND (:subCategoryId IS NULL OR sc.id = :subCategoryId)
              AND (:startDate IS NULL OR p.createdAt >= :startDate)
              AND (:endDate IS NULL OR p.createdAt <= :endDate)
              AND p.deletedAt IS NULL
            """)
    Page<Product> searchProduct(
            @Param("name") String name,
            @Param("categoryId") Long categoryId,
            @Param("subCategoryId") Long subCategoryId,
            @Param("startDate")Instant startDate,
            @Param("endDate") Instant endDate,
            Pageable pageable
            );

    Optional<Product> findById(Long id);

    Optional<Product> findByName(String name);

    List<Product> findAll();

    List<Product> findAllByDeletedAtIsNull();

    Page<Product> findAllByDeletedAtIsNull(Pageable pageable);

    @Query("SELECT p FROM Product p JOIN p.subCategory sc JOIN sc.category c WHERE c.id = :categoryId")
    List<Product> findByCategoryId(String categoryId);

    @Query("SELECT p FROM Product p JOIN p.subCategory sc JOIN sc.category c WHERE c.id = :categoryName")
    List<Product> findByCategoryName(String categoryName);

    @Query("SELECT p FROM Product p JOIN p.subCategory sc Where sc.name = :categoryName")
    List<Product> findBySubCategoryName(String categoryName);

    @Query("SELECT p FROM Product p JOIN p.subCategory sc Where sc.name = :categoryId")
    List<Product> findBySubCategoryId(String categoryId);

    Product save(Product product);

    @Modifying
    @Query("UPDATE Product u SET u.deletedAt = CURRENT_TIMESTAMP WHERE u.id = :id")
    void softDeleteById(Long id);

    @Modifying
    @Query("UPDATE Product u SET u.deletedAt = CURRENT_TIMESTAMP WHERE u.name = :name")
    void softDeleteByName(String name);

    void deleteById(Long id);
}
