package com.app.ecom.dto;

import lombok.Data;

@Data
public class CartItemRequest
{
  private Long produktId;
  private Integer quantity;
}
