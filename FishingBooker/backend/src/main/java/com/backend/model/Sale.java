package com.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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

   @Column(unique=false, nullable=true)
   private String additionalServices;

   @Column(unique=false, nullable=false)
   private double price;

   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "entity_id")
   @JsonIgnoreProperties("sales")
   private RentingEntity rentingEntity;

   public Sale() {}

   public Sale(Date dateTimeFrom, int durationInHours, int maximumPersons, Date expireDateTime, String additionalServices, double price) {
      this.dateTimeFrom = dateTimeFrom;
      this.durationInHours = durationInHours;
      this.maximumPersons = maximumPersons;
      this.expireDateTime = expireDateTime;
      this.additionalServices = additionalServices;
      this.price = price;
   }

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

   public String getAdditionalServices() {
      return additionalServices;
   }

   public void setAdditionalServices(String additionalServices) {
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

   public Date getSaleEndTime() {
      Calendar cal = Calendar.getInstance();
      cal.setTime(this.getDateTimeFrom());
      cal.add(Calendar.HOUR_OF_DAY, this.getDurationInHours());
      return cal.getTime();
   }

   @Override
   public String toString() {
      return "Sale{}";
   }
}