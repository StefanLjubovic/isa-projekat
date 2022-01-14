package com.backend.model;

import javax.persistence.*;

@Entity
public class RegistrationRequest {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name="reg_request_id", unique=true, nullable=false)
   private Integer id;

   @Column(unique=false, nullable=false)
   private String firstName;

   @Column(unique=false, nullable=false)
   private String lastName;

   @Column(unique=false, nullable=false)
   private String phoneNumber;

   @Column(unique=false, nullable=false)
   private String email;

   @Column(unique=false, nullable=false)
   private String password;

   @ManyToOne(fetch = FetchType.EAGER)
   @JoinColumn(name = "role_id")
   private Role role;

   @Column(unique=false, nullable=false)
   private String explanation;

   @Column(unique = false, nullable = true)
   private String biography;

   @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
   @JoinColumn(name = "address_id")
   private Address address;

   public RegistrationRequest(String firstName, String lastName, String phoneNumber, String email, String password, Role role, String explanation, String biography, Address address) {
      this.firstName = firstName;
      this.lastName = lastName;
      this.phoneNumber = phoneNumber;
      this.email = email;
      this.password = password;
      this.role = role;
      this.explanation = explanation;
      this.biography = biography;
      this.address = address;
   }

   public RegistrationRequest() { }

   public RegistrationRequest(Integer id, String firstName, String lastName, String phoneNumber, String email, String password, String explanation) {
      this.id = id;
      this.firstName = firstName;
      this.lastName = lastName;
      this.phoneNumber = phoneNumber;
      this.email = email;
      this.password = password;
      this.explanation = explanation;
   }

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

   public Role getRole() {
      return role;
   }

   public void setRole(Role role) {
      this.role = role;
   }

   public String getExplanation() {
      return explanation;
   }

   public void setExplanation(String explanation) {
      this.explanation = explanation;
   }

   public String getBiography() { return biography; }

   public void setBiography(String biography) { this.biography = biography; }

   public Address getAddress() {
      return address;
   }

   public void setAddress(Address address) {
      this.address = address;
   }

   @Override
   public String toString() {
      return "RegistrationRequest{}";
   }
}