package com.backend.controller;

import com.backend.dto.RegisteredUserDTO;
import com.backend.dto.UpdateProfileDTO;
import com.backend.dto.UserTokenState;
import com.backend.model.RegisteredUser;
import com.backend.model.RentingEntity;
import com.backend.service.DeleteRequestService;
import com.backend.service.DeleteUserService;
import com.backend.service.UserService;
import com.backend.util.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private DeleteUserService deleteUserService;

    @Autowired
    private TokenUtils tokenUtils;

    @GetMapping(value="/getById/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','COTTAGE_OWNER', 'SHIP_OWNER', 'INSTRUCTOR','CLIENT')")
    public ResponseEntity<RegisteredUserDTO> getById(@PathVariable Integer id){
        RegisteredUser user = userService.GetById(id);
        RegisteredUserDTO userDTO = new RegisteredUserDTO(user.getId(), user.getFirstName(), user.getLastName(), user.getPhoneNumber(), user.getEmail(), user.getPassword(), user.getStatus(), user.isEnabled(), user.getRole(), user.getLastPasswordResetDate(), user.getAddress());
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @GetMapping(value="/getByEmail/{email}")
    @PreAuthorize("hasAnyRole('ADMIN','COTTAGE_OWNER', 'SHIP_OWNER', 'INSTRUCTOR','CLIENT')")
    public ResponseEntity<RegisteredUserDTO> getByEmail(@PathVariable("email") String email){
        RegisteredUser user = userService.GetByEmail(email);
        RegisteredUserDTO userDTO = new RegisteredUserDTO(user.getId(), user.getFirstName(), user.getLastName(), user.getPhoneNumber(), user.getEmail(), user.getPassword(), user.getStatus(), user.isEnabled(), user.getRole(), user.getLastPasswordResetDate(), user.getAddress());
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @GetMapping(value="/getLoggedUser")
    @PreAuthorize("hasAnyRole('ADMIN','COTTAGE_OWNER', 'SHIP_OWNER', 'INSTRUCTOR','CLIENT')")
    public ResponseEntity<RegisteredUserDTO> getLoggedUser(Principal principal){
        RegisteredUserDTO user=userService.fetchMyProfileInfo(principal.getName());
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping(value="/update")
    @PreAuthorize("hasAnyRole('ADMIN','COTTAGE_OWNER', 'SHIP_OWNER', 'INSTRUCTOR','CLIENT')")
    public ResponseEntity<Void> updateUser(@RequestBody UpdateProfileDTO user){
        userService.update(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping (value="/changePassword/{password}")
    @PreAuthorize("hasAnyRole('ADMIN','COTTAGE_OWNER', 'SHIP_OWNER', 'INSTRUCTOR','CLIENT')")
    public ResponseEntity<UserTokenState> changePassword(@PathVariable String password, Principal principal){
        String email = principal.getName();
        userService.updatePasswod(principal.getName(), password);
        SecurityContextHolder.getContext().setAuthentication(null);
        Authentication authentication = null;
        try {
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    email, password));
        }
        catch (Exception ex){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        RegisteredUser user = (RegisteredUser) authentication.getPrincipal();
        String jwt = tokenUtils.generateToken(user.getUsername());
        int expiresIn = tokenUtils.getExpiredIn();

        return ResponseEntity.ok(new UserTokenState(jwt, expiresIn,user.getRole().getName()));
    }

    @GetMapping("/allUsers")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<RegisteredUserDTO>> getAllUsers() {
        List<RegisteredUser> users = this.userService.getAllUsers();

        List<RegisteredUserDTO> dto = new ArrayList<>();
        for(RegisteredUser u : users) {
            RegisteredUserDTO userDTO = new RegisteredUserDTO(u.getId(),u.getFirstName(), u.getLastName(), u.getPhoneNumber(), u.getEmail(), u.getPassword(), u.getStatus(), u.isEnabled(), u.getRole(), u.getLastPasswordResetDate(), u.getAddress());
            dto.add(userDTO);
        }

        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @DeleteMapping("/deleteUser/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteUser(@PathVariable("id") Integer id) {
        deleteUserService.deleteUser(id);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @GetMapping("/passwordChanged")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Boolean> hasAdminChangedInitialPassword(Principal admin) {
        Boolean initialPasswordChanged = userService.hasAdminChangedInitialPassword(admin.getName());
        return new ResponseEntity<>(initialPasswordChanged, HttpStatus.OK);
    }

    @PutMapping("/alter-subscriptions/{id}")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<Void> alterSubscriptions(@PathVariable Integer id, Principal principal) {
        userService.alterSubscriptions(principal.getName(),id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}