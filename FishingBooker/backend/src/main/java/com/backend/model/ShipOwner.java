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
public class ShipOwner extends RegisteredUser {

    @OneToMany(mappedBy = "shipOwner", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Ship> ships = new HashSet<Ship>();

    public ShipOwner() { }

    public ShipOwner(RegisteredUser user) {
        super(user);
    }

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