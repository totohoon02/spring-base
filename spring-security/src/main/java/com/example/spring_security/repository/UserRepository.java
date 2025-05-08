package com.example.spring_security.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.spring_security.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    public boolean existsByUsername(String username);

    public Optional<UserEntity> findByUsername(String username);
}
