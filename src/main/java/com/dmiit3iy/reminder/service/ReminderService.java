package com.dmiit3iy.reminder.service;

import com.dmiit3iy.reminder.model.Reminder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface ReminderService {

    void add(Reminder reminder, long userID);

    Reminder getById(long id);

    /**
     * Получение напоминания по ID для конкретного полоьзователя
     *
     * @param id
     * @return
     */
    Reminder get(long id, long userID);

    /**
     * Метод для получения списка напоминаний всех пользователей необходимых к отправке
     *
     * @return
     */
    List<Reminder> getToSend();

    /**
     * Получение списка всех напоминаний
     *
     * @return
     */
    List<Reminder> get(long userID);

    /**
     * Получение списка с пагинацией
     *
     * @param pageable
     * @return
     */
    Page<Reminder> get(Pageable pageable, long userID);

    /**
     * Получение списка с пагинацией с указанием параметров
     *
     * @param
     * @return
     */
    Page<Reminder> get(int page, int size, long userID);

    /**
     * Получение списка с пагинацией с указанием параметров и сортировкой
     *
     * @param
     * @return
     */
    Page<Reminder> get(int page, int size, long userID, String by);

    /**
     * Получение отфильтрованного списка
     *
     * @param date
     * @param time
     * @param userId
     * @return
     */
    Page<Reminder> getFilter(int page, int size, LocalDate date, LocalTime time, long userId);


    /**
     * Получение последнего созданного напоминаия
     *
     * @return
     */
    Reminder getLast(long userID);

    /**
     * Получение списка напоминаний по заголовку
     *
     * @param title
     * @return
     */
    List<Reminder> get(String title, long userID);

    /**
     * Получение списка напоминаний по описанию
     *
     * @param description
     * @return
     */

    List<Reminder> getByDescription(String description, long userID);

    /**
     * Получение списка напоминаний по дате
     *
     * @param localDate
     * @return
     */

    List<Reminder> get(LocalDate localDate, long userID);

    /**
     * Получение списка напоминаний по времени
     *
     * @param localTime
     * @return
     */
    List<Reminder> get(LocalTime localTime, long userID);

    /**
     * Удаление напоминания по ID
     *
     * @param id
     * @return
     */
    Reminder delete(long id, long userID);

    /**
     * Удаление последнего напоминания
     *
     * @return
     */
    Reminder delete(long userID);

    /**
     * Обновление напоминания
     *
     * @param reminder
     * @return
     */
    Reminder update(Reminder reminder, long userID);

    Reminder update(Reminder reminder);


    /**
     * Поиск уведомлений
     *
     * @param title
     * @param description
     * @param date
     * @param time
     * @return
     */
    List<Reminder> searchReminders(String title, String description, LocalDate date, LocalTime time, long userID);


}
