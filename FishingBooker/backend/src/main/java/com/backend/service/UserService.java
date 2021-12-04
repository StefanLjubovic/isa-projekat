package com.backend.service;

import com.backend.dto.UserRequest;
import com.backend.model.Admin;
import com.backend.model.RegistratedUser;
import com.backend.model.Role;
import com.backend.model.UserStatus;
import com.backend.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.sql.Timestamp;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleService roleService;

    public RegistratedUser findByEmail(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username);
    }

    public RegistratedUser findById(Integer id) throws AccessDeniedException {
        return userRepository.findById(id).orElseGet(null);
    }

    public List<RegistratedUser> findAll() throws AccessDeniedException {
        return userRepository.findAll();
    }

    public RegistratedUser save(UserRequest userRequest) {
        RegistratedUser u = new RegistratedUser();
        u.setPassword(passwordEncoder.encode(userRequest.getPassword()));

        u.setFirstName(userRequest.getFirstname());
        u.setLastName(userRequest.getLastname());
        u.setEnabled(true);
        u.setEmail(userRequest.getEmail());
        u.setStatus(userRequest.getStatus());
        u.setEnabled(true);
        u.setAddress(userRequest.getAddress());
        u.setPhoneNumber(userRequest.getPhoneNumber());
        List<Role> roles = roleService.findByName(userRequest.getRoleName());
        u.setRoles(roles);

        return this.userRepository.save(u);
    }

    public RegistratedUser saveAdmin(RegistratedUser newAdminUser) {
        Admin admin = new Admin(newAdminUser);

        List<Role> roles = roleService.findByName("ROLE_ADMIN");
        admin.setRoles(roles);
        admin.setPassword(passwordEncoder.encode(newAdminUser.getPassword()));
        admin.setLastPasswordResetDate(new Timestamp(System.currentTimeMillis()));
        admin.setEnabled(true);
        admin.setStatus(UserStatus.active);
        return this.userRepository.save(admin);
    }
}
