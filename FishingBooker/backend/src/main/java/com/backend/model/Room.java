package com.backend.model;

import javax.persistence.*;
import java.util.*;

@Entity
public class Room {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name="room_id", unique=true, nullable=false)
   private Integer id;

   @Column(unique=false, nullable=false)
   private int bedNumber;

   public Room() {}

   public Integer getId() {
      return id;
   }

   public void setId(Integer id) {
      this.id = id;
   }

   public int getBedNumber() {
      return bedNumber;
   }

   public void setBedNumber(int bedNumber) {
      this.bedNumber = bedNumber;
   }

   @Override
   public String toString() {
      return "Room{}" + this.getBedNumber();
   }
}