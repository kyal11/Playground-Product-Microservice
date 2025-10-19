package com.microservice.product.dto.product.req;

import lombok.Data;

@Data
public class CreateVariantProductReq {
    private String variantName;
    private Double price;
    private Integer stock;
}
