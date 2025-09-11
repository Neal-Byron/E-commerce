package com.app.ecom.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class CartItemResponse
{
  private Long id;
  private Integer quantity;
  private BigDecimal unitPrice;
}
