package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService {
    List<User> findAllUsers();
    void saveUser(User user);
    User findUserById(Long id);
    void updateUser(User user);
    void deleteUserById(Long id);
    User findUserByUsername(String username);

    void init();

}
