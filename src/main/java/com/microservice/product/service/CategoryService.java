package com.microservice.product.service;

import com.microservice.product.dto.ApiResponse;
import com.microservice.product.dto.Category.res.CategoryRes;
import com.microservice.product.dto.SubCategory.res.SubCategoryRes;
import com.microservice.product.entity.Category;
import com.microservice.product.repository.CategoryRepository;
import com.microservice.product.repository.SubCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final SubCategoryRepository subCategoryRepository;

    public ApiResponse<List<CategoryRes>> getAllCategory() {
        List<CategoryRes> categories = categoryRepository.findAllWithSubCategories().stream()
                .map(this::mapToCategoryRes)
                .toList();

        return new ApiResponse<>(true, "Success get data category!", categories);
    }

    public ApiResponse<List<SubCategoryRes>> getSubCategory() {
        List<SubCategoryRes> subCategories = subCategoryRepository.findAll().stream()
                .map(sc -> {
                    SubCategoryRes subDto = new SubCategoryRes();
                    subDto.setId(sc.getId());
                    subDto.setName(sc.getName());
                    return subDto;
                })
                .toList();

        return new ApiResponse<>(true, "Success get data sub category!!", subCategories);
    }

    private CategoryRes mapToCategoryRes(Category category) {
        CategoryRes dto = new CategoryRes();
        dto.setId(category.getId());
        dto.setName(category.getName());
        dto.setDescription(category.getDescription());
        dto.setCreated_at(category.getCreatedAt() != null ? category.getCreatedAt() : null);
        dto.setUpdated_at(category.getUpdatedAt() != null ? category.getUpdatedAt() : null);

        if (category.getSubCategories() != null && !category.getSubCategories().isEmpty()) {
            dto.setSub_category(category.getSubCategories().stream()
                    .map(sc -> {
                        SubCategoryRes subDto = new SubCategoryRes();
                        subDto.setId(sc.getId());
                        subDto.setName(sc.getName());
                        subDto.setCreated_at(sc.getCreatedAt());
                        subDto.setUpdated_at(sc.getUpdatedAt());
                        return subDto;
                    })
                    .toList());
        }
        return dto;
    }
}
