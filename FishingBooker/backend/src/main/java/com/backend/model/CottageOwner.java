package com.backend.model;
import javax.persistence.*;
import java.util.*;

@Entity
@DiscriminatorValue("COTTAGE_OWNER")
public class CottageOwner extends RegisteredUser {

    public CottageOwner() { }

    public CottageOwner(RegisteredUser user) {
        super(user);
    }
}