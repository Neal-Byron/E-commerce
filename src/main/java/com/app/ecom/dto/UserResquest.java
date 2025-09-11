package com.app.ecom.dto;

import lombok.Data;

@Data
public class UserResquest
{
  private String vorname;
  private String nachname;
  private String email;
  private String telefon;
  private AnschriftDTO anschrift;
}
