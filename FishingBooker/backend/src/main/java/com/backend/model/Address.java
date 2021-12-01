package com.backend.model;

import javax.persistence.*;
import javax.persistence.Entity;

@Entity
public class Address {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name="address_id", unique=true, nullable=false)
   private Integer id;

   @Column(name="street_name", unique=false, nullable=false)
   private String streetName;

   @Column(name="street_number", unique=false, nullable=false)
   private String streetNumber;

   @Column(name="postal_code", unique=false, nullable=false)
   private String postalCode;

   @Column(name="city", unique=false, nullable=false)
   private String city;

   @Column(name="country", unique=false, nullable=false)
   private String country;

   private double longitude;
   private double latitude;

   public Address() { }

   public Address(String streetName, String streetNumber, String postalCode, String city, String country) {
      this.streetName = streetName;
      this.streetNumber = streetNumber;
      this.postalCode = postalCode;
      this.city = city;
      this.country = country;
   }

   public Integer getId() {
      return id;
   }

   public void setId(Integer id) {
      this.id = id;
   }

   public String getStreetName() {
      return streetName;
   }

   public void setStreetName(String streetName) {
      this.streetName = streetName;
   }

   public String getStreetNumber() {
      return streetNumber;
   }

   public void setStreetNumber(String streetNumber) {
      this.streetNumber = streetNumber;
   }

   public String getPostalCode() {
      return postalCode;
   }

   public void setPostalCode(String postalCode) {
      this.postalCode = postalCode;
   }

   public String getCity() {
      return city;
   }

   public void setCity(String city) {
      this.city = city;
   }

   public String getCountry() {
      return country;
   }

   public void setCountry(String country) {
      this.country = country;
   }

   public double getLongitude() {
      return longitude;
   }

   public void setLongitude(double longitude) {
      this.longitude = longitude;
   }

   public double getLatitude() {
      return latitude;
   }

   public void setLatitude(double latitude) {
      this.latitude = latitude;
   }
}