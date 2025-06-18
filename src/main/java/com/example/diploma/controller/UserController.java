package com.example.diploma.controller;

import com.example.diploma.dto.UserDto;
import com.example.diploma.dto.user.UpdateUserRequest;
import com.example.diploma.dto.user.UserResponse;
import com.example.diploma.service.interfaces.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long id, @RequestBody UserDto userDto) {
        return ResponseEntity.ok(userService.updateUser(id, userDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
    @PutMapping("/me")
    public ResponseEntity<UserResponse> updateProfile(@RequestBody UpdateUserRequest request, @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(userService.updateCurrentUser(request, userDetails));
    }

    @DeleteMapping("/me")
    public ResponseEntity<Void> deleteProfile(@AuthenticationPrincipal UserDetails userDetails) {
        userService.deleteCurrentUser(userDetails);
        return ResponseEntity.noContent().build();
    }
}
