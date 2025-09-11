package com.app.ecom.model;

import java.time.LocalDateTime;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;

@Data
@NoArgsConstructor
@Entity(name="user_table")
public class User
{
  @Id
  @GeneratedValue(strategy= GenerationType.IDENTITY)
  private Long id;
  private String vorname;
  private String nachname;
  private String email;
  private String telefon;
  private UserRole userRole = UserRole.CUSTOMER;

  @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "anschrift_id", referencedColumnName = "id")
  private Anschrift anschrift;

  @CreationTimestamp
  private LocalDateTime createdAt;
  @UpdateTimestamp
  private LocalDateTime modifiedAt;
}
