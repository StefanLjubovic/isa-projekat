/***********************************************************************
 * Module:  Reservation.java
 * Author:  Ana Gavrilovic
 * Purpose: Defines the Class Reservation
 ***********************************************************************/
package com.backend.model;
import javax.persistence.*;
import java.util.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Reservation {

   @Id
   @SequenceGenerator(name = "reservationSeqGen", sequenceName = "reservationSeqGen", initialValue = 1, allocationSize = 1)
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "reservationSeqGen")
   @Column(name="reservation_id", unique=true, nullable=false)
   private Integer id;

   @Column(unique=true, nullable=false)
   private Date dateTime;

   @Column(unique=false, nullable=false)
   private int durationInHours;

   @Column(unique=false, nullable=false)
   private int maxPersons;

   @ElementCollection(fetch = FetchType.EAGER)
   @CollectionTable(name = "reservation_additional_services", joinColumns = @JoinColumn(name = "reservation_id"))
   @Column(name = "additional_services")
   private Set<String> additionalServices = new HashSet<String>();

   @Column(unique=false, nullable=false)
   private double price;

   @Column(unique=false, nullable=false)
   private Boolean isCanceled = false;

   @ManyToOne(fetch = FetchType.EAGER)
   @JoinColumn(name = "reg_user_id")
   private Client client;

   @ManyToOne(fetch = FetchType.EAGER)
   @JoinColumn(name = "entity_id")
   private RentingEntity rentingEntity;

   public Reservation(Integer id, Date dateTime, int durationInHours, int maxPersons, double price, Client client, RentingEntity rentingEntity) {
      this.id = id;
      this.dateTime = dateTime;
      this.durationInHours = durationInHours;
      this.maxPersons = maxPersons;
      this.additionalServices = new HashSet<>();
      this.price = price;
      this.isCanceled = false;
      this.client = client;
      this.rentingEntity = rentingEntity;
   }

   public Reservation() { }

   public Integer getId() {
      return id;
   }

   public void setId(Integer id) {
      this.id = id;
   }

   public Date getDateTime() {
      return dateTime;
   }

   public void setDateTime(Date dateTime) {
      this.dateTime = dateTime;
   }

   public int getDurationInHours() {
      return durationInHours;
   }

   public void setDurationInHours(int durationInHours) {
      this.durationInHours = durationInHours;
   }

   public int getMaxPersons() {
      return maxPersons;
   }

   public void setMaxPersons(int maxPersons) {
      this.maxPersons = maxPersons;
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

   public Boolean getCanceled() {
      return isCanceled;
   }

   public void setCanceled(Boolean canceled) { isCanceled = canceled; }

   public Client getClient() {
      return client;
   }

   public void setClient(Client client) {
      this.client = client;
   }

   public RentingEntity getRentingEntity() {
      return rentingEntity;
   }

   public void setRentingEntity(RentingEntity rentingEntity) {
      this.rentingEntity = rentingEntity;
   }

   public Date getReservationEndTime() {
      Calendar cal = Calendar.getInstance();
      cal.setTime(this.getDateTime());
      cal.add(Calendar.HOUR_OF_DAY, this.getDurationInHours());
      return cal.getTime();
   }

   @Override
   public String toString() {
      return "Reservation{}";
   }
}