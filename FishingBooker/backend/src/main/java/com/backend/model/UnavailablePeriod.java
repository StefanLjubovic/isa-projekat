package com.backend.model;

import javax.persistence.*;
import java.util.*;

@Entity
public class UnavailablePeriod {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name="period_id", unique=true, nullable=false)
   private Integer id;

   @Column(unique=false, nullable=false)
   private Date fromDateTime;

   @Column(unique=false, nullable=false)
   private Date toDateTime;

   public UnavailablePeriod() {}

   public UnavailablePeriod(Date dateTime, Date endDate) {
      this.fromDateTime=dateTime;
      this.toDateTime=endDate;
   }

   public UnavailablePeriod(Integer id, Date fromDateTime, Date toDateTime) {
      this.id = id;
      this.fromDateTime = fromDateTime;
      this.toDateTime = toDateTime;
   }

   public Integer getId() {
      return id;
   }

   public void setId(Integer id) {
      this.id = id;
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

   @Override
   public String toString() {
      return "UnavailablePeriod{}";
   }
}