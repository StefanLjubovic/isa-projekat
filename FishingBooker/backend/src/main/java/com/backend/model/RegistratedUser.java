/***********************************************************************
 * Module:  RegistratedUser.java
 * Author:  Ana Gavrilovic
 * Purpose: Defines the Class RegistratedUser
 ***********************************************************************/
package com.backend.model;
import javax.persistence.*;
import javax.persistence.Entity;
import java.util.*;

@Entity
@Inheritance(strategy= InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="type", discriminatorType=DiscriminatorType.STRING)
public class RegistratedUser {

   @Id
   @SequenceGenerator(name = "userSeqGen", sequenceName = "userSeqGen", initialValue = 1, allocationSize = 1)
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "userSeqGen")
   @Column(name="reg_user_id", unique=true, nullable=false)
   private Integer id;

   @Column(name="first_name", unique=false, nullable=false)
   private String firstName;

   @Column(name="last_name", unique=false, nullable=false)
   private String lastName;

   @Column(name="phone_number", unique=false, nullable=false)
   private String phoneNumber;

   @Column(name="email", unique=true, nullable=false)
   private String email;

   @Column(name="password", unique=false, nullable=false)
   private String password;

   @Column(name="user_status", unique=false, nullable=false)
   private UserStatus status;

   @Column(name="role", unique=false, nullable=false)
   private Role role;

   @ManyToOne(fetch = FetchType.EAGER)
   @JoinColumn(name = "address_id")
   private Address address;

   public RegistratedUser() { }

   public Integer getId() {
      return id;
   }

   public void setId(Integer id) {
      this.id = id;
   }

   public String getFirstName() {
      return firstName;
   }

   public void setFirstName(String firstName) {
      this.firstName = firstName;
   }

   public String getLastName() {
      return lastName;
   }

   public void setLastName(String lastName) {
      this.lastName = lastName;
   }

   public String getPhoneNumber() {
      return phoneNumber;
   }

   public void setPhoneNumber(String phoneNumber) {
      this.phoneNumber = phoneNumber;
   }

   public String getEmail() {
      return email;
   }

   public void setEmail(String email) {
      this.email = email;
   }

   public String getPassword() {
      return password;
   }

   public void setPassword(String password) {
      this.password = password;
   }

   public UserStatus getStatus() {
      return status;
   }

   public void setStatus(UserStatus status) {
      this.status = status;
   }

   public Role getRole() {
      return role;
   }

   public void setRole(Role role) {
      this.role = role;
   }

   public Address getAddress() {
      return address;
   }

   public void setAddress(Address address) {
      this.address = address;
   }

   @Override
   public String toString() {
      return "RegistratedUser{}";
   }
}