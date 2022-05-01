package com.bekker.security.services;

import com.bekker.security.entities.Role;

import java.util.List;

public interface RoleService {

    Role findByName(String roleName);

    List<Role> findAll();
}
