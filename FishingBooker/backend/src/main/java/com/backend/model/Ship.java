/***********************************************************************
 * Module:  Ship.java
 * Author:  Ana Gavrilovic
 * Purpose: Defines the Class Ship
 ***********************************************************************/
package com.backend.model;
import java.util.*;

public class Ship extends Entity {
   private String type;
   private double length;
   private int engineNumber;
   private int enginePower;
   private double maxSpeed;
   private ArrayList<NavigationEquipment> navigationEquipment;
   private int capacity;

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

   public ArrayList<NavigationEquipment> getNavigationEquipment() {
      return navigationEquipment;
   }

   public void setNavigationEquipment(ArrayList<NavigationEquipment> navigationEquipment) {
      this.navigationEquipment = navigationEquipment;
   }

   public int getCapacity() {
      return capacity;
   }

   public void setCapacity(int capacity) {
      this.capacity = capacity;
   }

   public CancellationCondition getCancellationCondition() {
      return cancellationCondition;
   }

   public void setCancellationCondition(CancellationCondition cancellationCondition) {
      this.cancellationCondition = cancellationCondition;
   }

   private CancellationCondition cancellationCondition;

   public Ship(String name,String description,double averageGrade){
      super(name,description,averageGrade);
   }
}