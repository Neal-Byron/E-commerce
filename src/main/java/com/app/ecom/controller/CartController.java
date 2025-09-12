package com.app.ecom.controller;

import java.util.List;

import com.app.ecom.dto.CartItemRequest;
import com.app.ecom.dto.CartItemResponse;
import com.app.ecom.model.CartItem;
import com.app.ecom.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@Controller
@RequiredArgsConstructor
public class CartController
{
  private final CartService cartService;

  @PostMapping("api/cart")
  public ResponseEntity<String> addCartItem(
          @RequestHeader("X-User-ID") String userId,
          @RequestBody CartItemRequest cartItemRequest){
    boolean created = cartService.addToCart(userId, cartItemRequest);
    if (created){
      return new ResponseEntity<>(HttpStatus.CREATED);
    }
    return ResponseEntity.badRequest().body("Product Out of Stock ou User not found or Produkt not found");

  }

  @DeleteMapping("api/cart/{produktId}")
  public ResponseEntity<Void> removeCartItem(@RequestHeader("X-User-ID") String userID, @PathVariable Long produktId){
    boolean deleted = cartService.deleteByUserAndProdukt(userID, produktId);
    return deleted ? ResponseEntity.noContent().build() : ResponseEntity.badRequest().build();
  }

  @GetMapping("api/carts")
  public ResponseEntity<List<CartItem>> fetchCartItemResponse(@RequestHeader("X-user-ID") String userID){
    return new ResponseEntity<>(cartService.fetchAllCartItem(userID), HttpStatus.OK);
  }

}
