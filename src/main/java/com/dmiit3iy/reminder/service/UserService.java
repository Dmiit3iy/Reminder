package com.dmiit3iy.reminder.service;

import com.dmiit3iy.reminder.model.User;

import java.util.Optional;

public interface UserService {
    void add(User user);

    User get(long id);

    Optional<User> get(String telegram);
    User update (User user);

    User delete(long id);
}
