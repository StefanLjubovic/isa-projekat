package com.backend.model;
import javax.persistence.*;
import java.util.*;

@Entity
public class Revision {

   @Id
   @SequenceGenerator(name = "revisionSeqGen", sequenceName = "revisionSeqGen", initialValue = 1, allocationSize = 1)
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "revisionSeqGen")
   @Column(name="revision_id", unique=true, nullable=false)
   private Integer id;

   @Column(unique=false, nullable=false)
   private String content;

   @Column(unique=false, nullable=false)
   private int mark;

   @Column(unique=false, nullable=false)
   private Boolean isApproved;

   @OneToOne(fetch = FetchType.EAGER)
   @JoinColumn(name = "reservation_id")
   private Reservation reservation;

   public Revision() { }

   public Revision(String content, int mark, Boolean isApproved, Reservation reservation) {
      this.content = content;
      this.mark = mark;
      this.isApproved = isApproved;
      this.reservation = reservation;
   }

   public Integer getId() { return id; }

   public void setId(Integer id) { this.id = id; }

   public String getContent() { return content; }

   public void setContent(String content) { this.content = content; }

   public int getMark() { return mark; }

   public void setMark(int mark) { this.mark = mark; }

   public Boolean getApproved() { return isApproved; }

   public void setApproved(Boolean approved) { isApproved = approved; }

   public Reservation getReservation() { return reservation; }

   public void setReservation(Reservation reservation) { this.reservation = reservation; }

   @Override
   public String toString() {
      return "Revision{}";
   }
}