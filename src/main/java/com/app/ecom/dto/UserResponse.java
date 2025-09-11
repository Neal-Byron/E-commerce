package com.app.ecom.dto;

import com.app.ecom.model.UserRole;
import lombok.Data;

@Data
public class UserResponse
{
  private String id;
  private String vorname;
  private String nachname;
  private String email;
  private String telefon;
  private UserRole userRole;
  private AnschriftDTO anschrift;
}
