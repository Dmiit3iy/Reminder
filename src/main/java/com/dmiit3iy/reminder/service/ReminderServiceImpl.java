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
import java.util.stream.Collectors;

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
        User user = userService.get(userID);
        return reminderRepository.findByUserAndId(user, id).
                orElseThrow(() -> new IllegalArgumentException(" There is no such reminder in the application"));
    }

    @Override
    public List<Reminder> get(long userID) {
        User user = userService.get(userID);
        return reminderRepository.findByUser(user);
    }

    @Override
    public Page<Reminder> get(Pageable pageable, long userID) {
        User user = userService.get(userID);
        return reminderRepository.findByUser(user, pageable);
    }

    @Override
    public Page<Reminder> get(int page, int size, long userID) {
        User user = userService.get(userID);
        Pageable pageable = PageRequest.of(page, size);
        return reminderRepository.findByUser(user, pageable);
    }

    @Override
    public Page<Reminder> get(int page, int size, long userID, String by) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Reminder> reminderPage = Page.empty();
        if (userService.get(userID) != null) {
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
        }
        return reminderPage;

    }

    @Override
    public Page<Reminder> getFilter(int page, int size, LocalDate date, LocalTime time, long userId) {
        User user = userService.get(userId);
        Pageable pageable = PageRequest.of(page, size);

        if (time != null) {
            int hour = time.getHour();
            int minute = time.getMinute();
            int second = time.getSecond();
            if (date != null && time != null) {

                return reminderRepository.findByRemindDateAndTime(userId, date, hour, minute, second, pageable);
            } else {
                return reminderRepository.findByRemindTime(userId, hour, minute, second, pageable);
            }
        } else if (date != null) {
            return reminderRepository.findByRemindDate(userId, date, pageable);
        }
        return reminderRepository.findByUser(user, pageable);
    }

    @Override
    public Reminder getLast(long userID) {
        User user = userService.get(userID);
        return reminderRepository.findTopByUserOrderByIdAsc(user).orElseThrow(() -> new IllegalArgumentException("No reminders have been created yet"));
    }

    @Override
    public List<Reminder> get(String title, long userID) {
        User user = userService.get(userID);
        return reminderRepository.findByTitleAndUser(title, user);
    }

    @Override
    public List<Reminder> getByDescription(String description, long userID) {
        User user = userService.get(userID);
        return reminderRepository.findByDescriptionAndUser(description, user);
    }

    @Override
    public List<Reminder> get(LocalDate localDate, long userID) {
        User user = userService.get(userID);
        return reminderRepository.findByLocalDate(localDate, user);
    }

    @Override
    public List<Reminder> get(LocalTime localTime, long userID) {
        User user = userService.get(userID);
        return reminderRepository.findByLocalTime(localTime, user);
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


    @Override
    public List<Reminder> searchReminders(String title, String description, LocalDate date, LocalTime time, long userID) {
        User user = userService.get(userID);
        List<Reminder> allReminders = reminderRepository.findByUser(user);

        return allReminders.stream()
                .filter(reminder -> (title == null || reminder.getTitle().contains(title)))
                .filter(reminder -> (description == null || reminder.getDescription().contains(description)))
                .filter(reminder -> (date == null || reminder.getRemind().toLocalDate().equals(date)))
                .filter(reminder -> (time == null || reminder.getRemind().toLocalTime().equals(time)))
                .collect(Collectors.toList());
    }
}
