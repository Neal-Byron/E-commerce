package com.app.ecom.service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.app.ecom.dto.CartItemRequest;
import com.app.ecom.dto.CartItemResponse;
import com.app.ecom.model.CartItem;
import com.app.ecom.model.Produkt;
import com.app.ecom.model.User;
import com.app.ecom.repository.CartItemRepository;
import com.app.ecom.repository.ProduktRepository;
import com.app.ecom.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CartService
{
  private final UserRepository userRepository;
  private final ProduktRepository produktRepository;
  private final CartItemRepository cartItemRepository;

  public boolean addToCart(String userId, CartItemRequest cartItemRequest){

    Optional<User> userOptional = userRepository.findById(Long.valueOf(userId));
    if(userOptional.isEmpty()){
      return false;
    }
    User user = userOptional.get();

    Optional<Produkt> produktOptional = produktRepository.findById(cartItemRequest.getProduktId());
    if(produktOptional.isEmpty()){
      return false;
    }
    Produkt produkt = produktOptional.get();

    if(produkt.getStockQuantity() < cartItemRequest.getQuantity()){
      return false;
    }

    CartItem cartItem = cartItemRepository.findByUserAndProdukt(user, produkt);
    if(cartItem != null){
      cartItem.setQuantity(cartItem.getQuantity() + cartItemRequest.getQuantity());
    }else{
      cartItem = new CartItem();
      cartItem.setUser(user);
      cartItem.setProdukt(produkt);
      cartItem.setQuantity(cartItemRequest.getQuantity());
    }
    cartItem.setUnitPrice(produkt.getPrice().multiply(new BigDecimal(cartItemRequest.getQuantity())));
    cartItemRepository.save(cartItem);
    return true;
  }

  @Transactional
  public boolean deleteByUserAndProdukt(String userID, Long productId)
  {
    Optional<User> userOptional = userRepository.findById(Long.valueOf(userID));
    Optional<Produkt> produktOptional = produktRepository.findById(productId);


    /*userOptional.flatMap(user -> produktOptional.map(produkt -> {
                                          cartItemRepository.deleteByUserAndProdukt(user, produkt);
                                          return true;
    }));*/
    if (userOptional.isPresent() && produktOptional.isPresent())
    {
      cartItemRepository.deleteByUserAndProdukt(userOptional.get(), produktOptional.get());
      return true;
    }

    return false;
  }

  public List<CartItem> fetchAllCartItem(String userID)
  {
    return userRepository.findById(Long.valueOf(userID))
            .map(cartItemRepository::findByUser)
            .orElseGet(List::of);

  }

  @Transactional
  public void clearCart(String userId)
  {
    userRepository.findById(Long.valueOf(userId)).ifPresent(cartItemRepository::deleteByUser
            );
  }
}
