package com.app.ecom.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.app.ecom.model.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

@Data
@AllArgsConstructor
public class OrderResponse
{
  private Long orderId;
  private OrderStatus orderStatus;
  private BigDecimal totalAmount;
  private List<OrderItemDTO> items;

  @CreationTimestamp
  private LocalDateTime createdAt;
}
