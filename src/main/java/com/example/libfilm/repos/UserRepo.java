package com.example.libfilm.repos;


import com.example.libfilm.dao.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {
       User findByUsername(String username);

       User findByActivationCode(String code);

       void deleteByActivationCode(String code);
}