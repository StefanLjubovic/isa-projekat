package com.backend.dto;

import com.backend.model.Address;
import com.backend.model.NavigationEquipment;
import com.backend.model.PricelistItem;

import java.util.HashSet;
import java.util.Set;

public class UpdateShipDTO {

    private Integer id;
    private String name;
    private String description;
    private double cancellationPercentage;
    private Set<String> images = new HashSet<>();
    private Set<String> allowedBehavior = new HashSet<String>();
    private Set<String> unallowedBehavior = new HashSet<String>();
    private Address address;
    private Set<PricelistItem> pricelistItems = new HashSet<PricelistItem>();
    private String type;
    private double length;
    private int engineNumber;
    private int enginePower;
    private double maxSpeed;
    private int capacity;
    private Set<NavigationEquipment> navigationEquipment = new HashSet<NavigationEquipment>();
    private Set<String> fishingEquipment = new HashSet<String>();


    public UpdateShipDTO() {}

    public UpdateShipDTO(Integer id, String name, String description, double cancellationPercentage, Set<String> images,
                         Address address, Set<String> allowedBehavior, Set<String> unallowedBehavior, Set<PricelistItem> pricelistItem,
                         String type, double length, int engineNumber, int enginePower, double maxSpeed, int capacity,
                         Set<NavigationEquipment> navigationEquipment, Set<String> fishingEquipment) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.cancellationPercentage = cancellationPercentage;
        this.images = images;
        this.allowedBehavior = allowedBehavior;
        this.unallowedBehavior = unallowedBehavior;
        this.address = address;
        this.pricelistItems = pricelistItem;
        this.type = type;
        this.length = length;
        this.engineNumber = engineNumber;
        this.enginePower = enginePower;
        this.maxSpeed = maxSpeed;
        this.capacity = capacity;
        this.navigationEquipment = navigationEquipment;
        this.fishingEquipment = fishingEquipment;
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

    public Set<String> getImages() { return images; }

    public void setImages(Set<String> images) {  this.images = images; }

    public String getType() { return type; }

    public void setType(String type) {  this.type = type; }

    public double getLength() { return length; }

    public void setLength(double length) {  this.length = length; }

    public int getEngineNumber() {  return engineNumber; }

    public void setEngineNumber(int engineNumber) { this.engineNumber = engineNumber; }

    public int getEnginePower() { return enginePower; }

    public void setEnginePower(int enginePower) { this.enginePower = enginePower; }

    public double getMaxSpeed() {  return maxSpeed; }

    public void setMaxSpeed(double maxSpeed) {  this.maxSpeed = maxSpeed; }

    public int getCapacity() { return capacity; }

    public void setCapacity(int capacity) {  this.capacity = capacity; }

    public Set<NavigationEquipment> getNavigationEquipment() { return navigationEquipment;  }

    public void setNavigationEquipment(Set<NavigationEquipment> navigationEquipment) { this.navigationEquipment = navigationEquipment; }

    public Set<String> getFishingEquipment() { return fishingEquipment; }

    public void setFishingEquipment(Set<String> fishingEquipment) { this.fishingEquipment = fishingEquipment; }
}

