package com.backend.model;
import javax.persistence.*;
import java.util.*;

@Entity
@DiscriminatorValue("COTTAGE_OWNER")
public class CottageOwner extends RegistratedUser {

    @OneToMany(mappedBy = "cottageOwner", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Cottage> cottages = new HashSet<Cottage>();

    public CottageOwner() { }

    public Set<Cottage> getCottages() {
        return cottages;
    }

    public void setCottages(Set<Cottage> cottages) {
        this.cottages = cottages;
    }
}