package com.dmiit3iy.reminder.service;

import com.dmiit3iy.reminder.model.User;

public interface UserService {
    void add(User user);

    User get(long id);

    User delete(long id);
}
