package com.dmiit3iy.reminder.repository;

import com.dmiit3iy.reminder.model.Reminder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReminderRepository extends JpaRepository<Reminder, Long> {
    Optional<Reminder> findFirstByIdOrderByIdAsc();
    List<Reminder> findByTitle(String title);

    List<Reminder> findByDescription(String description);

    @Query("SELECT r FROM Reminder r WHERE FUNCTION('TIME', r.remind) = :localTime")
    List<Reminder> findByLocalTime(@Param("localTime") LocalTime localTime);

    @Query("SELECT r FROM Reminder r WHERE FUNCTION('DAY', r.remind) = :localDate")
    List<Reminder> findByLocalDate(@Param("localDate") LocalDate localDate);

    List<Reminder> findByRemind(LocalDate remind);


}
