package com.backend.model;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.persistence.Entity;
import java.util.*;
import java.sql.Timestamp;
@Entity
@Inheritance(strategy= InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="type", discriminatorType=DiscriminatorType.STRING)
public class RegistratedUser implements UserDetails {

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

   @Column(name = "enabled")
   private boolean enabled;

   @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
   @JoinTable(name = "user_role",
           joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "reg_user_id"),
           inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
   private List<Role> roles;

   @Column(name = "last_password_reset_date")
   private Timestamp lastPasswordResetDate;

   @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
   @JoinColumn(name = "address_id")
   private Address address;

   public RegistratedUser() { }

   public RegistratedUser(String firstName, String lastName, String phoneNumber, String email, String password, UserStatus status, boolean enabled, List<Role> roles, Timestamp lastPasswordResetDate, Address address) {
      this.firstName = firstName;
      this.lastName = lastName;
      this.phoneNumber = phoneNumber;
      this.email = email;
      this.password = password;
      this.status = status;
      this.enabled = enabled;
      this.roles = roles;
      this.lastPasswordResetDate = lastPasswordResetDate;
      this.address = address;
   }

   public RegistratedUser(RegistratedUser user) {
      this.id = user.getId();
      this.firstName = user.getFirstName();
      this.lastName = user.getLastName();
      this.phoneNumber = user.getPhoneNumber();
      this.email = user.getEmail();
      this.password = user.getPassword();
      this.status = user.getStatus();
      this.enabled = user.isEnabled();
      this.roles = user.getRoles();
      this.lastPasswordResetDate = user.getLastPasswordResetDate();
      this.address = user.getAddress();
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

   public void setPassword(String password) {
      Timestamp now = new Timestamp(new Date().getTime());
      this.setLastPasswordResetDate(now);
      this.password = password;
   }

   public UserStatus getStatus() {
      return status;
   }

   public void setStatus(UserStatus status) {
      this.status = status;
   }

   public void setRoles(List<Role> roles) {
      this.roles = roles;
   }

   public List<Role> getRoles() {
      return roles;
   }

   public Address getAddress() {
      return address;
   }

   public void setAddress(Address address) {
      this.address = address;
   }

   @Override
   public Collection<? extends GrantedAuthority> getAuthorities() {
      return this.roles;
   }

   public String getPassword() {
      return password;
   }

   public Timestamp getLastPasswordResetDate() {
      return lastPasswordResetDate;
   }

   public void setLastPasswordResetDate(Timestamp lastPasswordResetDate) {
      this.lastPasswordResetDate = lastPasswordResetDate;
   }


   @Override
   public String getUsername() {
      return email;
   }

   @Override
   public boolean isAccountNonExpired() {
      return true;
   }

   @Override
   public boolean isAccountNonLocked() {
      return true;
   }

   @Override
   public boolean isCredentialsNonExpired() {
      return true;
   }

   @Override
   public boolean isEnabled() {
      return enabled;
   }

   public void setEnabled(boolean enabled) {
      this.enabled = enabled;
   }

   public String toString() {
      return "RegistratedUser{}";
   }
}