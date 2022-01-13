package com.backend.service;

import com.backend.model.SystemProperty;
import com.backend.repository.ISystemPropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class SystemPropertyService {

    @Autowired
    private ISystemPropertyRepository systemPropertyRepository;

    @Transactional(readOnly = true)
    public SystemProperty getPercentage() {
        return systemPropertyRepository.getIncomePercentage();
    }

    @Transactional(readOnly = false)
    public void updatePercentage(SystemProperty percentage) {
        systemPropertyRepository.save(percentage);
    }

}
