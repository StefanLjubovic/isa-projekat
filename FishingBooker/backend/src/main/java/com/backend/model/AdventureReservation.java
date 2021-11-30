package com.backend.model;
import javax.persistence.Column;
import java.util.*;

public class AdventureReservation extends Reservation {

   @Column(unique=true, nullable=false)
   private String place;

   public AdventureReservation() { }

   public String getPlace() {
      return place;
   }

   public void setPlace(String place) {
      this.place = place;
   }
}