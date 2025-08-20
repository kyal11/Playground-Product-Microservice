package com.microservice.product.dto.product.req;

import lombok.Data;

import java.time.Instant;

@Data
public class SearchProductReq {
    private String name;
    private Long categoryId;
    private Long subCategoryId;
    private Instant startDate;
    private Instant endDate;
}
