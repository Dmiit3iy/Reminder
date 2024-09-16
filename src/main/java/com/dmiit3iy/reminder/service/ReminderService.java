package com.dmiit3iy.reminder.service;

import com.dmiit3iy.reminder.model.Reminder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface ReminderService {

    void add(Reminder reminder, int userID);

    /**
     * Получение списка напоминаний по ID
     *
     * @param id
     * @return
     */
    Reminder get(long id, int userID);

    /**
     * Получение списка всех напоминаний
     *
     * @return
     */
    List<Reminder> get(int userID);

    /**
     * Получение списка с пагинацией
     * @param pageable
     * @return
     */
    Page<Reminder> get(Pageable pageable, int userID);

    /**
     * Получение списка с пагинацией с указанием параметров
     * @param
     * @return
     */
    Page<Reminder> get(int page, int size, int userID);
    /**
     * Получение последнего созданного напоминаия
     *
     * @return
     */
    Reminder getLast(int userID);

    /**
     * Получение списка напоминаний по заголовку
     *
     * @param title
     * @return
     */
    List<Reminder> get(String title, int userID);

    /**
     * Получение списка напоминаний по описанию
     *
     * @param description
     * @return
     */

    List<Reminder> getByDescription(String description, int userID);

    /**
     * Получение списка напоминаний по дате
     *
     * @param localDate
     * @return
     */

    List<Reminder> get(LocalDate localDate, int userID);

    /**
     * Получение списка напоминаний по времени
     *
     * @param localTime
     * @return
     */
    List<Reminder> get(LocalTime localTime, int userID);

    /**
     * Удаление напоминания по ID
     *
     * @param id
     * @return
     */
    Reminder delete(long id, int userID);

    /**
     * Удаление последнего напоминания
     * @return
     */
    Reminder delete(int userID);

    /**
     * Обновление напоминания
     *
     * @param reminder
     * @return
     */
    Reminder update(Reminder reminder, int userID);

}
