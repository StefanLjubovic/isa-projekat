/***********************************************************************
 * Module:  Cottage.java
 * Author:  Ana Gavrilovic
 * Purpose: Defines the Class Cottage
 ***********************************************************************/
package com.backend.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Cottage extends RentingEntity {

    @OneToMany(mappedBy = "cottage", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Room> rooms = new HashSet<Room>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "reg_user_id")
    private CottageOwner cottageOwner;

    public Cottage() {}

    public Set<Room> getRooms() {
        return rooms;
    }

    public void setRooms(Set<Room> rooms) {
        this.rooms = rooms;
    }

    public CottageOwner getCottageOwner() {
        return cottageOwner;
    }

    public void setCottageOwner(CottageOwner cottageOwner) {
        this.cottageOwner = cottageOwner;
    }
}