package com.microservice.product.dto.product.req;

import lombok.Data;

@Data
public class UpdateProductReq {
    private Long id;
    private String name;
    private String description;
    private Double price;
    private Integer stock;
    private Boolean hasVariant;
    private Long subCategoryId;
}
