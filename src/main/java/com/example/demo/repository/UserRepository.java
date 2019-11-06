package com.example.demo.repository;

import com.example.demo.entity.CustomUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<CustomUser, Long> {

    @Query("SELECT u FROM CustomUser u WHERE u.email = :email")
    CustomUser findByEmail(@Param("email") String login);

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM CustomUser u WHERE u.email = :email")
    boolean existsByEmail(String email);
}
