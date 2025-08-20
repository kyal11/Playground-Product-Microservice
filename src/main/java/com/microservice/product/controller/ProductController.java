package com.microservice.product.controller;

import com.microservice.product.dto.ApiResponse;
import com.microservice.product.dto.product.req.CreateProductReq;
import com.microservice.product.dto.product.req.SearchProductReq;
import com.microservice.product.dto.product.req.UpdateProductReq;
import com.microservice.product.dto.product.res.ProductRes;
import com.microservice.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<ProductRes>>> getAllProducts(
    ) {
        return ResponseEntity.ok(productService.getAllProducts());
    }
    @PostMapping("/search")
    public ResponseEntity<ApiResponse<Page<ProductRes>>> searchProducts(
            @RequestBody SearchProductReq dto,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(productService.searchProducts(
              dto, pageable
        ));
    }
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductRes>> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }


    @PostMapping
    public ResponseEntity<ApiResponse<ProductRes>> createProduct(@RequestBody CreateProductReq dto) {
        return ResponseEntity.ok(productService.createProduct(dto));
    }

    @PutMapping
    public ResponseEntity<ApiResponse<ProductRes>> updateProduct(@RequestBody UpdateProductReq dto) {
        return ResponseEntity.ok(productService.updateProduct(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> softDeleteProduct(@PathVariable Long id) {
        return ResponseEntity.ok(productService.softDeleteProductById(id));
    }
}
