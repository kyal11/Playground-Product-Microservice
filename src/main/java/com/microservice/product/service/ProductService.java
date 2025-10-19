package com.microservice.product.service;

import com.microservice.product.dto.ApiResponse;
import com.microservice.product.dto.product.req.CreateProductReq;
import com.microservice.product.dto.product.req.CreateVariantProductReq;
import com.microservice.product.dto.product.req.SearchProductReq;
import com.microservice.product.dto.product.req.UpdateProductReq;
import com.microservice.product.dto.product.res.ProductRes;
import com.microservice.product.entity.Product;
import com.microservice.product.entity.ProductVariant;
import com.microservice.product.entity.SubCategory;
import com.microservice.product.repository.ProductRepository;
import com.microservice.product.repository.SubCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final SubCategoryRepository subCategoryRepository;

    public ApiResponse<Page<ProductRes>> searchProducts(SearchProductReq dto, Pageable pageable) {
        Page<ProductRes> products = productRepository
                .searchProduct(dto.getName(), dto.getCategoryId(), dto.getSubCategoryId(), dto.getStartDate(), dto.getEndDate(), pageable)
                .map(this::mapToProductRes);
        return new ApiResponse<>(true, "Success search products", products);
    }

    public ApiResponse<List<ProductRes>> getAllProducts() {
        List<ProductRes> products = productRepository.findAllByDeletedAtIsNull().stream()
                .map(this::mapToProductRes)
                .toList();
        return new ApiResponse<>(true, "Success get all products", products);
    }

    public ApiResponse<ProductRes> getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        return new ApiResponse<>(true, "Success get product", mapToProductRes(product));
    }

    public ApiResponse<ProductRes> createProduct(CreateProductReq dto) {
        SubCategory subCategory = subCategoryRepository.findById(dto.getSubCategoryId())
                .orElseThrow(() -> new RuntimeException("SubCategory not found"));

        boolean hasVariant = Boolean.TRUE.equals(dto.getHasVariant());

        Product product = new Product();
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setStock(dto.getStock());
        product.setHasVariant(dto.getHasVariant());
        product.setSubCategory(subCategory);
        product.setCreatedAt(Instant.now());

        if (hasVariant) {
            List<CreateVariantProductReq> variants = dto.getVariantProducts();

            if (variants == null || variants.isEmpty()) {
                throw new IllegalArgumentException("Variant products must be provided when hasVariant is true");
            }

            List<ProductVariant> productVariants = variants.stream()
                    .map(v -> {
                        ProductVariant variant = new ProductVariant();
                        variant.setVariantName(v.getVariantName());
                        variant.setPrice(v.getPrice());
                        variant.setStock(v.getStock());
                        variant.setProduct(product);
                        return variant;
                    }).toList();

            product.setVariants(productVariants);
        }

        Product savedProduct = productRepository.save(product);
        return new ApiResponse<>(true, "Product created successfully", mapToProductRes(savedProduct));
    }

    public ApiResponse<ProductRes> updateProduct(UpdateProductReq dto) {
        Product product = productRepository.findById(dto.getId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (dto.getName() != null) product.setName(dto.getName());
        if (dto.getDescription() != null) product.setDescription(dto.getDescription());
        if (dto.getPrice() != null) product.setPrice(dto.getPrice());
        if (dto.getStock() != null) product.setStock(dto.getStock());
        if (dto.getHasVariant() != null) product.setHasVariant(dto.getHasVariant());

        if (dto.getSubCategoryId() != null) {
            SubCategory subCategory = subCategoryRepository.findById(dto.getSubCategoryId())
                    .orElseThrow(() -> new RuntimeException("SubCategory not found"));
            product.setSubCategory(subCategory);
        }

        product.setUpdatedAt(Instant.now());
        Product updatedProduct = productRepository.save(product);
        return new ApiResponse<>(true, "Product updated successfully", mapToProductRes(updatedProduct));
    }

    @Transactional
    public ApiResponse<Void> softDeleteProductById(Long id) {
        productRepository.softDeleteById(id);
        return new ApiResponse<>(true, "Product deleted successfully", null);
    }

    private ProductRes mapToProductRes(Product product) {
        ProductRes dto = new ProductRes();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        dto.setStock(product.getStock());
        dto.setHasVariant(product.getHasVariant());
        dto.setCreatedAt(product.getCreatedAt());
        dto.setUpdatedAt(product.getUpdatedAt());

        if (product.getSubCategory() != null) {
            dto.setSubCategoryId(product.getSubCategory().getId());
            dto.setSubCategoryName(product.getSubCategory().getName());
            if (product.getSubCategory().getCategory() != null) {
                dto.setCategoryId(product.getSubCategory().getCategory().getId());
                dto.setCategoryName(product.getSubCategory().getCategory().getName());
            }
        }

        return dto;
    }
}
