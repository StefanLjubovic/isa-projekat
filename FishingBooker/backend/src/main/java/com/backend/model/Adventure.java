package com.backend.model;

import java.util.*;

public class Adventure extends Entity {
   private String instructorId;
   private int maxPersons;
   private CancellationCondition cancellationCondition;

   public Adventure(String name,String description,double averageGrade){
      super(name,description,averageGrade);
   }
   public Adventure(){}

   public String getInstructorId() {
      return instructorId;
   }

   public void setInstructorId(String instructorId) {
      this.instructorId = instructorId;
   }

   public int getMaxPersons() {
      return maxPersons;
   }

   public void setMaxPersons(int maxPersons) {
      this.maxPersons = maxPersons;
   }

   public CancellationCondition getCancellationCondition() {
      return cancellationCondition;
   }

   public void setCancellationCondition(CancellationCondition cancellationCondition) {
      this.cancellationCondition = cancellationCondition;
   }
}