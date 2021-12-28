/***********************************************************************
 * Module:  Role.java
 * Author:  Ana Gavrilovic
 * Purpose: Defines the Class Role
 ***********************************************************************/
package com.backend.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name="ROLE")
public class Role implements GrantedAuthority {

   private static final long serialVersionUID = 1L;

   @Id
   @Column(name="id")
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   Integer id;

   @Column(name="name")
   String name;

   public Role(String role) {
      this.name=role;
   }

   public Role() {}

   public Role(Integer id, String name) {
      this.id = id;
      this.name = name;
   }

   @JsonIgnore
   @Override
   @Column(unique = false,nullable = false)
   public String getAuthority() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getName() {
      return name;
   }

   @JsonIgnore
   public Integer getId() {
      return id;
   }

   public void setId(Integer id) {
      this.id = id;
   }

}