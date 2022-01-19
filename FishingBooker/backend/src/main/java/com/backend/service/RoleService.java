package com.backend.service;

import com.backend.model.Role;
import com.backend.repository.IRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {
    @Autowired
    private IRoleRepository roleRepository;

    public Role findById(Integer id) {
        Role auth = this.roleRepository.getOne(id);
        return auth;
    }

    public List<Role> findByName(String name) {
        List<Role> roles = this.roleRepository.findByName(name);
        return roles;
    }

    public Role findOneByName(String name) {
        Role roles = this.roleRepository.findOneByName(name);
        return roles;
    }
}
