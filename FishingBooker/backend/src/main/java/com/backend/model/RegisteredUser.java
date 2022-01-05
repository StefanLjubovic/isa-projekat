package com.backend.model;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.persistence.Entity;
import java.util.*;
import java.sql.Timestamp;
@Entity
@Inheritance(strategy= InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="type", discriminatorType=DiscriminatorType.STRING)
public class RegisteredUser implements UserDetails {

    @Id
    @SequenceGenerator(name = "userSeqGen", sequenceName = "userSeqGen", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "userSeqGen")
    @Column(name="reg_user_id", unique=true, nullable=false)
    private Integer id;

    @Column(name="first_name", unique=false, nullable=false)
    private String firstName;

    @Column(name="last_name", unique=false, nullable=false)
    private String lastName;

    @Column(name="phone_number", unique=false, nullable=false)
    private String phoneNumber;

    @Column(name="email", unique=true, nullable=false)
    private String email;

    @Column(name="password", unique=false, nullable=false)
    private String password;

    @Column(name="user_status", unique=false, nullable=false)
    private UserStatus status;

    @Column(name = "enabled")
    private boolean enabled;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "role_id")
    private Role role;

    @Column(name = "last_password_reset_date")
    private Timestamp lastPasswordResetDate;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private Address address;

    public RegisteredUser() { }

    public RegisteredUser(String firstName, String lastName, String phoneNumber, String email, String password, UserStatus status, boolean enabled, Role role, Timestamp lastPasswordResetDate, Address address) {
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

    public RegisteredUser(Integer id, String firstName, String lastName, String phoneNumber, String email, String password, UserStatus status, boolean enabled, Role role, Timestamp lastPasswordResetDate, Address address) {
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

    public RegisteredUser(RegisteredUser user) {
        this.id = user.getId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.phoneNumber = user.getPhoneNumber();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.status = user.getStatus();
        this.enabled = user.isEnabled();
        this.role = user.getRole();
        this.lastPasswordResetDate = user.getLastPasswordResetDate();
        this.address = user.getAddress();
    }

    public RegisteredUser(RegistrationRequest user) {
        this.id = user.getId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.phoneNumber = user.getPhoneNumber();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.status = UserStatus.active;
        this.enabled = true;
        this.address = user.getAddress();
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

    public void setPassword(String password) {
        Timestamp now = new Timestamp(new Date().getTime());
        this.setLastPasswordResetDate(now);
        this.password = password;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Role getRole() {
        return role;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<Role> collection = new ArrayList<Role>();
        collection.add(this.role);
        return collection;
    }

    public String getPassword() {
        return password;
    }

    public Timestamp getLastPasswordResetDate() {
        return lastPasswordResetDate;
    }

    public void setLastPasswordResetDate(Timestamp lastPasswordResetDate) {
        this.lastPasswordResetDate = lastPasswordResetDate;
    }


    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String toString() {
        return "RegistratedUser{}";
    }

    public String getFullName() {
        return this.getFirstName() + " " + this.getLastName();
    }
}