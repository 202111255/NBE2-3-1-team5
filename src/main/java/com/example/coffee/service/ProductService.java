package com.example.coffee.service;

import com.example.coffee.common.Result;
import com.example.coffee.common.ResultCode;
import com.example.coffee.model.product.ProductDTO;
import com.example.coffee.model.product.ProductRequestDTO;
import com.example.coffee.model.product.ProductResponseDTO;
import com.example.coffee.repository.ProductMapper;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ProductService {
    private final ProductMapper productMapper;

    // 모든 상품 조회
    public Result<List<ProductResponseDTO>> getAllProducts() {
        try {
            List<ProductDTO> products = productMapper.findAll();
            List<ProductResponseDTO> response = new ArrayList<>();
            for (ProductDTO product : products) {
                response.add(convertToResponseDTO(product));
            }

            return new Result<>(ResultCode.SUCCESS, response);
        } catch (DataAccessException e) {
            return new Result<>(ResultCode.DB_ERROR);
        }
    }


    // 특정 상품 조회
    public Result<ProductResponseDTO> getProductById(Long productId) {
        if (productId == null || productId <= 0) {
            return new Result<>(ResultCode.INVALID_PARAMETER);
        }
        try {
            ProductDTO product = productMapper.findById(productId);
            if (product == null) {
                return new Result<>(ResultCode.PRODUCT_NOT_FOUND);
            }
            return new Result<>(ResultCode.SUCCESS, convertToResponseDTO(product));
        } catch (DataAccessException e) {
            return new Result<>(ResultCode.DB_ERROR);
        }
    }

    // 상품 생성
    public Result<Void> createProduct(ProductRequestDTO productRequest) {
        try {
            ProductDTO product = convertToDTO(productRequest);
            productMapper.insert(product);
            return new Result<>(ResultCode.SUCCESS);
        } catch (DataAccessException e) {
            return new Result<>(ResultCode.FAIL_TO_SAVE_PRODUCT);
        } catch (Exception e) {
            return new Result<>(ResultCode.SYSTEM_ERROR);
        }
    }

    // 상품 수정
    public Result<Void> updateProduct(Long productId, ProductRequestDTO productRequest) {
        if (productId == null || productId <= 0) {
            return new Result<>(ResultCode.INVALID_PARAMETER);
        }
        try {
            ProductDTO product = convertToDTO(productRequest);
            product.setProductId(productId);
            int sqlNum = productMapper.update(product);
            if (sqlNum == 0) {
                return new Result<>(ResultCode.PRODUCT_NOT_FOUND);
            }
            return new Result<>(ResultCode.SUCCESS);
        } catch (DataAccessException e) {
            return new Result<>(ResultCode.FAIL_TO_UPDATE_PRODUCT);
        } catch (Exception e) {
            return new Result<>(ResultCode.SYSTEM_ERROR);
        }
    }

    // 상품 삭제
    public Result<Void> deleteProduct(Long productId) {
        if (productId == null || productId <= 0) {
            return new Result<>(ResultCode.INVALID_PARAMETER);
        }
        try {
            int sqlNum = productMapper.delete(productId);
            if (sqlNum == 0) {
                return new Result<>(ResultCode.PRODUCT_NOT_FOUND);
            }
            return new Result<>(ResultCode.SUCCESS);
        } catch (DataAccessException e) {
            return new Result<>(ResultCode.FAIL_TO_DELETE_PRODUCT);
        } catch (Exception e) {
            return new Result<>(ResultCode.SYSTEM_ERROR);
        }
    }

    // DTO 변환 메서드: ProductRequestDTO -> ProductDTO
    private ProductDTO convertToDTO(ProductRequestDTO request) {
        return new ProductDTO(
                null,
                request.getName(),
                request.getPrice(),
                request.getInventory(),
                request.getDescription(),
                request.getImage(),
                null,
                null
        );
    }

    // DTO 변환 메서드: ProductDTO -> ProductResponseDTO
    private ProductResponseDTO convertToResponseDTO(ProductDTO product) {
        return new ProductResponseDTO(
                product.getProductId(),
                product.getName(),
                product.getPrice(),
                product.getInventory(),
                product.getDescription(),
                product.getImage(),
                product.getCreatedAt().toString(),
                product.getUpdatedAt().toString()
        );
    }
}


