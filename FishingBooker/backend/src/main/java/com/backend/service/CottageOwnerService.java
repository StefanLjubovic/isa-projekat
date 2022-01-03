package com.backend.service;

import com.backend.repository.ICottageRepository;
import com.backend.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class CottageOwnerService {

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private ICottageRepository cottageRepository;
}
