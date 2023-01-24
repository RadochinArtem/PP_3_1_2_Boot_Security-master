package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    public UserServiceImpl(RoleRepository roleRepository, UserRepository userRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findByUsername(username);

        if (userOptional.isEmpty()) {
            throw new UsernameNotFoundException("Username not found");
        } else {
            return userOptional.get();
        }
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public void saveUser(User user) {
        user.setUsername(user.getUsername());
        user.setPassword(user.getPassword());
        List<Role> roles = Collections.singletonList(roleRepository.findRoleByName("ROLE_USER"));
        user.setRoles(roles);
        userRepository.save(user);
    }

    @Override
    public User findUserById(Long id) {
        return userRepository.findUserById(id);
    }

    @Override
    public void updateUser(User user) {
        User userDataBase = findUserById(user.getId());
        userDataBase.setUsername(user.getUsername());
        userDataBase.setPassword(user.getPassword());
        userDataBase.setRoles(user.getRoles());
        userRepository.save(userDataBase);
    }

    @Override
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username).get();
    }

    @PostConstruct
    @Override
    public void init() {
        Role roleAdmin = new Role("ROLE_ADMIN");
        Role roleUser = new Role("ROLE_USER");
        if (roleRepository.count() == 0) {
            roleRepository.save(roleAdmin);
            roleRepository.save(roleUser);
            List<Role> rolesForAdmin = new ArrayList<>();
            List<Role> rolesForUser = new ArrayList<>();
            rolesForAdmin.add(roleAdmin);
            rolesForAdmin.add(roleUser);
            rolesForUser.add(roleUser);
            User admin = new User("admin", "admin", rolesForAdmin);
            User user = new User("user", "user", rolesForUser);
            userRepository.save(admin);
            userRepository.save(user);
        }
    }
}
