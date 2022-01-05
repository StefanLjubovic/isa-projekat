package com.backend.service;

import com.backend.dto.RegisteredUserDTO;
import com.backend.dto.UpdateProfileDTO;
import com.backend.dto.UserRequest;
import com.backend.model.*;
import com.backend.repository.IAddressRepository;
import com.backend.repository.IEntityRepository;
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
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class UserService {

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IAddressRepository addressRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private IEntityRepository entityRepository;

    @Autowired
    private VerificationTokenService verificationTokenService;

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

    public RegistrationRequest saveRequest(UserRequest userRequest){
        RegistrationRequest u = new RegistrationRequest();
        u.setPassword(passwordEncoder.encode(userRequest.getPassword()));

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
        Client u = new Client(userRequest);
        u.setPassword(userRequest.getPassword());
        Role role = roleService.findOneByName(userRequest.getRole().getName());
        u.setRole(role);
        addressRepository.save(userRequest.getAddress());
        this.verificationTokenService.DeleteTokenByUser(userRequest);
        this.registrationRequestRepository.delete(userRequest);
        return this.userRepository.save(u);
    }

    public RegistrationRequest findRequestByEmail(String email) {
        return registrationRequestRepository.findByEmail(email);
    }

    public Admin saveAdmin(RegisteredUser newAdminUser) {
        Admin admin = new Admin(newAdminUser, false);

        Role role = roleService.findOneByName("ROLE_ADMIN");
        admin.setRole(role);
        admin.setPassword(passwordEncoder.encode(newAdminUser.getPassword()));
        admin.setLastPasswordResetDate(new Timestamp(System.currentTimeMillis()));
        admin.setEnabled(true);
        admin.setStatus(UserStatus.active);
        admin.setInitialPasswordChanged(false);

        return this.userRepository.save(admin);
    }

    public RegisteredUser GetById(int id) {
        return userRepository.getById(id);

    }

    public List<RegisteredUser> getAllUsers() {
        return this.userRepository.findAll();
    }

    public RegistrationRequest saveRegistrationRequest(RegistrationRequest request) {
        request.setRole(this.roleService.findOneByName(request.getRole().getName()));
        request.setPassword(passwordEncoder.encode(request.getPassword()));
        return this.registrationRequestRepository.save(request);
    }

    public RegisteredUserDTO fetchMyProfileInfo(String email){
        return userRepository.myProfileInformation(email);
    }

    public void update(UpdateProfileDTO user) {
       RegisteredUser userToUpdate = userRepository.findById(user.getId()).get();
       userToUpdate.setAddress(user.getAddress());
       userToUpdate.setFirstName(user.getFirstname());
       userToUpdate.setLastName(user.getLastname());
       userToUpdate.setPhoneNumber(user.getPhoneNumber());
       userRepository.save(userToUpdate);
    }

    public void updatePasswod(String email, String password) {
        RegisteredUser user=userRepository.findByEmail(email);
        user.setPassword(passwordEncoder.encode(password));

        if(user.getRole().getName().equals("ROLE_ADMIN")) {
            Admin admin = (Admin) user;
            admin.setInitialPasswordChanged(true);
            userRepository.save(admin);
        } else {
            userRepository.save(user);
        }
    }

    public Boolean hasAdminChangedInitialPassword(String email) {
        Admin admin = (Admin) userRepository.findByEmail(email);
        return admin.isInitialPasswordChanged();
    }

    public void  alterSubscriptions(String email, Integer id){
        Client client = userRepository.fetchClientWithSubscriptions(email);
        RentingEntity entityToAdd = client.getSubscriptions()
                .stream()
                .filter(e -> e.getId().equals(id)).findFirst().orElse(null);
        if(entityToAdd == null)client.getSubscriptions().add(entityRepository.findById(id).get());
        else client.getSubscriptions().remove(entityToAdd);
        userRepository.save(client);
    }

    public RegisteredUser GetByEmail(String email) {
        return userRepository.getByEmail(email);
    }
}