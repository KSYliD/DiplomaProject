package com.example.diploma.repository.admin;

import com.example.diploma.model.user.User;
import com.example.diploma.model.user.UserStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserAdminRepository extends JpaRepository<User, Long> {
    // Додай кастомні методи для пошуку за ролями, статусом і пагінацією
    Page<User> findAllByRoleContainingIgnoreCaseAndStatusContainingIgnoreCase(String role, String status, Pageable pageable);
    @Query("SELECT DISTINCT u FROM User u JOIN u.role r WHERE r.name <> 'ADMIN'")
    List<User> findAllExceptAdmins();

    @Query("SELECT DISTINCT u FROM User u JOIN u.role r WHERE r.name = :role AND r.name <> 'ADMIN'")
    List<User> findByRoleExcludingAdmin(@Param("role") String role);

    @Query("SELECT DISTINCT u FROM User u JOIN u.role r WHERE u.status = :status AND r.name <> 'ADMIN'")
    List<User> findByStatusExcludingAdmin(@Param("status") UserStatus status);

    @Query("SELECT DISTINCT u FROM User u JOIN u.role r WHERE r.name = :role AND u.status = :status AND r.name <> 'ADMIN'")
    List<User> findByRoleAndStatusExcludingAdmin(@Param("role") String role, @Param("status") UserStatus status);

}

