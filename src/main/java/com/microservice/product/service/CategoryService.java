package com.microservice.product.service;

import com.microservice.product.dto.ApiResponse;
import com.microservice.product.dto.Category.req.CreateCategoryReq;
import com.microservice.product.dto.Category.req.UpdateCategoryReq;
import com.microservice.product.dto.Category.res.CategoryRes;
import com.microservice.product.dto.SubCategory.req.CreateSCategoryReq;
import com.microservice.product.dto.SubCategory.req.UpdateSCategotyReq;
import com.microservice.product.dto.SubCategory.res.SubCategoryRes;
import com.microservice.product.entity.Category;
import com.microservice.product.entity.SubCategory;
import com.microservice.product.repository.CategoryRepository;
import com.microservice.product.repository.SubCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final SubCategoryRepository subCategoryRepository;

    @Cacheable(value = "categories")
    public ApiResponse<List<CategoryRes>> getAllCategory() {
        List<CategoryRes> categories = categoryRepository.findAllWithSubCategories().stream()
                .map(this::mapToCategoryRes)
                .toList();

        return new ApiResponse<>(true, "Success get data category!", categories);
    }

    @Cacheable(value = "subCategories")
    public ApiResponse<List<SubCategoryRes>> getSubCategory() {
        List<SubCategoryRes> subCategories = subCategoryRepository.findAll().stream()
                .map(this::mapToSubCategoryRes)
                .toList();

        return new ApiResponse<>(true, "Success get data sub category!!", subCategories);
    }

    public ApiResponse<CategoryRes> getCategoryById(Long id) {
        Category categories = categoryRepository.findByIdWithSubCategories(id)
                .orElseThrow(() -> new RuntimeException("Category Not Found!"));

        return new ApiResponse<>(true, "Success get data category!", mapToCategoryRes(categories));
    }

    public ApiResponse<SubCategoryRes> getSubCategoryById(Long id) {
        SubCategory subCategory = subCategoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category Not Found!"));

        return new ApiResponse<>(true, "Success get data subCategory !", mapToSubCategoryRes(subCategory));
    }

    @CacheEvict(value = "categories", allEntries = true)
    public ApiResponse<CategoryRes> createCategory(CreateCategoryReq dto) {
        Category dataCategory = new Category();
        dataCategory.setName(dto.getName());
        dataCategory.setDescription(dto.getDescription());
        Category category = categoryRepository.save(dataCategory);

        return new ApiResponse<>(true, "Success Create Category", mapToCategoryRes(category));
    }

    @CacheEvict(value = "subCategories", allEntries = true)
    public ApiResponse<SubCategoryRes> createSubCategory(CreateSCategoryReq dto) {
        Category category = categoryRepository.findById(dto.getCategory_id())
                .orElseThrow(() -> new IllegalArgumentException("Category not found for subcategory creation"));

        SubCategory dataSubCategory = new SubCategory();
        dataSubCategory.setName(dto.getName());
        dataSubCategory.setCategory(category);
        SubCategory subCategory = subCategoryRepository.save(dataSubCategory);

        return new ApiResponse<>(true, "Success Create Category", mapToSubCategoryRes(subCategory));
    }

    public ApiResponse<CategoryRes> createCategoryWithSub(CreateCategoryReq dto) {
        Category category = new Category();
        category.setName(dto.getName());
        category.setDescription(dto.getDescription());

        if (dto.getSub_categories() != null && !dto.getSub_categories().isEmpty()) {
            List<SubCategory> subCategories = dto.getSub_categories().stream()
                    .map(subDto -> {
                       SubCategory subCategory = new SubCategory();
                       subCategory.setName(subDto.getName());
                       subCategory.setCategory(category);
                       return subCategory;
                    })
                    .collect(Collectors.toList());

            category.setSubCategories(subCategories);
        }
        Category savedCategory = categoryRepository.save(category);

        return new ApiResponse<>(true, "Success create data category!", mapToCategoryRes(savedCategory));
    };
    public ApiResponse<CategoryRes> updateCategory(UpdateCategoryReq dto) {
        Category category = categoryRepository.findByIdWithSubCategories(dto.getId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        category.setName(dto.getName());
        category.setDescription(dto.getDescription());

        Category updated = categoryRepository.save(category);
        return new ApiResponse<>(true, "Category updated successfully", mapToCategoryRes(updated));
    }

    public ApiResponse<SubCategoryRes> updateSubCategory(UpdateSCategotyReq dto) {
        SubCategory subCategory = subCategoryRepository.findById(dto.getId())
                .orElseThrow(() -> new RuntimeException("SubCategory not found"));

        subCategory.setName(dto.getName());

        SubCategory updated = subCategoryRepository.save(subCategory);
        return new ApiResponse<>(true, "SubCategory updated successfully", mapToSubCategoryRes(updated));
    }

    @Transactional
    public ApiResponse<Void> softDeleteCategoryById(Long id) {
        categoryRepository.softDeleteById(id);
        return new ApiResponse<>(true, "Category deleted successfully", null);
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
                        if (sc.getCategory() != null) {
                            subDto.setCategory_id(sc.getCategory().getId());
                            subDto.setCategory_name(sc.getCategory().getName());
                        }
                        return subDto;
                    })
                    .toList());
        }
        return dto;
    }
    private SubCategoryRes mapToSubCategoryRes(SubCategory subCategory) {
        SubCategoryRes dto = new SubCategoryRes();
        dto.setId(subCategory.getId());
        dto.setName(subCategory.getName());
        dto.setCreated_at(subCategory.getCreatedAt());
        dto.setUpdated_at(subCategory.getUpdatedAt());

        if (subCategory.getCategory() != null) {
            dto.setCategory_id(subCategory.getCategory().getId());
            dto.setCategory_name(subCategory.getCategory().getName());
        }

        return dto;
    }

}
