package com.app.ecom.controller;

import java.util.List;

import com.app.ecom.dto.ProduktResponse;
import com.app.ecom.dto.ProduktResquest;
import com.app.ecom.model.Produkt;
import com.app.ecom.service.ProduktService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class ProduktController
{
  private final ProduktService produktService;

  @GetMapping("/api/produkts")
  private ResponseEntity<List<ProduktResponse>> fetchAllProdukt(){
    return new ResponseEntity<>(produktService.getAllProducts(), HttpStatus.OK);
  }

  @GetMapping("api/produkt/{id}")
  private ResponseEntity<ProduktResponse> fetchProduktById(@PathVariable("id") Long id)
  {
    return new ResponseEntity<>(produktService.getProductById(id), HttpStatus.OK);
  }

  @PostMapping("api/produkt")
  private ResponseEntity<ProduktResponse> createProdukt(@RequestBody ProduktResquest produkt){
    return new ResponseEntity<>(produktService.createProdukt
                                                      (produkt), HttpStatus.CREATED);
  }

  @PutMapping("api/produkt/{id}")
  private ResponseEntity<ProduktResponse> updateProdukt(@PathVariable("id") Long id, @RequestBody ProduktResquest produkt)
  {
    return new ResponseEntity<>(produktService.updateProdukt(id, produkt), HttpStatus.OK);
  }

  @DeleteMapping("api/produkt/{id}")
  private ResponseEntity<Boolean> deleteProdukt(@PathVariable("id") Long id){
    boolean deleted = produktService.deleteProdukt(id);
    return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
  }

  @GetMapping("api/produkts/search")
  private ResponseEntity<List<ProduktResponse>> searchProdukts(@RequestParam String keyword)
  {
    return new ResponseEntity<>(produktService.searchProducts(keyword), HttpStatus.OK);
  }
}
