package com.backend.service;

import com.backend.model.SystemProperty;
import com.backend.repository.ISystemPropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class SystemPropertyService {

    @Autowired
    private ISystemPropertyRepository systemPropertyRepository;
    
    public Double getPercentage() {
        SystemProperty systemProperty = systemPropertyRepository.getIncomePercentage();
        return Double.parseDouble(systemProperty.getValue());
    }

    @Transactional
    public void updatePercentage(Double percentage) {
        systemPropertyRepository.updateIncomePercentage(percentage.toString());
    }

}
