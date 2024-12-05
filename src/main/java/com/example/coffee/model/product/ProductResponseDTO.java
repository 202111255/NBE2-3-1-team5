package com.example.coffee.model.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponseDTO {
    private Long productId;       // 상품 ID
    private String name;          // 상품 이름
    private int price;            // 상품 가격
    private int inventory;        // 재고 수량
    private String description;   // 상품 설명
    private String image;         // 상품 이미지 URL
    private String createdAt; // 등록일
    private String updatedAt; // 수정일
}

// 조회 응답 DTO