package com.backend.constants;

import java.security.Principal;

public class PrincipalCottageOwner implements Principal {
    @Override
    public String getName() {
        return "marijakljestan@gmail.com";
    }
}
