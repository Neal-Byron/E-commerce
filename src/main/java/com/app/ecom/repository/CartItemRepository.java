package com.app.ecom.repository;

import java.util.List;

import com.app.ecom.model.CartItem;
import com.app.ecom.model.Produkt;
import com.app.ecom.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long>
{
  CartItem findByUserAndProdukt(User user, Produkt produkt);

  void deleteByUserAndProdukt(User user, Produkt produkt);

  List<CartItem> findByUser(User user);

  void deleteByUser(User user);
}
