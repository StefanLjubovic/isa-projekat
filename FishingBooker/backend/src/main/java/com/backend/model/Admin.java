package com.backend.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.*;

@Entity
@DiscriminatorValue("ADMIN")
public class Admin extends RegistratedUser {

    public Admin() { }
}