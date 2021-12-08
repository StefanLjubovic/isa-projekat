package com.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

import javax.persistence.*;
import java.util.*;

@Entity
public class PricelistItem {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name="pricelist_item_id", unique=true, nullable=false)
   private Integer id;

   @Column(unique=false, nullable=false)
   private String service;

   @Column(unique=false, nullable=false)
   private double price;

   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "entity_id")
   @JsonIgnoreProperties("pricelistItems")
   private RentingEntity rentingEntity;

   public PricelistItem() {}

   public Integer getId() {
      return id;
   }

   public void setId(Integer id) {
      this.id = id;
   }

   public String getService() {
      return service;
   }

   public void setService(String service) {
      this.service = service;
   }

   public double getPrice() {
      return price;
   }

   public void setPrice(double price) {
      this.price = price;
   }

   public RentingEntity getRentingEntity() {
      return rentingEntity;
   }

   public void setRentingEntity(RentingEntity rentingEntity) {
      this.rentingEntity = rentingEntity;
   }

   @Override
   public String toString() {
      return "PricelistItem{}";
   }
}