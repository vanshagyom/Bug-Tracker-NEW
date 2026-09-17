package com.practice.OneYear.repository;

import com.practice.OneYear.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
