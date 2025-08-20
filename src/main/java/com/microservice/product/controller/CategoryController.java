package com.microservice.product.controller;

import com.microservice.product.dto.ApiResponse;
import com.microservice.product.dto.Category.req.CreateCategoryReq;
import com.microservice.product.dto.Category.req.UpdateCategoryReq;
import com.microservice.product.dto.Category.res.CategoryRes;
import com.microservice.product.dto.SubCategory.req.CreateSCategoryReq;
import com.microservice.product.dto.SubCategory.req.UpdateSCategotyReq;
import com.microservice.product.dto.SubCategory.res.SubCategoryRes;
import com.microservice.product.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<CategoryRes>>> getAllCategories() {
        return ResponseEntity.ok(categoryService.getAllCategory());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoryRes>> getCategoryById(@PathVariable Long id) {
        return ResponseEntity.ok(categoryService.getCategoryById(id));
    }

    @GetMapping("/sub-categories")
    public ResponseEntity<ApiResponse<List<SubCategoryRes>>> getAllSubCategories() {
        return ResponseEntity.ok(categoryService.getSubCategory());
    }

    @GetMapping("/sub-categories/{id}")
    public ResponseEntity<ApiResponse<SubCategoryRes>> getSubCategoryById(@PathVariable Long id) {
        return ResponseEntity.ok(categoryService.getSubCategoryById(id));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CategoryRes>> createCategory(@RequestBody CreateCategoryReq dto) {
        return ResponseEntity.ok(categoryService.createCategory(dto));
    }

    @PostMapping("/sub-categories")
    public ResponseEntity<ApiResponse<SubCategoryRes>> createSubCategory(@RequestBody CreateSCategoryReq dto) {
        return ResponseEntity.ok(categoryService.createSubCategory(dto));
    }

    @PostMapping("/with-subcategories")
    public ResponseEntity<ApiResponse<CategoryRes>> createCategoryWithSub(@RequestBody CreateCategoryReq dto) {
        return ResponseEntity.ok(categoryService.createCategoryWithSub(dto));
    }

    @PutMapping
    public ResponseEntity<ApiResponse<CategoryRes>> updateCategory(@RequestBody UpdateCategoryReq dto) {
        return ResponseEntity.ok(categoryService.updateCategory(dto));
    }

    @PutMapping("/sub-categories")
    public ResponseEntity<ApiResponse<SubCategoryRes>> updateSubCategory(@RequestBody UpdateSCategotyReq dto) {
        return ResponseEntity.ok(categoryService.updateSubCategory(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> softDeleteCategory(@PathVariable Long id) {
        return ResponseEntity.ok(categoryService.softDeleteCategoryById(id));
    }
}
