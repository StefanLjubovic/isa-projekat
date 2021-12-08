package com.backend.dto;

import com.backend.model.Address;
import com.backend.model.Role;
import com.backend.model.UserStatus;

import java.sql.Timestamp;

public class RegisteredUserDTO {

    private Integer id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private String password;
    private UserStatus status;
    private boolean enabled;
    private Role role;
    private Timestamp lastPasswordResetDate;
    private Address address;

    public RegisteredUserDTO() {
    }

    public RegisteredUserDTO(Integer id, String firstName, String lastName, String phoneNumber, String email, String password, UserStatus status, boolean enabled, Role role, Timestamp lastPasswordResetDate, Address address) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.password = password;
        this.status = status;
        this.enabled = enabled;
        this.role = role;
        this.lastPasswordResetDate = lastPasswordResetDate;
        this.address = address;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Timestamp getLastPasswordResetDate() {
        return lastPasswordResetDate;
    }

    public void setLastPasswordResetDate(Timestamp lastPasswordResetDate) {
        this.lastPasswordResetDate = lastPasswordResetDate;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
