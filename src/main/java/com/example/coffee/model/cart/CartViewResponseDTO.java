package com.example.coffee.model.cart;



import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@JsonPropertyOrder({ "cartViewResponseDTOS", "totalQuantity", "totalPrice"})
public class CartViewResponseDTO {
    private List<ViewCartListDTO> CartViewResponseDTOS;
    private int totalQuantity;
    private int totalPrice;
}
