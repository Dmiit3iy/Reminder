package com.dmiit3iy.reminder.repository;

import com.dmiit3iy.reminder.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByTelegram(String telegram);
}
