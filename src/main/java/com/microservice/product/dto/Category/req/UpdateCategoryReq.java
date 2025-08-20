package com.microservice.product.dto.Category.req;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateCategoryReq {
    @NotBlank
    private Long id;

    @NotBlank
    private String name;

    private String description;
}
