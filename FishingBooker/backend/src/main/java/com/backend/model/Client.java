/***********************************************************************
 * Module:  Client.java
 * Author:  Ana Gavrilovic
 * Purpose: Defines the Class Client
 ***********************************************************************/
package com.backend.model;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.*;

@Entity
@DiscriminatorValue("CLIENT")
public class Client extends RegisteredUser {

   @Column(name="points", unique=false, nullable=true)
   private double points = 0;

   @Column(name="cancellation_number", unique=false, nullable=true)
   private int cancellationNumber = 0;

   @Column(name="client_type", unique=false, nullable=true)
   private ClientType clientType = ClientType.bronze;

   @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
   @JoinTable(name = "subscriptions", joinColumns = @JoinColumn(name = "client_id", referencedColumnName = "reg_user_id"), inverseJoinColumns = @JoinColumn(name = "entity_id", referencedColumnName = "entity_id"))
   private Set<RentingEntity> subscriptions = new HashSet<RentingEntity>();

   public Client() { }

    public Client(RegistrationRequest userRequest) {
      super(userRequest);
    }
    public Client(RegisteredUser registeredUser) {
      super(registeredUser);
   }
    public double getPoints() {
      return points;
   }

   public void setPoints(double points) {
      this.points = points;
   }

   public int getCancellationNumber() {
      return cancellationNumber;
   }

   public void setCancellationNumber(int cancellationNumber) {
      this.cancellationNumber = cancellationNumber;
   }

   public ClientType getClientType() {
      return clientType;
   }

   public void setClientType(ClientType clientType) {
      this.clientType = clientType;
   }

    public Set<RentingEntity> getSubscriptions() {
        return subscriptions;
    }

    public void setSubscriptions(Set<RentingEntity> subscriptions) {
        this.subscriptions = subscriptions;
    }
}