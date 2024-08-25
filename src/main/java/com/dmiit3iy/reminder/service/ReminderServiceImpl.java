package com.dmiit3iy.reminder.service;

import com.dmiit3iy.reminder.model.Reminder;
import com.dmiit3iy.reminder.repository.ReminderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class ReminderServiceImpl implements ReminderService {
    private ReminderRepository reminderRepository;

    @Autowired
    public void setReminderRepository(ReminderRepository reminderRepository) {
        this.reminderRepository = reminderRepository;
    }

    @Override
    public void add(Reminder reminder) {
        try {
            reminderRepository.save(reminder);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("This reminder already exists");
        }
    }

    @Override
    public Reminder get(long id) {
        return reminderRepository.findById(id).
                orElseThrow(() -> new IllegalArgumentException(" There is no such reminder in the application"));
    }

    @Override
    public List<Reminder> get(String title) {
        return reminderRepository.findByTitle(title);
    }

    @Override
    public List<Reminder> getByDescription(String description) {
        return reminderRepository.findByDescription(description);
    }

    @Override
    public List<Reminder> get(LocalDate localDate) {
        return reminderRepository.findByLocalDate(localDate);
    }

    @Override
    public List<Reminder> get(LocalTime localTime) {
        return reminderRepository.findByLocalTime(localTime);
    }

    @Override
    public Reminder delete(long id) {
        Reminder reminder = this.get(id);
        reminderRepository.delete(reminder);
        return reminder;
    }

    @Override
    public Reminder update(Reminder reminder) {
        Reminder baseReminder = this.get(reminder.getId());
        baseReminder.setTitle(reminder.getTitle());
        baseReminder.setDescription(reminder.getDescription());
        baseReminder.setRemind(reminder.getRemind());
        return reminderRepository.save(baseReminder);
    }
}
