package com.backend.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("ADMIN")
public class Admin extends RegisteredUser {

    private boolean initialPasswordChanged;

    public Admin() { }

    public Admin(RegisteredUser user, boolean initialPasswordChanged) {
        super(user);
        this.initialPasswordChanged = initialPasswordChanged;
    }

    public boolean isInitialPasswordChanged() {
        return initialPasswordChanged;
    }

    public void setInitialPasswordChanged(boolean initialPasswordChanged) {
        this.initialPasswordChanged = initialPasswordChanged;
    }
}