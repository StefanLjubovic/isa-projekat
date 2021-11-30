package com.backend.model;

import javax.persistence.*;
import java.util.*;

@Entity
public class Sale {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name="sale_id", unique=true, nullable=false)
   private Integer id;

   @Column(unique=false, nullable=false)
   private Date dateTimeFrom;

   @Column(unique=false, nullable=false)
   private int durationInHours;

   @Column(unique=false, nullable=false)
   private int maximumPersons;

   @Column(unique=false, nullable=false)
   private Date expireDateTime;

   @ElementCollection
   private Set<String> additionalServices = new HashSet<String>();

   @Column(unique=false, nullable=false)
   private double price;

   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "entity_id")
   private RentingEntity rentingEntity;

   public Sale() {}

   public Integer getId() {
      return id;
   }

   public void setId(Integer id) {
      this.id = id;
   }

   public Date getDateTimeFrom() {
      return dateTimeFrom;
   }

   public void setDateTimeFrom(Date dateTimeFrom) {
      this.dateTimeFrom = dateTimeFrom;
   }

   public int getDurationInHours() {
      return durationInHours;
   }

   public void setDurationInHours(int durationInHours) {
      this.durationInHours = durationInHours;
   }

   public int getMaximumPersons() {
      return maximumPersons;
   }

   public void setMaximumPersons(int maximumPersons) {
      this.maximumPersons = maximumPersons;
   }

   public Date getExpireDateTime() {
      return expireDateTime;
   }

   public void setExpireDateTime(Date expireDateTime) {
      this.expireDateTime = expireDateTime;
   }

   public Set<String> getAdditionalServices() {
      return additionalServices;
   }

   public void setAdditionalServices(Set<String> additionalServices) {
      this.additionalServices = additionalServices;
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