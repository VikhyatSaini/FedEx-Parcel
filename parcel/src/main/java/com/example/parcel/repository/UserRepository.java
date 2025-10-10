// In src/main/java/com/example/parcel/repository/UserRepository.java
package com.example.parcel.repository;

import com.example.parcel.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByFullName(String fullName);
}