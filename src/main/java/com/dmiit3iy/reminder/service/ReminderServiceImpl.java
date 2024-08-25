package com.dmiit3iy.reminder.service;

import com.dmiit3iy.reminder.model.Reminder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class ReminderServiceImpl implements ReminderService {
    @Override
    public void add(Reminder reminder) {

    }

    @Override
    public Reminder get(long id) {
        return null;
    }

    @Override
    public List<Reminder> get(String title) {
        return null;
    }

    @Override
    public List<Reminder> getByDescription(String description) {
        return null;
    }

    @Override
    public List<Reminder> get(LocalDate localDate) {
        return null;
    }

    @Override
    public List<Reminder> get(LocalTime localTime) {
        return null;
    }

    @Override
    public Reminder delete(long id) {
        return null;
    }

    @Override
    public Reminder update(Reminder reminder) {
        return null;
    }
}
