/***********************************************************************
 * Module:  Reservation.java
 * Author:  Ana Gavrilovic
 * Purpose: Defines the Class Reservation
 ***********************************************************************/
package com.backend.model;
import java.util.*;

public class Reservation {
   private String id;
   private String entityId;
   private String clientEmail;
   private Date dateTime;
   private int durationInHours;
   private int maxPersons;
   private ArrayList<String> additionalServices;
   private double price;

}