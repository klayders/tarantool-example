package org.gosloto.example.repository;

import org.gosloto.example.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepositoryDB extends JpaRepository<User, Long> {
}
