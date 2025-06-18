package com.example.diploma.dto.user;

import com.example.diploma.model.user.User;
import com.example.diploma.model.user.UserStatus;
import lombok.Data;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class UserAdminDTO {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private List<String> roles;
    private UserStatus status;

    public static UserAdminDTO fromEntity(User user) {
        UserAdminDTO dto = new UserAdminDTO();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setRoles(user.getRole().stream()
                .map(role -> role.getName().toUpperCase())
                .collect(Collectors.toList()));
        dto.setStatus(user.getStatus());
        return dto;
    }
}

