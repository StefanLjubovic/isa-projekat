package com.backend.model;
import javax.persistence.*;
import java.util.*;

@Entity
public class DeleteRequest {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name="delete_req_id", unique=true, nullable=false)
   private Integer id;

   @Column(unique=false, nullable=false)
   private String content;

   @OneToOne(fetch = FetchType.EAGER)
   @JoinColumn(name = "reg_user_id")
   private Client client;

   public DeleteRequest() { }

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

   public Client getClient() {
      return client;
   }

   public void setClient(Client client) {
      this.client = client;
   }
}