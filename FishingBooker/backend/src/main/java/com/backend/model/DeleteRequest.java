package com.backend.model;
import javax.persistence.*;
import java.util.*;

@Entity
public class DeleteRequest {

   @Id
   @SequenceGenerator(name = "deleteRequestSeqGen", sequenceName = "deleteRequestSeqGen", initialValue = 1, allocationSize = 1)
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "deleteRequestSeqGen")
   @Column(name="delete_req_id", unique=true, nullable=false)
   private Integer id;

   @Column(unique=false, nullable=false)
   private String content;

   @OneToOne(fetch = FetchType.EAGER)
   @JoinColumn(name = "reg_user_id")
   private RegisteredUser user;

   public DeleteRequest() { }

   public DeleteRequest(RegisteredUser user,String content) {
      this.content = content;
      this.user = user;
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

   public RegisteredUser getRegisteredUser() {
      return user;
   }

   public void setRegisteredUser(RegisteredUser user) {
      this.user = user;
   }
}