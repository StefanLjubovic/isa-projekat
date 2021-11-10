/***********************************************************************
 * Module:  RegistratedUser.java
 * Author:  Ana Gavrilovic
 * Purpose: Defines the Class RegistratedUser
 ***********************************************************************/
package com.backend.model;
import java.util.*;

public class RegistratedUser {
   private String firstName;
   private String lastName;
   private String phoneNumber;
   private String email;
   private String password;
   private UserStatus status;
   private Role role;
   public Address address;

}