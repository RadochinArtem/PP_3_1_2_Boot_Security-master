package ru.kata.spring.boot_security.demo.model;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "roles")
public class Role implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO )
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "password")
    private  String password;

    @ManyToMany(mappedBy = "roles")
    private List<User> roles;

    @Override
    public String getAuthority() {
        return name;
    }

    public Role(Long id, String name, String password, List<User> roles) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.roles = roles;
    }

    public Role(String name) {
        this.name = name;
    }

    public Role() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<User> getRoles() {
        return roles;
    }

    public void setRoles(List<User> roles) {
        this.roles = roles;
    }
}
