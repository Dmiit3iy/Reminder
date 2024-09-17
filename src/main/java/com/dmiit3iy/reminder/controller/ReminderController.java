package com.dmiit3iy.reminder.controller;

import com.dmiit3iy.reminder.DTO.ResponseResult;
import com.dmiit3iy.reminder.model.Reminder;
import com.dmiit3iy.reminder.service.ReminderService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/domain/api/v1")
@AllArgsConstructor
public class ReminderController {
    private final ReminderService reminderService;

    /**
     * Получение списка с пагинацией, с параметрами
     *
     * @param current
     * @param total
     * @return
     */
    @GetMapping("/list/{idUser}")
    public ResponseEntity<ResponseResult<Page<Reminder>>> getUsers(
            @RequestParam(name = "current", defaultValue = "0") int current,
            @RequestParam(name = "total", defaultValue = "5") int total, @PathVariable("idUser") int idUser) {
        Page<Reminder> pageRemind = reminderService.get(current, total, idUser);
        return new ResponseEntity<>(new ResponseResult<>(null, pageRemind), HttpStatus.OK);
    }


    @GetMapping("/sort/{idUser}")
    public ResponseEntity<ResponseResult<Page<Reminder>>> getSortedUsers(@RequestParam(defaultValue = "0") int current,
                                                                         @RequestParam(defaultValue = "10") int total,
                                                                         @RequestParam(defaultValue = "title") String by,
                                                                         @PathVariable("idUser") int idUser) {
        if (!List.of("title", "date", "time").contains(by)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Page<Reminder> pageRemind = reminderService.get(current, total, idUser, by);
        return new ResponseEntity<>(new ResponseResult<>(null, pageRemind), HttpStatus.OK);
    }


    @GetMapping("/filtr/{idUser}")
    public ResponseEntity<ResponseResult<Page<Reminder>>> getFilter(@RequestParam(defaultValue = "0") int current,
                                                                    @RequestParam(defaultValue = "10") int total,
                                                                    @RequestParam(value = "date", required = false)
                                                                    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                                                                    @RequestParam(value = "time", required = false)
                                                                    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime time,
                                                                    @PathVariable("idUser") int idUser) {
        Page<Reminder> pageRemind = reminderService.getFilter(current, total, date, time, idUser);
        return new ResponseEntity<>(new ResponseResult<>(null, pageRemind), HttpStatus.OK);
    }


    /**
     * Создание напоминания
     *
     * @param reminder
     * @return
     */
    @PostMapping("/reminder/create/{idUser}")
    public ResponseEntity<ResponseResult<Reminder>> add(@RequestBody Reminder reminder,
                                                        @PathVariable("idUser") int idUser) {
        reminderService.add(reminder, idUser);
        return new ResponseEntity<>(new ResponseResult<>(null, reminder), HttpStatus.OK);
    }

    @DeleteMapping("/reminder/delete/{idUser}")
    public ResponseEntity<ResponseResult<Reminder>> delete(@PathVariable("idUser") int idUser) {
        Reminder reminder = reminderService.delete(idUser);
        return new ResponseEntity<>(new ResponseResult<>(null, reminder), HttpStatus.OK);
    }

    /**
     * Удаление напоминания по id
     *
     * @param id
     * @return
     */
    @DeleteMapping("/reminder/delete/{id}/{idUser}")
    public ResponseEntity<ResponseResult<Reminder>> delete(@PathVariable("id") long id, @PathVariable("idUser") int idUser) {
        Reminder reminder = reminderService.delete(id);
        return new ResponseEntity<>(new ResponseResult<>(null, reminder), HttpStatus.OK);
    }


    @GetMapping("/reminder/search/{idUser}")
    public ResponseEntity<ResponseResult<List<Reminder>>> searchReminders(@RequestParam(value = "title", required = false) String title,
                                                                          @RequestParam(value = "description", required = false) String description,
                                                                          @RequestParam(value = "date", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                                                                          @RequestParam(value = "time", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime time,
                                                                          @PathVariable("idUser") int idUser) {

        List<Reminder> reminders = reminderService.searchReminders(title, description, date, time, idUser);
        return new ResponseEntity<>(new ResponseResult<>(null, reminders), HttpStatus.OK);
    }

}
