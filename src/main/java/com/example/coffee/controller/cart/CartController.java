package com.example.coffee.controller.cart;

import com.example.coffee.common.Result;
import com.example.coffee.model.cart.CartListDTO;
import com.example.coffee.model.cart.CartDTO;
import com.example.coffee.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CartController {
    @Autowired
    private CartService cartService;

    @GetMapping("/viewCartList/{cartId}")
    public Result viewCartList(@PathVariable Long cartId) {
        CartDTO to = new CartDTO();
        to.setCartId(cartId);
        return cartService.viewCartList(to);
    }


    @PostMapping("/addProductToCart")
    public Result addProductToCart(@RequestBody Long cartId, @RequestBody Long productId, @RequestBody int quantity, @RequestBody int price) {
        CartListDTO to = new CartListDTO();
        to.setCartId(cartId);
        to.setProductId(productId);
        to.setQuantity(quantity);
        to.setPrice(price);
        return cartService.addProductToCart(to);
    }

    @DeleteMapping("/deleteCartItem/{cartItemId}")
    public Result deleteCartList(@PathVariable Long cartItemId) {
        CartListDTO to = new CartListDTO();
        to.setCartItemId(cartItemId);
        return cartService.deleteCartList(to);
    }

    @PutMapping("/updateCartItem")
    public Result updateCartList(@RequestBody Long cartItemId, @RequestBody int quantity) {
        CartListDTO to = new CartListDTO();
        to.setCartItemId(cartItemId);
        to.setQuantity(quantity);
        return cartService.updateCartList(to);
    }
}