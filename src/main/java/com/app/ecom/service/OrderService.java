package com.app.ecom.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import aj.org.objectweb.asm.commons.Remapper;
import com.app.ecom.dto.OrderItemDTO;
import com.app.ecom.dto.OrderResponse;
import com.app.ecom.model.CartItem;
import com.app.ecom.model.Order;
import com.app.ecom.model.OrderItem;
import com.app.ecom.model.OrderStatus;
import com.app.ecom.model.User;
import com.app.ecom.repository.CartItemRepository;
import com.app.ecom.repository.OrderRepository;
import com.app.ecom.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService
{
  private final OrderRepository orderRepository;
  private final UserRepository userRepository;
  private final CartItemRepository cartItemRepository;
  private final CartService cartService;


  public Optional<OrderResponse> createOrder(String userId)
  {
    //Validate for Cart items
    List<CartItem> cartItemList = cartItemRepository.getCart(userId);
    if (cartItemList.isEmpty()){
      return Optional.empty();
    }

    //Validate User
    Optional<User> userOptional = userRepository.findById(Long.valueOf(userId));
    if (userOptional.isEmpty()){
      return Optional.empty();
    }
    User user = userOptional.get();

    //calculate total price
    BigDecimal totalPrice = cartItemList.stream()
            .map(CartItem::getUnitPrice)
            .reduce(BigDecimal.ZERO, BigDecimal::add);

    //create Order
    Order order = new Order();
    order.setUser(user);
    order.setStatus(OrderStatus.CONFIRMED);
    order.setTotalAmount(totalPrice);
    List<OrderItem> orderItems = cartItemList.stream()
                                             .map(item -> new OrderItem(
                                                     null,
                                                     item.getProdukt(),
                                                     item.getQuantity(),
                                                     item.getUnitPrice(),
                                                     order
                                             )).toList();
    order.setOrderItems(orderItems);
    Order savedOrder = orderRepository.save(order);

    //clear CartItem
    cartService.clearCart(userId);

    return Optional.of(mapToOrderResponse(savedOrder));

  }

  private OrderResponse mapToOrderResponse(Order savedOrder)
  {
    return new OrderResponse(
            savedOrder.getId(),
            savedOrder.getStatus(),
            savedOrder.getTotalAmount(),
            savedOrder.getOrderItems().stream()
                    .map(orderItem ->
                      new OrderItemDTO(
                              orderItem.getId(),
                              orderItem.getProdukt().getId(),
                              orderItem.getQuantity(),
                              orderItem.getPrice(),
                              orderItem.getPrice().multiply(new BigDecimal(orderItem.getQuantity()))

                      )
                    ).toList(),
            savedOrder.getCreatedAt()
    );
  }
}
