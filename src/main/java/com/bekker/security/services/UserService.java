package com.bekker.security.services;

import com.bekker.security.entities.Role;
import com.bekker.security.entities.User;
import java.util.List;
import java.util.Set;

public interface UserService {

    List<User> findAll();
    User findById(Long id);
    void save(User user);
    void delete(Long id);
    void update(Long id, User updatedUser);
    User findByUsername(String username);
}
