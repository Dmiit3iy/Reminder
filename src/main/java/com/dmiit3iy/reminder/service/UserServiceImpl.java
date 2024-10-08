package com.dmiit3iy.reminder.service;

import com.dmiit3iy.reminder.model.User;
import com.dmiit3iy.reminder.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public void add(User user) {
        try {
            userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("This user has already been added to the application!");
        }
    }

    @Override
    public User get(long id) {
        return userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("The user with the same ID is not in the application"));
    }

    @Override
    public Optional<User> get(String telegram) {
        return userRepository.findByTelegram(telegram);
    }

    @Override
    public User update(User user) {
        User base = this.get(user.getId());
        base.setEmail(user.getEmail());
        base.setChatId(user.getChatId());
        base.setLastName(user.getLastName());
        base.setFirstName(user.getFirstName());
        return userRepository.save(base);
    }

    @Override
    public User delete(long id) {
        User user = this.get(id);
        userRepository.delete(user);
        return user;
    }
}
