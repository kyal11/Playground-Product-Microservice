package com.microservice.product.dto.SubCategory.res;

import lombok.Data;

import java.time.Instant;

@Data
public class SubCategoryRes {
    private Long id;
    private String name;
    private Instant created_at;
    private Instant updated_at;

    private Long category_id;
    private String category_name;
}
