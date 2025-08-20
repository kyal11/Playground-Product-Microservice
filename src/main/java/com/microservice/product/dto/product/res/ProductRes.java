package com.microservice.product.dto.product.res;

import lombok.Data;

import java.time.Instant;

@Data
public class ProductRes {
    private Long id;
    private String name;
    private String description;
    private Double price;
    private Integer stock;
    private Boolean hasVariant;
    private Instant createdAt;
    private Instant updatedAt;
    private Long subCategoryId;
    private String subCategoryName;
    private Long categoryId;
    private String categoryName;
}
