package com.example.diploma.service.interfaces.user;

import com.example.diploma.model.user.Role;

public interface RoleService {
    Role findByName(String name);

}