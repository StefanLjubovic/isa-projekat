package com.backend.model;
import javax.persistence.*;
import java.util.*;

@Entity
public class Report {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name="report_id", unique=true, nullable=false)
   private Integer id;

   @Column(unique=false, nullable=false)
   private String content;

   @Column(unique=false, nullable=false)
   private boolean isBadReview;

   @Column(unique=false, nullable=false)
   private boolean notAppeared;

   @ManyToOne(fetch = FetchType.EAGER)
   @JoinColumn(name = "reg_user_id")
   private Client client;

   @ManyToOne(fetch = FetchType.EAGER)
   @JoinColumn(name = "entity_id")
   private RentingEntity rentingEntity;

   public Report() { }

   public Integer getId() {
      return id;
   }

   public void setId(Integer id) {
      this.id = id;
   }

   public String getContent() {
      return content;
   }

   public void setContent(String content) {
      this.content = content;
   }

   public boolean isBadReview() {
      return isBadReview;
   }

   public void setBadReview(boolean badReview) {
      isBadReview = badReview;
   }

   public boolean isNotAppeared() {
      return notAppeared;
   }

   public void setNotAppeared(boolean notAppeared) {
      this.notAppeared = notAppeared;
   }

   public Client getClient() {
      return client;
   }

   public void setClient(Client client) {
      this.client = client;
   }

   public RentingEntity getRentingEntity() {
      return rentingEntity;
   }

   public void setRentingEntity(RentingEntity rentingEntity) {
      this.rentingEntity = rentingEntity;
   }
}