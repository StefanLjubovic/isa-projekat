/***********************************************************************
 * Module:  Ship.java
 * Author:  Ana Gavrilovic
 * Purpose: Defines the Class Ship
 ***********************************************************************/
package com.backend.model;
import javax.persistence.*;
import java.util.*;

@Entity
public class Ship extends RentingEntity {

   @Column(unique=true, nullable=false)
   private String type;

   @Column(unique=true, nullable=false)
   private double length;

   @Column(unique=true, nullable=false)
   private int engineNumber;

   @Column(unique=true, nullable=false)
   private int enginePower;

   @Column(unique=true, nullable=false)
   private double maxSpeed;

   @ElementCollection(fetch = FetchType.EAGER)
   @CollectionTable(name = "ship_navigation_equipment", joinColumns = @JoinColumn(name = "entity_id"))
   @Column(name = "navigation_equipment")
   private Set<NavigationEquipment> navigationEquipment = new HashSet<NavigationEquipment>();

   @Column(unique=true, nullable=false)
   private int capacity;

   @ElementCollection(fetch = FetchType.EAGER)
   @CollectionTable(name = "ship_fishing_equipment", joinColumns = @JoinColumn(name = "entity_id"))
   @Column(name = "fishing_equipment")
   private Set<String> fishingEquipment = new HashSet<String>();

   @ManyToOne(fetch = FetchType.EAGER)
   @JoinColumn(name = "reg_user_id")
   private ShipOwner shipOwner;

   public Ship() {}

   public Ship(int id,String name,String description,Address address,double grade) {
      super(id,name,description,address,grade);
   }

   public String getType() {
      return type;
   }

   public void setType(String type) {
      this.type = type;
   }

   public double getLength() {
      return length;
   }

   public void setLength(double length) {
      this.length = length;
   }

   public int getEngineNumber() {
      return engineNumber;
   }

   public void setEngineNumber(int engineNumber) {
      this.engineNumber = engineNumber;
   }

   public int getEnginePower() {
      return enginePower;
   }

   public void setEnginePower(int enginePower) {
      this.enginePower = enginePower;
   }

   public double getMaxSpeed() {
      return maxSpeed;
   }

   public void setMaxSpeed(double maxSpeed) {
      this.maxSpeed = maxSpeed;
   }

   public int getCapacity() {
      return capacity;
   }

   public void setCapacity(int capacity) {
      this.capacity = capacity;
   }

   public Set<NavigationEquipment> getNavigationEquipment() { return navigationEquipment; }

   public void setNavigationEquipment(Set<NavigationEquipment> navigationEquipment) { this.navigationEquipment = navigationEquipment; }

   public Set<String> getFishingEquipment() {
      return fishingEquipment;
   }

   public void setFishingEquipment(Set<String> fishingEquipment) {
      this.fishingEquipment = fishingEquipment;
   }

   public ShipOwner getShipOwner() {
      return shipOwner;
   }

   public void setShipOwner(ShipOwner shipOwner) {
      this.shipOwner = shipOwner;
   }

   @Override
   public String toString() {
      return "Ship{}";
   }
}