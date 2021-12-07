/***********************************************************************
 * Module:  FishingInstructor.java
 * Author:  Ana Gavrilovic
 * Purpose: Defines the Class FishingInstructor
 ***********************************************************************/
package com.backend.model;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.*;

@Entity
@DiscriminatorValue("INSTRUCTOR")
public class FishingInstructor extends RegisteredUser {

   @Column(name="short_biography", unique=false, nullable=true)
   private String shortBiography;

   @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
   @JsonIgnore
   private Set<UnavailablePeriod> unavailablePeriod = new HashSet<UnavailablePeriod>();

   public FishingInstructor() { }

   public FishingInstructor(RegisteredUser user, String shortBiography) {
      super(user);
      this.shortBiography = shortBiography;
   }

   public String getShortBiography() {
      return shortBiography;
   }

   public void setShortBiography(String shortBiography) {
      this.shortBiography = shortBiography;
   }

   public Set<UnavailablePeriod> getUnavailablePeriod() {
      return unavailablePeriod;
   }

   public void setUnavailablePeriod(Set<UnavailablePeriod> unavailablePeriod) {
      this.unavailablePeriod = unavailablePeriod;
   }
}