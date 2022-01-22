package com.backend.constants;

import java.security.Principal;

public class PrincipalClient implements Principal {
    @Override
    public String getName() {
        return "user.fishingbooker+client1@gmail.com";
    }
}
