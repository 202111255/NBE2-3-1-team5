package com.example.coffee.repository;

import com.example.coffee.model.product.ProductDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ProductMapper {
    List<ProductDTO> findAll();
    ProductDTO findById(@Param("productId") Long productId);
    void save(ProductDTO product);
    void update(ProductDTO product);
    void delete(@Param("productId") Long productId);
}
