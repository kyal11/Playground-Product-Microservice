package com.microservice.product.repository;

import com.microservice.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findById(Long id);

    Optional<Product> findByName(String name);

    List<Product> findAll();

    List<Product> findAllByDeletedAtIsNull();

    List<Product> findByCategoryId(Long id);

    List<Product> findBySubCategoryId(Long id);

    @Query("SELECT p FROM Product p JOIN p.subCategory sc JOIN sc.category c WHERE c.id = :categoryId")
    List<Product> findByCategoryName(String categoryId);

    @Query("SELECT p FROM Product p JOIN p.subCategory sc JOIN sc.category c WHERE c.name = :categoryName")
    List<Product> findBySubCategoryName(String categoryName);

    Product save(Product product);

    @Modifying
    @Query("UPDATE Product u SET u.deletedAt = CURRENT_TIMESTAMP WHERE u.id = :id")
    void softDeleteById(Long id);

    @Modifying
    @Query("UPDATE Product u SET u.deletedAt = CURRENT_TIMESTAMP WHERE u.name = :name")
    void softDeleteByName(String name);

    void deletedById(Long id);

    void deletedByName(String name);
}
