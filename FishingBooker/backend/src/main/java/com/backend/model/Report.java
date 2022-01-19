package com.backend.model;
import javax.persistence.*;
import java.util.*;

@Entity
public class Report {

   @Id
   @SequenceGenerator(name = "reportSeqGen", sequenceName = "reportSeqGen", initialValue = 1, allocationSize = 1)
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "reportSeqGen")
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

   public Report(String content, boolean isBadReview, boolean notAppeared, Client client, RentingEntity rentingEntity) {
      this.content = content;
      this.isBadReview = isBadReview;
      this.notAppeared = notAppeared;
      this.client = client;
      this.rentingEntity = rentingEntity;
   }

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

   @Override
   public String toString() {
      return "Report{}";
   }
}