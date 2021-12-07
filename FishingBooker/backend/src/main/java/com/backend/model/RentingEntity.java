package com.backend.model;

import javax.persistence.*;
import java.util.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class RentingEntity {

   @Id
   @SequenceGenerator(name = "entitySeqGen", sequenceName = "entitySeqGen", initialValue = 1, allocationSize = 1)
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "entitySeqGen")
   @Column(name="entity_id", unique=true, nullable=false)
   private Integer id;

   @Column(unique=true, nullable=false)
   private String name;

   @Column(unique=false, nullable=false)
   private String description;

   @Column(unique=false, nullable=false)
   private double averageGrade = 0;

   private double cancellationPercentage;

   @ElementCollection(fetch = FetchType.EAGER)
   @CollectionTable(name = "renting_entity_images", joinColumns = @JoinColumn(name = "entity_id"))
   @Column(name = "images")
   private Set<String> images = new HashSet<String>();

   @ElementCollection(fetch = FetchType.EAGER)
   @CollectionTable(name = "renting_entity_allowed_behavior", joinColumns = @JoinColumn(name = "entity_id"))
   @Column(name = "allowed_behaviour")
   private Set<String> allowedBehavior = new HashSet<String>();

   @ElementCollection(fetch = FetchType.EAGER)
   @CollectionTable(name = "renting_entity_unallowed_behavior", joinColumns = @JoinColumn(name = "entity_id"))
   @Column(name = "unallowed_behaviour")
   private Set<String> unallowedBehavior = new HashSet<String>();

   @OneToOne(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
   @JoinColumn(name = "address_id")
   private Address address;

   @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
   private Set<UnavailablePeriod> unavailablePeriod = new HashSet<UnavailablePeriod>();

   @OneToMany(mappedBy = "rentingEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
   private Set<PricelistItem> pricelistItem = new HashSet<PricelistItem>();

   @OneToMany(mappedBy = "rentingEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
   private Set<Sale> sale = new HashSet<Sale>();

   public RentingEntity() { }

   public RentingEntity(String name, String description, double averageGrade,Address address,Set<String> unallowedBehavior){
      this.name=name;
      this.description=description;
      this.averageGrade=averageGrade;
      this.address = address;
      this.unallowedBehavior = unallowedBehavior;
   }

   public RentingEntity(String name, String description, double averageGrade, double cancellationPercentage, Set<String> allowedBehavior, Set<String> unallowedBehavior, Address address) {
      this.name = name;
      this.description = description;
      this.averageGrade = averageGrade;
      this.cancellationPercentage = cancellationPercentage;
      this.allowedBehavior = allowedBehavior;
      this.unallowedBehavior = unallowedBehavior;
      this.address = address;
   }

   public RentingEntity(Integer id, String name, String description, Address address, double averageGrade) {
      this.id = id;
      this.name = name;
      this.description = description;
      this.averageGrade = averageGrade;
      this.address = address;
   }

   public Address getAddress() {
      return address;
   }

   public void setAddress(Address address) {
      this.address = address;
   }

   public Integer getId() {
      return id;
   }

   public Set<String> getUnallowedBehavior() {
      return unallowedBehavior;
   }

   public void setUnallowedBehavior(Set<String> unallowedBehavior) {
      this.unallowedBehavior = unallowedBehavior;
   }

   public void setId(Integer id) {
      this.id = id;
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

   public double getCancellationPercentage() { return cancellationPercentage; }

   public void setCancellationPercentage(double cancellationPercentage) { this.cancellationPercentage = cancellationPercentage; }

   public Set<String> getImages() {
      return images;
   }

   public void setImages(Set<String> images) {
      this.images = images;
   }

   public Set<String> getAllowedBehavior() {
      return allowedBehavior;
   }

   public void setAllowedBehavior(Set<String> allowedBehavior) {
      this.allowedBehavior = allowedBehavior;
   }

   @Override
   public String toString() {
      return "RentingEntity{}";
   }
}