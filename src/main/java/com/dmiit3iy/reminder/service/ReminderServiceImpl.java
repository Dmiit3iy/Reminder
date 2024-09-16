package com.dmiit3iy.reminder.service;

import com.dmiit3iy.reminder.model.Reminder;
import com.dmiit3iy.reminder.model.User;
import com.dmiit3iy.reminder.repository.ReminderRepository;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
@AllArgsConstructor
public class ReminderServiceImpl implements ReminderService {
    private final ReminderRepository reminderRepository;
    private final UserService userService;

    @Override
    public void add(Reminder reminder, long userID) {
        try {
            User user = userService.get(userID);
            reminder.setUser(user);
            reminderRepository.save(reminder);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("This reminder already exists");
        }
    }

    @Override
    public Reminder get(long id, long userID) {
        return reminderRepository.findById(id).
                orElseThrow(() -> new IllegalArgumentException(" There is no such reminder in the application"));
    }

    @Override
    public List<Reminder> get(long userID) {
        return reminderRepository.findAll();
    }

    @Override
    public Page<Reminder> get(Pageable pageable, long userID) {
        return reminderRepository.findAll(pageable);
    }

    @Override
    public Page<Reminder> get(int page, int size, long userID) {
        Pageable pageable = PageRequest.of(page, size);
        return reminderRepository.findAll(pageable);
    }

    @Override
    public Page<Reminder> get(int page, int size, long userID, String by) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Reminder> reminderPage = Page.empty();
        switch (by) {
            case "time":
                reminderPage = reminderRepository.findAllSortedByTime(userID, pageable);
                break;
            case "date":
                reminderPage = reminderRepository.findAllSortedByDate(userID, pageable);
                break;
            case "title":
                reminderPage = reminderRepository.findAllSortedByTitle(userID, pageable);
                break;

        }
        return reminderPage;

    }

    @Override
    public Reminder getLast(long userID) {
        return reminderRepository.findTopByUserIdOrderByIdAsc(userID).orElseThrow(() -> new IllegalArgumentException("No reminders have been created yet"));
    }

    @Override
    public List<Reminder> get(String title, long userID) {
        return reminderRepository.findByTitleAndUserId(title, userID);
    }

    @Override
    public List<Reminder> getByDescription(String description, long userID) {
        return reminderRepository.findByDescriptionAndUserId(description, userID);
    }

    @Override
    public List<Reminder> get(LocalDate localDate, long userID) {
        return reminderRepository.findByLocalDate(localDate);
    }

    @Override
    public List<Reminder> get(LocalTime localTime, long userID) {
        return reminderRepository.findByLocalTime(localTime);
    }

    @Override
    public Reminder delete(long id, long userID) {
        Reminder reminder = this.get(id, userID);
        reminderRepository.delete(reminder);
        return reminder;
    }

    @Override
    public Reminder delete(long userID) {
        Reminder reminder = this.getLast(userID);
        reminderRepository.delete(reminder);
        return reminder;
    }

    @Override
    public Reminder update(Reminder reminder, long userID) {
        Reminder baseReminder = this.get(reminder.getId(), userID);
        baseReminder.setTitle(reminder.getTitle());
        baseReminder.setDescription(reminder.getDescription());
        baseReminder.setRemind(reminder.getRemind());
        return reminderRepository.save(baseReminder);
    }
}
