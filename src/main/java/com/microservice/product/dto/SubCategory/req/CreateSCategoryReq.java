package com.microservice.product.dto.SubCategory.req;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateSCategoryReq {
    @NotBlank
    private String name;

    @NotBlank
    private Long category_id;
}
