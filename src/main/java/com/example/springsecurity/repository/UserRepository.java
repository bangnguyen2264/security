package com.example.springsecurity.repository;

import com.example.springsecurity.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);

    @Query("SELECT p FROM User p " +
            "WHERE p.firstname like concat('%',:query,'%') " +
            "or p.lastname like concat('%',:query,'%')" +
            "or p.username like concat('%',:query,'%')"
    )
    List<User> searchUser(String query);
}
