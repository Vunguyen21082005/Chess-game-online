package com.example.backend.repository;

import com.example.backend.moder.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Controller;

@Controller
public interface UserRepository extends JpaRepository<User,String> {
    Boolean existsByEmail (String email);
}
