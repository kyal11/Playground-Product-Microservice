package com.microservice.product.dto.Category.res;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.microservice.product.dto.SubCategory.res.SubCategoryRes;
import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CategoryRes {
    private Long id;
    private String name;
    private String description;
    private Instant created_at;
    private Instant updated_at;

    private List<SubCategoryRes> sub_category;
}
