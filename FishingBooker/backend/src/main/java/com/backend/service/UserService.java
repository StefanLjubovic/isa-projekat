package com.backend.service;

import com.backend.dto.UserRequest;
import com.backend.model.*;
import com.backend.repository.IRegistrationRequestRepository;
import com.backend.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.nio.file.AccessDeniedException;
import java.sql.Timestamp;
import java.util.List;

@Service
@Transactional
public class UserService {

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleService roleService;

    @Autowired
    private IRegistrationRequestRepository registrationRequestRepository;

    public RegisteredUser findByEmail(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username);
    }

    public RegisteredUser findById(Integer id) throws AccessDeniedException {
        return userRepository.findById(id).orElseGet(null);
    }

    public List<RegisteredUser> findAll() throws AccessDeniedException {
        return userRepository.findAll();
    }

    public RegistrationRequest saveRequest(UserRequest userRequest){
        RegistrationRequest u = new RegistrationRequest();
        u.setPassword(userRequest.getPassword());

        u.setFirstName(userRequest.getFirstname());
        u.setLastName(userRequest.getLastname());
        u.setEmail(userRequest.getEmail());
        u.setAddress(userRequest.getAddress());
        u.setPhoneNumber(userRequest.getPhoneNumber());
        u.setExplanation("");
        List<Role> roles = roleService.findByName(userRequest.getRoleName());
        u.setRole(roles.get(0));
        return registrationRequestRepository.save(u);
    }

    public RegisteredUser saveClient(RegistrationRequest userRequest) {
        RegisteredUser u = new RegisteredUser();
        u.setPassword(passwordEncoder.encode(userRequest.getPassword()));

        u.setFirstName(userRequest.getFirstName());
        u.setLastName(userRequest.getLastName());
        u.setEnabled(true);
        u.setEmail(userRequest.getEmail());
        u.setStatus(UserStatus.active);
        u.setEnabled(true);
        u.setAddress(userRequest.getAddress());
        u.setPhoneNumber(userRequest.getPhoneNumber());
        List<Role> roles = roleService.findByName(userRequest.getRole().getName());
        u.setRoles(roles);

        return this.userRepository.save(u);
    }

    public RegistrationRequest findRequestByEmail(String email) {
        return registrationRequestRepository.findByEmail(email);
    }
    public Admin saveAdmin(RegisteredUser newAdminUser) {
        Admin admin = new Admin(newAdminUser, false);

        List<Role> roles = roleService.findByName("ROLE_ADMIN");
        admin.setRoles(roles);
        admin.setPassword(passwordEncoder.encode(newAdminUser.getPassword()));
        admin.setLastPasswordResetDate(new Timestamp(System.currentTimeMillis()));
        admin.setEnabled(true);
        admin.setStatus(UserStatus.active);

        return this.userRepository.save(admin);
    }

    public RegisteredUser GetById(int id) {
        return userRepository.getById(id);

    }
    public List<RegisteredUser> getAllUsers() {
        return this.userRepository.findAll();
    }

    public void deleteUser(Integer id) {
        this.userRepository.delete(this.userRepository.getById(id));
    }

    public RegistrationRequest saveRegistrationRequest(RegistrationRequest request) {
        return this.registrationRequestRepository.save(request);
    }
}