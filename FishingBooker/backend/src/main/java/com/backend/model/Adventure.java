package com.backend.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Adventure extends RentingEntity {

   @Column(unique=true, nullable=false)
   private int maxPersons;

   @ElementCollection
   private Set<String> fishingEquipment = new HashSet<String>();

   @ManyToOne(fetch = FetchType.EAGER)
   @JoinColumn(name = "reg_user_id")
   private FishingInstructor fishingInstructor;

   public Adventure() {}

   public int getMaxPersons() {
      return maxPersons;
   }

   public void setMaxPersons(int maxPersons) {
      this.maxPersons = maxPersons;
   }

   public Set<String> getFishingEquipment() {
      return fishingEquipment;
   }

   public void setFishingEquipment(Set<String> fishingEquipment) {
      this.fishingEquipment = fishingEquipment;
   }

   public FishingInstructor getFishingInstructor() {
      return fishingInstructor;
   }

   public void setFishingInstructor(FishingInstructor fishingInstructor) {
      this.fishingInstructor = fishingInstructor;
   }
}