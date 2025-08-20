package com.microservice.product.dto.SubCategory.req;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateSCategotyReq {
    @NotBlank
    private Long id;

    @NotBlank
    private String name;

}
