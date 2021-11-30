package com.backend.model;

import javax.persistence.*;
import java.util.*;

@Entity
public class UnavaliablePeriod {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name="period_id", unique=true, nullable=false)
   private Integer id;

   @Column(unique=false, nullable=false)
   private Date fromDateTime;

   @Column(unique=false, nullable=false)
   private Date toDateTime;

   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "entity_id")
   private RentingEntity rentingEntity;

   public UnavaliablePeriod() {}

   public Integer getId() {
      return id;
   }

   public void setId(Integer id) {
      this.id = id;
   }

   public RentingEntity getRentingEntity() {
      return rentingEntity;
   }

   public void setRentingEntity(RentingEntity rentingEntity) {
      this.rentingEntity = rentingEntity;
   }

   public Date getFromDateTime() {
      return fromDateTime;
   }

   public void setFromDateTime(Date fromDateTime) {
      this.fromDateTime = fromDateTime;
   }

   public Date getToDateTime() {
      return toDateTime;
   }

   public void setToDateTime(Date toDateTime) {
      this.toDateTime = toDateTime;
   }
}