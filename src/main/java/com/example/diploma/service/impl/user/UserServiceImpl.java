package com.example.diploma.service.impl.user;

import com.example.diploma.dto.UserDto;
import com.example.diploma.dto.user.UpdateUserRequest;
import com.example.diploma.dto.user.UserResponse;
import com.example.diploma.mapper.UserMapper;
import com.example.diploma.model.fundraiser.Fundraiser;
import com.example.diploma.model.user.User;
import com.example.diploma.model.user.Role;
import com.example.diploma.repository.FundraiserRepository;
import com.example.diploma.repository.UserRepository;
import com.example.diploma.service.interfaces.user.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final FundraiserRepository fundraiserRepository;
    private final UserMapper userMapper;

    @Override
    public UserDto getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return userMapper.userToUserDto(user);
    }

    @Override
    public UserDto updateUser(Long id, UserDto dto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setEmail(dto.getEmail());
        user.setRole(dto.getRole());
        user.setStatus(dto.getStatus());
        userRepository.save(user);

        return getUserById(id);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public void subscribe(String email, Long fundraiserId) {
        User user = getUserByEmail(email);
        Fundraiser fundraiser = getFundraiserById(fundraiserId);

        if (!user.getSubscriptions().contains(fundraiser)) {
            user.getSubscriptions().add(fundraiser);
            userRepository.save(user);
        }
    }

    @Override
    public void unsubscribe(String email, Long fundraiserId) {
        User user = getUserByEmail(email);
        Fundraiser fundraiser = getFundraiserById(fundraiserId);

        if (user.getSubscriptions().contains(fundraiser)) {
            user.getSubscriptions().remove(fundraiser);
            userRepository.save(user);
        }
    }

    @Override
    public List<Fundraiser> getUserSubscriptions(String email) {
        return getUserByEmail(email).getSubscriptions();
    }

    private User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User with email " + email + " not found"));
    }

    private Fundraiser getFundraiserById(Long fundraiserId) {
        return fundraiserRepository.findById(fundraiserId)
                .orElseThrow(() -> new EntityNotFoundException("Fundraiser with ID " + fundraiserId + " not found"));
    }

    @Override
    public UserResponse updateCurrentUser(UpdateUserRequest request, UserDetails userDetails) {
        User user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setContacts(request.getContacts());

        userRepository.save(user);

        return mapToUserResponse(user);
    }

    @Override
    public void deleteCurrentUser(UserDetails userDetails) {
        User user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        userRepository.delete(user);
    }

    private UserResponse mapToUserResponse(User user) {
        return UserResponse.builder()
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .contacts(user.getContacts())
                .roles(user.getRole().stream().map(Role::getName).collect(Collectors.toList()))
                .build();
    }
}
