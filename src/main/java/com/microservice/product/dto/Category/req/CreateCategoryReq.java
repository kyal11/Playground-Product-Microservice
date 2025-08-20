package com.microservice.product.dto.Category.req;

import com.microservice.product.dto.SubCategory.req.CreateSCategoryReq;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Data
public class CreateCategoryReq {
    @NotBlank
    private String name;

    private String description;

    private List<CreateSCategoryReq> sub_categories;
}
