package com.backend.dto;

import com.backend.model.Address;
import com.backend.model.CottageOwner;
import com.backend.model.PricelistItem;
import com.backend.model.Room;

import java.util.HashSet;
import java.util.Set;

public class CottageDTO {

    private String name;
    private String description;
    private double cancellationPercentage;
    private Set<String> images = new HashSet<String>();
    private Set<String> allowedBehavior = new HashSet<String>();
    private Set<String> unallowedBehavior = new HashSet<String>();
    private Address address;
    private Set<PricelistItem> pricelistItem = new HashSet<PricelistItem>();
    private Set<Room> rooms = new HashSet<Room>();
    private CottageOwner cottageOwner;

    public CottageDTO() { }

    public CottageDTO(String name, String description, double cancellationPercentage, Set<String> images,
                      Set<String> allowedBehavior, Set<String> unallowedBehavior, Address address,
                      Set<PricelistItem> pricelistItem, Set<Room> rooms, CottageOwner cottageOwner) {
        this.name = name;
        this.description = description;
        this.cancellationPercentage = cancellationPercentage;
        this.images = images;
        this.allowedBehavior = allowedBehavior;
        this.unallowedBehavior = unallowedBehavior;
        this.address = address;
        this.pricelistItem = pricelistItem;
        this.rooms = rooms;
        this.cottageOwner = cottageOwner;
    }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getDescription() {  return description;  }

    public void setDescription(String description) { this.description = description; }

    public double getCancellationPercentage() { return cancellationPercentage; }

    public void setCancellationPercentage(double cancellationPercentage) { this.cancellationPercentage = cancellationPercentage; }

    public Set<String> getImages() { return images; }

    public void setImages(Set<String> images) {  this.images = images; }

    public Set<String> getAllowedBehavior() { return allowedBehavior; }

    public void setAllowedBehavior(Set<String> allowedBehavior) { this.allowedBehavior = allowedBehavior; }

    public Set<String> getUnallowedBehavior() { return unallowedBehavior; }

    public void setUnallowedBehavior(Set<String> unallowedBehavior) {  this.unallowedBehavior = unallowedBehavior; }

    public Address getAddress() { return address; }

    public void setAddress(Address address) { this.address = address;  }

    public Set<PricelistItem> getPricelistItem() { return pricelistItem;  }

    public void setPricelistItem(Set<PricelistItem> pricelistItem) {  this.pricelistItem = pricelistItem; }

    public Set<Room> getRooms() {  return rooms; }

    public void setRooms(Set<Room> rooms) {  this.rooms = rooms; }

    public CottageOwner getCottageOwner() {   return cottageOwner; }

    public void setCottageOwner(CottageOwner cottageOwner) { this.cottageOwner = cottageOwner;  }
}
