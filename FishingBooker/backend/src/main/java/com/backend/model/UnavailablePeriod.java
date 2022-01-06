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

   public boolean overlapsWithExistingUnavailablePeriods(Set<UnavailablePeriod> unavailablePeriods) {
      for (UnavailablePeriod period : unavailablePeriods)
         if (period.getFromDateTime().before(this.getToDateTime()) && period.getToDateTime().after(this.getFromDateTime()))
            return true;

      return false;
   }

   public boolean overlapsWithExistingReservations(List<Reservation> reservations) {
      for(Reservation r : reservations)
         if (r.getDateTime().before(this.getToDateTime()) && r.getReservationEndTime().after(this.getFromDateTime()))
            return true;

      return false;
   }

   public boolean overlapsWithExistingSales(Set<Sale> sales) {
      for(Sale sale : sales)
         if (sale.getDateTimeFrom().before(this.getToDateTime()) && sale.getSaleEndTime().after(this.getFromDateTime()))
               return true;

      return false;
   }

   @Override
   public String toString() {
      return "UnavailablePeriod{}";
   }
}