package com.app.ecom.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class ProduktResquest
{
  private String name;
  private String description;
  private BigDecimal price;
  private String imageUrl;
  private int stockQuantity;
  private String stockCategory;
}
