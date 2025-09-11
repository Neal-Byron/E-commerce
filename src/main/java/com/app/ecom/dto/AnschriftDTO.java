package com.app.ecom.dto;

import lombok.Data;

@Data
public class AnschriftDTO
{
  private String strasse;
  private String hausnummer;
  private String plz;
  private String ort;
  private String land;
}
