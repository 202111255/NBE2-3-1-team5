package com.example.coffee.controller.product;

import com.example.coffee.common.Result;
import com.example.coffee.common.ResultCode;
import com.example.coffee.model.product.ProductRequestDTO;
import com.example.coffee.model.product.ProductResponseDTO;
import com.example.coffee.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@AllArgsConstructor
public class ProductController {
    private final ProductService productService;

    // 모든 상품 조회
    @GetMapping
    public Result<List<ProductResponseDTO>> getAllProducts() {
        return productService.getAllProducts();
    }

    // 특정 상품 조회
    @GetMapping("/{id}")
    public Result<ProductResponseDTO> getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    // 상품 생성
    @PostMapping("/insert")
    public Result<Void> createProduct(@RequestBody ProductRequestDTO productRequest) {
        return productService.createProduct(productRequest);
    }

    // 상품 수정
    @PutMapping("/update/{id}")
    public Result<Void> updateProduct(@PathVariable Long id, @RequestBody ProductRequestDTO productRequest) {
        return productService.updateProduct(id, productRequest);
    }

    // 상품 삭제
    @DeleteMapping("/delete/{id}")
    public Result<Void> deleteProduct(@PathVariable Long id) {
        return productService.deleteProduct(id);
    }
}



