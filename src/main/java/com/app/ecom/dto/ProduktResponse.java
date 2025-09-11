package com.app.ecom.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class ProduktResponse
{
  private Long id;
  private String name;
  private String description;
  private BigDecimal price;
  private String imageUrl;
  private String stockCategory;
  private int stockQuantity;
  private Boolean active;
}
