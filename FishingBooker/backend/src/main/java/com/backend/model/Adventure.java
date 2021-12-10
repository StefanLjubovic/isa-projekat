package com.backend.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Adventure extends RentingEntity {

   @Column(unique=false, nullable=false)
   private int maxPersons;

   @ElementCollection(fetch = FetchType.EAGER)
   @CollectionTable(name = "adventure_fishing_equipment", joinColumns = @JoinColumn(name = "entity_id"))
   @Column(name = "fishing_equipment")
   private Set<String> fishingEquipment = new HashSet<String>();

   @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
   @JoinColumn(name = "reg_user_id")
   private FishingInstructor fishingInstructor;

   public Adventure(Integer id, String name, String description, Address address, double grade) {
      super(id, name, description, address, grade);
   }

   public Adventure(String name, String description, double averageGrade, Address address, double cancellationPercentage, Set<String> allowedBehaviour, Set<String> unallowedBehavior, int maxPersons, FishingInstructor instructor) {
      super(name, description, averageGrade, cancellationPercentage, allowedBehaviour,unallowedBehavior, address);
      this.maxPersons = maxPersons;
      this.fishingInstructor = instructor;
   }

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