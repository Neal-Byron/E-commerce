package com.app.ecom.service;

import java.util.List;
import java.util.stream.Collectors;

import com.app.ecom.dto.ProduktResponse;
import com.app.ecom.dto.ProduktResquest;
import com.app.ecom.model.Produkt;
import com.app.ecom.repository.ProduktRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProduktService
{
  private final ProduktRepository produktRepository;

  public List<ProduktResponse> getAllProducts(){
    return produktRepository.findByActiveTrue().stream().map(this::mapToProduktResponse).collect(Collectors.toList());
  }

  public ProduktResponse getProductById(Long id)
  {
    return produktRepository.findById(id).map(this::mapToProduktResponse).orElse(null);
  }

  public ProduktResponse createProdukt(ProduktResquest produktResquest){

      Produkt produkt = new Produkt();
      updateProduktFromRequest(produkt, produktResquest);
      Produkt savedProdukt = produktRepository.save(produkt);
      return mapToProduktResponse(savedProdukt);

  }

  public ProduktResponse updateProdukt(Long id, ProduktResquest produkt)
  {

    return produktRepository.findById(id).map(existingProdukt->{
                                          updateProduktFromRequest(existingProdukt, produkt);
                                          Produkt savedProdukt = produktRepository.save(existingProdukt);
                                          return mapToProduktResponse(savedProdukt);} )
                                          .orElseThrow(() -> new RuntimeException("Produkt wurde nicht gefunden " + id));
  }

  public boolean deleteProdukt(Long id){
    return produktRepository.findById(id)
            .map(produkt -> {
              produkt.setActive(false);
              produktRepository.save(produkt);
              return true;
            }).orElse(false);
  }

  public List<ProduktResponse> searchProducts(String keyword)
  {
    return produktRepository.searchProdukts(keyword).stream().map(this::mapToProduktResponse).collect(Collectors.toList());
  }


  private ProduktResponse mapToProduktResponse(Produkt produkt)
  {
    ProduktResponse produktResponse = new ProduktResponse();
    produktResponse.setId(produkt.getId());
    produktResponse.setName(produkt.getName());
    produktResponse.setDescription(produkt.getDescription());
    produktResponse.setPrice(produkt.getPrice());
    produktResponse.setImageUrl(produkt.getImageUrl());
    produktResponse.setStockCategory(produkt.getStockCategory());
    produktResponse.setStockQuantity(produkt.getStockQuantity());
    produktResponse.setActive(produkt.getActive());
    return produktResponse;
  }

  private void updateProduktFromRequest(Produkt produkt, ProduktResquest produktResquest)
  {
    produkt.setName(produktResquest.getName());
    produkt.setDescription(produktResquest.getDescription());
    produkt.setPrice(produktResquest.getPrice());
    produkt.setImageUrl(produktResquest.getImageUrl());
    produkt.setStockCategory(produktResquest.getStockCategory());
    produkt.setStockQuantity(produktResquest.getStockQuantity());
  }

}
