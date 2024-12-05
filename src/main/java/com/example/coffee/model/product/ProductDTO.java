package com.example.coffee.model.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    private Long productId;       // 상품 ID (Primary Key)
    private String name;          // 상품 이름
    private int price;            // 상품 가격
    private int inventory;        // 재고 수량
    private String description;   // 상품 설명
    private String image;         // 상품 이미지 URL
    private LocalDateTime createdAt; // 등록일
    private LocalDateTime updatedAt; // 수정일
}

