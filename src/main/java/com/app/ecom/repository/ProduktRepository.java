package com.app.ecom.repository;

import java.util.List;

import com.app.ecom.model.Produkt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProduktRepository extends JpaRepository<Produkt, Long>
{
  List<Produkt> findByActiveTrue();

  @Query("SELECT p FROM Produkt p WHERE p.active = true AND p.stockQuantity > 0 AND LOWER(p.name) LIKE LOWER( CONCAT('%', :keyword, '%'))")
  List<Produkt> searchProdukts(@Param("keyword") String keyword);
}
