package com.example.coffee.controller.product;

import com.example.coffee.common.Result;
import com.example.coffee.common.ResultCode;
import com.example.coffee.model.product.ProductRequestDTO;
import com.example.coffee.model.product.ProductResponseDTO;
import com.example.coffee.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@Tag(name = "Product API", description = "상품 관련 API")
@AllArgsConstructor
public class ProductController {
    private final ProductService productService;

    // 모든 상품 조회
    @GetMapping
    @Operation(summary = "전체 상품 조회 ", description = "전체 상품 조회 API 입니다." )
    public Result<List<ProductResponseDTO>> getAllProducts() {
        return productService.getAllProducts();
    }

    // 특정 상품 조회
    @GetMapping("/{id}")
    @Operation(summary = "특정 상품 조회", description = "특정 상품 조회 API 입니다. 요청항목 : 상품(product)ID" )
    public Result<ProductResponseDTO> getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    // 상품 생성
    @PostMapping("/insert")
    @Operation(summary = "상품 생성", description = "상품 생성 API 입니다." )
    public Result<Void> createProduct(@RequestBody ProductRequestDTO productRequest) {
        return productService.createProduct(productRequest);
    }

    // 상품 수정
    @PutMapping("/update/{id}")
    @Operation(summary = "상품 수정", description = "상품 수정 API 입니다. 요청항목 : 상품(product)ID" )
    public Result<Void> updateProduct(@PathVariable Long id, @RequestBody ProductRequestDTO productRequest) {
        return productService.updateProduct(id, productRequest);
    }

    // 상품 삭제
    @DeleteMapping("/delete/{id}")
    @Operation(summary = "상품 삭제", description = "상품 삭제 API 입니다. 요청항목 : 상품(product)ID" )
    public Result<Void> deleteProduct(@PathVariable Long id) {
        return productService.deleteProduct(id);
    }
}



