/***********************************************************************
 * Module:  FishingInstructor.java
 * Author:  Ana Gavrilovic
 * Purpose: Defines the Class FishingInstructor
 ***********************************************************************/
package com.backend.model;
import javax.persistence.*;
import java.util.*;

@Entity
@DiscriminatorValue("INSTRUCTOR")
public class FishingInstructor extends RegisteredUser {

   @Column(name="short_biography", unique=false, nullable=true)
   private String shortBiography;

   @OneToMany(mappedBy = "fishingInstructor", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
   private Set<Adventure> adventures = new HashSet<Adventure>();

   public FishingInstructor() { }

   public String getShortBiography() {
      return shortBiography;
   }

   public void setShortBiography(String shortBiography) {
      this.shortBiography = shortBiography;
   }

   public Set<Adventure> getAdventures() {
      return adventures;
   }

   public void setAdventures(Set<Adventure> adventures) {
      this.adventures = adventures;
   }
}