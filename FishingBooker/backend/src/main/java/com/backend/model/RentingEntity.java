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

   @Column(unique=true, nullable=false)
   private String description;

   @Column(unique=true, nullable=false)
   private double averageGrade = 0;

   private double cancellationPercentage;

   @ElementCollection
   private Set<String> images = new HashSet<String>();

   @ElementCollection
   private Set<String> allowedBehavior = new HashSet<String>();

   @ElementCollection
   private Set<String> unallowedBehavior = new HashSet<String>();

   @OneToOne(fetch = FetchType.EAGER)
   @JoinColumn(name = "address_id")
   private Address address;

   @OneToMany(mappedBy = "rentingEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
   private Set<UnavaliablePeriod> unvaliablePeriod = new HashSet<UnavaliablePeriod>();

   @OneToMany(mappedBy = "rentingEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
   private Set<PricelistItem> pricelistItem = new HashSet<PricelistItem>();

   @OneToMany(mappedBy = "rentingEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
   private Set<Sale> sale = new HashSet<Sale>();

   @ManyToMany(mappedBy = "subscriptions", fetch = FetchType.LAZY)
   private Set<Client> subcribedClients = new HashSet<Client>();

   public RentingEntity() { }

   public RentingEntity(String name, String description, double averageGrade){
      id = 1;
      this.name=name;
      this.description=description;
      this.averageGrade=averageGrade;
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

}