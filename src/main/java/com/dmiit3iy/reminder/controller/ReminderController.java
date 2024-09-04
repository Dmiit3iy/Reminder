package com.dmiit3iy.reminder.controller;

import com.dmiit3iy.reminder.DTO.ResponseResult;
import com.dmiit3iy.reminder.model.Reminder;
import com.dmiit3iy.reminder.service.ReminderService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/domain/api/v1")
@AllArgsConstructor
public class ReminderController {
    private final ReminderService reminderService;

    /**
     * Создание напоминания
     *
     * @param reminder
     * @return
     */
    @PostMapping("/reminder/create")
    public ResponseEntity<ResponseResult<Reminder>> add(@RequestBody Reminder reminder) {
        try {
            reminderService.add(reminder);
            return new ResponseEntity<>(new ResponseResult<>(null, reminder), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new ResponseResult<>(e.getMessage(), null), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/reminder/delete")
    public ResponseEntity<ResponseResult<Reminder>> delete() {
        try {
            Reminder reminder = reminderService.delete();
            return new ResponseEntity<>(new ResponseResult<>(null, reminder), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new ResponseResult<>(e.getMessage(), null), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Удаление напоминания по id
     *
     * @param id
     * @return
     */
    @DeleteMapping("/reminder/delete/{id}")
    public ResponseEntity<ResponseResult<Reminder>> delete(@PathVariable("id") long id) {
        try {
            Reminder reminder = reminderService.delete(id);
            return new ResponseEntity<>(new ResponseResult<>(null, reminder), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new ResponseResult<>(e.getMessage(), null), HttpStatus.BAD_REQUEST);
        }
    }

}
