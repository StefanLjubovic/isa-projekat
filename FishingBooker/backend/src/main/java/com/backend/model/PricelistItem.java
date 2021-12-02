package com.backend.model;

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
}