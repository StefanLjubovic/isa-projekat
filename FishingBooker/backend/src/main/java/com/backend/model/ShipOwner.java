/***********************************************************************
 * Module:  ShipOwner.java
 * Author:  Ana Gavrilovic
 * Purpose: Defines the Class ShipOwner
 ***********************************************************************/
package com.backend.model;
import javax.persistence.*;
import java.util.*;

@Entity
@DiscriminatorValue("SHIP_OWNER")
public class ShipOwner extends RegistratedUser {

    @OneToMany(mappedBy = "shipOwner", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Ship> ships = new HashSet<Ship>();

    public ShipOwner() { }

    public Set<Ship> getShips() {
        return ships;
    }

    public void setShips(Set<Ship> ships) {
        this.ships = ships;
    }

    @Override
    public String toString() {
        return "ShipOwner{}";
    }
}