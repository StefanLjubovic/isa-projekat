package com.backend.dto;

import com.backend.model.Address;
import com.backend.model.CottageOwner;
import com.backend.model.PricelistItem;
import com.backend.model.Room;

import java.util.HashSet;
import java.util.Set;

public class UpdateCottageDTO {

    private Integer id;
    private String name;
    private String description;
    private double cancellationPercentage;
    private Set<String> allowedBehavior = new HashSet<String>();
    private Set<String> unallowedBehavior = new HashSet<String>();
    private Address address;
    private Set<PricelistItem> pricelistItems = new HashSet<PricelistItem>();
    private Set<Room> rooms = new HashSet<Room>();
    private Set<String> images = new HashSet<>();

    public UpdateCottageDTO() { }

    public UpdateCottageDTO(Integer id, String name, String description, double cancellationPercentage,
                      Set<String> allowedBehavior, Set<String> unallowedBehavior, Address address,
                      Set<PricelistItem> pricelistItem, Set<Room> rooms, Set<String> images) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.cancellationPercentage = cancellationPercentage;
        this.allowedBehavior = allowedBehavior;
        this.unallowedBehavior = unallowedBehavior;
        this.address = address;
        this.pricelistItems = pricelistItem;
        this.rooms = rooms;
        this.images = images;
    }

    public Integer getId() { return id; }

    public void setId(Integer id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getDescription() {  return description;  }

    public void setDescription(String description) { this.description = description; }

    public double getCancellationPercentage() { return cancellationPercentage; }

    public void setCancellationPercentage(double cancellationPercentage) { this.cancellationPercentage = cancellationPercentage; }

    public Set<String> getAllowedBehavior() { return allowedBehavior; }

    public void setAllowedBehavior(Set<String> allowedBehavior) { this.allowedBehavior = allowedBehavior; }

    public Set<String> getUnallowedBehavior() { return unallowedBehavior; }

    public void setUnallowedBehavior(Set<String> unallowedBehavior) {  this.unallowedBehavior = unallowedBehavior; }

    public Address getAddress() { return address; }

    public void setAddress(Address address) { this.address = address;  }

    public Set<PricelistItem> getPricelistItems() { return pricelistItems;  }

    public void setPricelistItems(Set<PricelistItem> pricelistItems) {  this.pricelistItems = pricelistItems; }

    public Set<Room> getRooms() {  return rooms; }

    public void setRooms(Set<Room> rooms) {  this.rooms = rooms; }

    public Set<String> getImages() { return images; }

    public void setImages(Set<String> images) {  this.images = images; }
}
