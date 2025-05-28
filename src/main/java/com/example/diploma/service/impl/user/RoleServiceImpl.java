package com.example.diploma.service.impl.user;

import com.example.diploma.model.user.Role;
import com.example.diploma.repository.RoleRepository;
import com.example.diploma.service.interfaces.user.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RoleServiceImpl implements RoleService {

    private RoleRepository roleRepository;

    @Override
    public Role findByName(String name) {
        return roleRepository.findByName(name);
    }
}

