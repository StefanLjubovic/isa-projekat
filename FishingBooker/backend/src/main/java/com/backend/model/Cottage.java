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

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Room> rooms = new HashSet<Room>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "reg_user_id")
    private CottageOwner cottageOwner;

    public Cottage() {}

    public Cottage(int id,String name,String description,Address address,double grade) {
        super(id,name,description,address,grade);
    }

    public Cottage(String name, String description, double averageGrade,Address address,Set<String> unallowedBehavior) {
        super(name, description, averageGrade, address, unallowedBehavior);
    }

    public Cottage (RentingEntity rentingEntity, CottageOwner cottageOwner){
        super(rentingEntity);
        this.cottageOwner = cottageOwner;
    }

    public Cottage(RentingEntity entity) {
        super(entity);
    }

    public Set<Room> getRooms() {
        return rooms;
    }

    public void setRooms(Set<Room> rooms) {
        this.rooms = rooms;
    }

    public CottageOwner getCottageOwner() {
        return cottageOwner;
    }

    public void setCottageOwner(CottageOwner cottageOwner) { this.cottageOwner = cottageOwner; }

    @Override
    public String toString() {
        return "Cottage{}";
    }
}