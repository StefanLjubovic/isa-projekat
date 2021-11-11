/***********************************************************************
 * Module:  Entity.java
 * Author:  Ana Gavrilovic
 * Purpose: Defines the Class Entity
 ***********************************************************************/
package com.backend.model;
import java.util.*;

public class Entity {
   private String id;
   private String ownerId;
   private String name;
   private String description;
   private double averageGrade;
   private ArrayList<String> images;
   private ArrayList<String> allowedBehavior;
   private ArrayList<String> unallowedBehavior;
   public Location location;

   public Entity(){}
   public Entity(String name, String description, double averageGrade){
      id="1";
      ownerId="2";
      images=new ArrayList<>();
      allowedBehavior=new ArrayList<>();
      unallowedBehavior=new ArrayList<>();
      this.name=name;
      this.description=description;
      this.averageGrade=averageGrade;
   }

   public Location getLocation() {
      return location;
   }

   public void setLocation(Location location) {
      this.location = location;
   }

   public String getId() {
      return id;
   }

   public ArrayList<String> getUnallowedBehavior() {
      return unallowedBehavior;
   }

   public void setUnallowedBehavior(ArrayList<String> unallowedBehavior) {
      this.unallowedBehavior = unallowedBehavior;
   }

   public void setId(String id) {
      this.id = id;
   }

   public String getOwnerId() {
      return ownerId;
   }

   public void setOwnerId(String ownerId) {
      this.ownerId = ownerId;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getDescription() {
      return description;
   }

   public void setDescription(String description) {
      this.description = description;
   }

   public double getAverageGrade() {
      return averageGrade;
   }

   public void setAverageGrade(double averageGrade) {
      this.averageGrade = averageGrade;
   }

   public ArrayList<String> getImages() {
      return images;
   }

   public void setImages(ArrayList<String> images) {
      this.images = images;
   }

   public ArrayList<String> getAllowedBehavior() {
      return allowedBehavior;
   }

   public void setAllowedBehavior(ArrayList<String> allowedBehavior) {
      this.allowedBehavior = allowedBehavior;
   }

}