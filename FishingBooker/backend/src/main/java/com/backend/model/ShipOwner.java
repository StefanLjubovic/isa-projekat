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

    public ShipOwner() { }

    public ShipOwner(RegisteredUser user) {
        super(user);
    }

    @Override
    public String toString() {
        return "ShipOwner{}";
    }
}