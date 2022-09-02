package com.example.beveragemanager.Reponsitory;

import com.example.beveragemanager.Entiry.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByToken(String token);
    User findByUsername(String username);
}
