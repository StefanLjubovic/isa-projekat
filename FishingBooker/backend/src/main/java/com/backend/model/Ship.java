/***********************************************************************
 * Module:  Ship.java
 * Author:  Ana Gavrilovic
 * Purpose: Defines the Class Ship
 ***********************************************************************/
package com.backend.model;
import java.util.*;

public class Ship extends Entity {
   private String type;
   private double length;
   private int engineNumber;
   private int enginePower;
   private double maxSpeed;
   private ArrayList<NavigationEquipment> navigationEquipment;
   private int capacity;
   private CancellationCondition cancellationCondition;

}